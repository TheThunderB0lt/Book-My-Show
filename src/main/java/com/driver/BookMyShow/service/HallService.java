package com.driver.BookMyShow.service;

import com.driver.BookMyShow.dto.request.AddScreenDTO;
import com.driver.BookMyShow.dto.request.AddShowDTO;
import com.driver.BookMyShow.dto.request.HallOwnerSignupDTO;
import com.driver.BookMyShow.exceptions.ResourcesNotExistException;
import com.driver.BookMyShow.exceptions.UnAuthorized;
import com.driver.BookMyShow.exceptions.UserDoesNotExistException;
import com.driver.BookMyShow.models.*;
import com.driver.BookMyShow.repository.ApplicationUserRepository;
import com.driver.BookMyShow.repository.HallRepository;
import com.driver.BookMyShow.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HallService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    RegularUserService regularUserService;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    ScreenService screenService;

    @Autowired
    MovieService movieService;

    @Autowired
    ShowService showService;

    public ApplicationUser signup(HallOwnerSignupDTO hallOwnerSignupDTO) {
        ApplicationUser hallOwner = new ApplicationUser();
        hallOwner.setName(hallOwnerSignupDTO.getName());
        hallOwner.setEmail(hallOwnerSignupDTO.getEmail());
        hallOwner.setPassword(hallOwnerSignupDTO.getPassword());
        hallOwner.setPhoneNo(hallOwnerSignupDTO.getPhoneNo());
        hallOwner.setType(hallOwnerSignupDTO.getType().toString());
        hallOwner.setAge(hallOwnerSignupDTO.getCompanyAge());
        applicationUserRepository.save(hallOwner); //saving ownerID

        List<Hall> halls = hallOwnerSignupDTO.getHalls();
        for (Hall hall : halls) {
            hall.setOwner(hallOwner);
            hallRepository.save(hall);
        }
        return hallOwner;
    }

    public Hall getHallById(UUID id) {
        return hallRepository.findById(id).orElse(null);
    }

    public void addScreens(AddScreenDTO addScreenDTO, String email) {
        List<Screen> screens = addScreenDTO.getScreens();
        UUID hallId = addScreenDTO.getHallId();
        ApplicationUser user = regularUserService.getUserByEmail(email);

        if (user == null) { //if user email not valid
            throw new UserDoesNotExistException("User with this email does not exist!");
        }
        if (!user.getType().equals("HallOwner")) { //if user type not hallowner then he can't access that hallowner type
            throw new UnAuthorized("User does not have permission to perform this action, You're not HallOwner!");
        }

        Hall hall = getHallById(hallId);
        if(hall == null) {
            throw new ResourcesNotExistException(String.format("Hall with id %s does not exist in system", hallId.toString()));
        }
        if(hall.getOwner().getId() != user.getId()) {
            throw new UnAuthorized("User does not hold this hallId");
        }

        for (Screen screen : screens) {
            screen.setHall(hall);
            screenService.registerScreen(screen); //save the screen to DB
        }
    }

    public Show createShow(AddShowDTO addShowDTO, String email) {
        //1. Valid email exist in system or not
        ApplicationUser user = applicationUserRepository.findByEmail(email);
        if(user == null) {
            throw new UserDoesNotExistException(String.format("User with this id %s does not exist in system", email));
        }
        //2. validate if user type is HallOwner/not, if not we'll throw an Unauthorized exception
        if(!user.getType().equals("HallOwner")) {
            throw new UnAuthorized(String.format("User with this email %s does not have permission to access/edit", email));
        }
        //3. If hallId is existing in DB/not
        UUID hallId = addShowDTO.getHallId();
        Hall hall = getHallById(hallId);
        if(hall == null) {
            throw new ResourcesNotExistException(String.format("Hall with id %s does not exist in system", hallId.toString()));
        }
        //4. Check if user trying to add show in the hall which is owns by him/not by id checking
        if(hall.getOwner().getId() != user.getId()) {
            throw new UnAuthorized(String.format("User with emailId %s does not own hall with this hallId %s", email, hallId.toString()));
        }
        //5. Check if movieId which we got from AddShowDTO is existing in DB if not then ResourceNotFound error
        UUID movieId = addShowDTO.getMovieId();
        Movie movie = movieService.getMovieById(movieId);
        if(movie == null) {
            throw new ResourcesNotExistException(String.format("Movie with movieId %s does not exist in system", movieId.toString()));
        }
        //All the validation passed
        //1. Get screens that are not occupied
        List<Screen> screens = new ArrayList<>();
        for (Screen screen : hall.getScreens()) {
            if (screen.isStatus() == false) {
                screens.add(screen);
            }
        }
        //If after the for loop list screens having size as 0, then we have to throw 1 exception ie; ResourceNotfound
        if(screens.size() == 0) {
            throw new ResourcesNotExistException(String.format("Hall with hallID %s does not have any unoccupied screen", hallId.toString()));
        }
        Screen screen = screens.get(0);
        //Setting all the properties for the show
        Show show = new Show();
        show.setHall(hall);
        show.setMovie(movie);
        show.setAvailableTickets(screen.getScreenCapacity());
        show.setTicketPrice(addShowDTO.getTicketPrice());
        show.setScreen(screen); //allocating screen to show

        Date startDateTime = new Date();
        startDateTime.setHours(addShowDTO.getHour());
        startDateTime.setMinutes(addShowDTO.getMinutes());

        Date endDateTime = new Date();
        int hour = (int)(addShowDTO.getHour() + movie.getDuration() % 24);
        endDateTime.setHours(hour);
        endDateTime.setMinutes(addShowDTO.getMinutes());

        show.setStartTime(startDateTime);
        show.setEndTime(endDateTime);

        //Mark status of screen as true, no other show book that screen
        screenService.bookScreen(screen.getId());
        showService.createShow(show);

        return show;
    }
}

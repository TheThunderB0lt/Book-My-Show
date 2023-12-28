package com.driver.BookMyShow.service;

import com.driver.BookMyShow.dto.request.AddScreenDTO;
import com.driver.BookMyShow.dto.request.HallOwnerSignupDTO;
import com.driver.BookMyShow.exceptions.ResourcesNotExistException;
import com.driver.BookMyShow.exceptions.UnAuthorized;
import com.driver.BookMyShow.exceptions.UserDoesNotExistException;
import com.driver.BookMyShow.models.ApplicationUser;
import com.driver.BookMyShow.models.Hall;
import com.driver.BookMyShow.models.Screen;
import com.driver.BookMyShow.repository.ApplicationUserRepository;
import com.driver.BookMyShow.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createShow(AddScreenDTO addScreenDTO) {

    }
}

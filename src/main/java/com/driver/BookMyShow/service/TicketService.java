package com.driver.BookMyShow.service;

import com.driver.BookMyShow.exceptions.ResourcesNotExistException;
import com.driver.BookMyShow.exceptions.UnAuthorized;
import com.driver.BookMyShow.exceptions.UserDoesNotExistException;
import com.driver.BookMyShow.models.*;
import com.driver.BookMyShow.repository.ApplicationUserRepository;
import com.driver.BookMyShow.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ShowService showService;

    @Autowired
    MovieService movieService;

    @Autowired
    HallService hallService;

    @Autowired
    MailService mailService;

    @Autowired
    BarCodeGenerationService barCodeGenerationService;

    public Ticket buyTicket(String email, UUID showId) {
        //1. get user by email
        ApplicationUser user = applicationUserRepository.findByEmail(email);
        //If user is null, no user is existing with this email id
        if(user == null) {
            throw new UserDoesNotExistException(String.format("User with email %s does not exist in system", email));
        }

        //2. check user has a required access of type --> Only regularUser
        if(!user.getType().equals("RegularUser")) {
            throw new UnAuthorized(String.format("User with email %s does not have access", email));
        }

        //3. Validate show with showID
        Show show = showService.getShowByShowId(showId);
        if (show == null) {
            throw new ResourcesNotExistException(String.format("Show with %s does not exist in our system", showId));
        }

        //we have to decrease ticket count for a particular showID, as we're buying on ticket
        showService.updateAvailableTicketCount(show);
        Ticket ticket = new Ticket();
        ticket.setHall(show.getHall());
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setUser(user);
        ticketRepository.save(ticket);

        Movie movie = movieService.getMovieById(show.getMovie().getId());
        Hall hall = hallService.getHallById(show.getHall().getId());

        //First send ticket details to user
        String userMessage = String.format("Hey %s,\n" +
                "Congratulations! Your ticket got booked on our MovieBooker. Below are your ticket details.\n \n" +
                "1. Movie Name: %s\n" +
                "2. Hall Name : %s\n" +
                "3. Hall Address : %s\n" +
                "4. Date and Time : %s\n" +
                "5. Ticket Price : %d\n \n" +
                "Hope you enjoy the show!\n \n \n" +
                "\nMovieBooker", user.getName(), movie.getName(), hall.getName(), hall.getAddress(), show.getStartTime().toString(), show.getTicketPrice());

        String userSub = String.format("Congratulation! %s your ticket got generated", user.getName());

        //Whatever text we passed, we'll generate QRCode for that text
        //when user scan that QRCode, it'll show all the details of the movie ticket.
        try {
            barCodeGenerationService.generateQR(userMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Sending generated QRCode image to user email
        mailService.generateMail(user.getEmail(), userSub, userMessage, "./src/main/resources/static/QRCode.png");

        //We'll generate mail for the above text, it'll send that mail text to user
//        mailService.generateMail(user.getEmail(), userSub, userMessage);

        // We are generating mail for movie owner for total income & ticket sold
        int totalTickets = movieService.getTotalTicketCount(movie);
        int totalIncome = movieService.boxOfficeCollection(movie);

        String movieMessage = String.format("Congratulations %s,\n" +
                "\nYour movie ticket got sold \n" +
                "\n1. Total Ticket Sold: %d" +
                "\n2. Your Total Income : %d", movie.getOwner().getName(), totalTickets, totalIncome);

        String movieSubject = String.format("Congratulations %s One more ticket sold", movie.getOwner().getName());

        mailService.generateMail(movie.getOwner().getEmail(), movieSubject, movieMessage);

        return ticket;
    }
}

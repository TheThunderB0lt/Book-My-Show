package com.driver.BookMyShow.controller;

import com.driver.BookMyShow.models.Ticket;
import com.driver.BookMyShow.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Ticket API", description = "This controller contains all the Ticket related service endpoint details.")
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("/buyticket")
    public ResponseEntity buyTicket(@RequestParam String email, @RequestParam UUID showId) {
        Ticket ticket = ticketService.buyTicket(email, showId);
        return new ResponseEntity("Tickets got created successfully", HttpStatus.CREATED);
    }
}

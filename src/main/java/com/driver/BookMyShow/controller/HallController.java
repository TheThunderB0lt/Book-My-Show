package com.driver.BookMyShow.controller;

import com.driver.BookMyShow.dto.request.AddScreenDTO;
import com.driver.BookMyShow.dto.request.AddShowDTO;
import com.driver.BookMyShow.dto.request.GeneralMessageDTO;
import com.driver.BookMyShow.dto.request.HallOwnerSignupDTO;
import com.driver.BookMyShow.exceptions.ResourcesNotExistException;
import com.driver.BookMyShow.exceptions.UnAuthorized;
import com.driver.BookMyShow.exceptions.UserDoesNotExistException;
import com.driver.BookMyShow.models.ApplicationUser;
import com.driver.BookMyShow.models.Show;
import com.driver.BookMyShow.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hall")
public class HallController {
    @Autowired
    HallService hallService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody HallOwnerSignupDTO hallOwnerSignupDTO) {
        ApplicationUser user = hallService.signup(hallOwnerSignupDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PostMapping("/addscreen")
    public ResponseEntity addScreen(@RequestBody AddScreenDTO addScreenDTO, @RequestParam String email) {
        try {
            hallService.addScreens(addScreenDTO, email);
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404
        } catch (UnAuthorized e) {
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED); //401
        } catch (ResourcesNotExistException e) {
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus. NOT_FOUND); //404
        }
        return new ResponseEntity(new GeneralMessageDTO("Screens added successfully") ,HttpStatus.CREATED); //201
    }

    @PostMapping("/addshow")
    public ResponseEntity addShow(@RequestBody AddShowDTO addShowDTO, @RequestParam String email) {
        try {
            Show show = hallService.createShow(addShowDTO, email);
            return new ResponseEntity(show, HttpStatus.CREATED); //201
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404
        } catch (UnAuthorized e) {
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED); //401
        } catch (ResourcesNotExistException e) {
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404
        }
    }
}

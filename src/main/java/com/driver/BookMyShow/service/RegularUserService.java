package com.driver.BookMyShow.service;

import com.driver.BookMyShow.dto.request.RegularUserSignupDTO;
import com.driver.BookMyShow.models.ApplicationUser;
import com.driver.BookMyShow.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularUserService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public ApplicationUser signup(RegularUserSignupDTO regularUserSignupDTO) {
        ApplicationUser user = new ApplicationUser();
        user.setAge(regularUserSignupDTO.getAge());
        user.setName(regularUserSignupDTO.getName());
        user.setEmail(regularUserSignupDTO.getEmail());
        user.setPassword(regularUserSignupDTO.getPassword());
        user.setPhoneNo(regularUserSignupDTO.getPhoneNo());
        user.setType(regularUserSignupDTO.getType().toString());

        applicationUserRepository.save(user); //saving ownerID
        return user;
    }

    public ApplicationUser getUserByEmail(String email) {
        return applicationUserRepository.findByEmail(email);
    }
}

package com.driver.BookMyShow.repository;

import com.driver.BookMyShow.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    public ApplicationUser findByEmail(String email);
}

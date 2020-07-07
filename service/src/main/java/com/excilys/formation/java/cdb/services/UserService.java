package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.User;
import com.excilys.formation.java.cdb.persistence.daos.UserDAO;

import exceptions.NotFoundInDatabaseException;

@Service
public class UserService {

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public void create(User user) {
        userDAO.create(user);
    }
    
    private User findById(String username) {
        Optional<User> user = userDAO.findByUsername(username);
        if (!user.isPresent()) {
            throw new NotFoundInDatabaseException ("The user is not found in the database.");
        }
        else {
            return user.get();
        }
    }
    
    public boolean validAuthentication (User user) {
        boolean autorize = false;
        if (user != null && user.getPassword() != null && !user.getPassword().isEmpty()) {
            User userInDb = findById(user.getUsername());
            autorize = passwordEncoder.matches(user.getPassword(), userInDb.getPassword());
        }
        return autorize;
    }

}

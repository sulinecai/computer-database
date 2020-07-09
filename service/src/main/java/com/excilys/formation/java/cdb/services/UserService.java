package com.excilys.formation.java.cdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.models.User;
import com.excilys.formation.java.cdb.persistence.daos.UserDAO;

import exceptions.NotFoundInDatabaseException;

@Service
public class UserService implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public void create(User user) {
        userDAO.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new NotFoundInDatabaseException("username not found");
        }
        User user = userOpt.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
    
//    public boolean validAuthentication (User user) {
//        boolean autorize = false;
//        if (user != null && user.getPassword() != null && !user.getPassword().isEmpty()) {
//            User userInDb = findById(user.getUsername());
//            autorize = passwordEncoder.matches(user.getPassword(), userInDb.getPassword());
//        }
//        return autorize;
//    }
}
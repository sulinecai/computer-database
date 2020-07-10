package com.excilys.formation.java.cdb.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.cdb.dtos.UserDTO;
import com.excilys.formation.java.cdb.models.User;

public class UserMapper {

    private static Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public static User toUser(UserDTO dto) {
        User user = new User();
        try {
            if (dto.getId() != null && !dto.getId().isEmpty()) {
                user.setId(Long.valueOf(dto.getId()));
            }
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user.setRole(dto.getRole());
        } catch (NullPointerException e) {
            logger.error("id user when converting a userDTO to a user", e);
        }
        return user;
    }
}

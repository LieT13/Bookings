package com.lietsoft.bookingmanager.repositories;

import com.lietsoft.bookingmanager.model.User;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;

import java.util.Map;

public interface UsersRepository {

    Map<String, User> findAllUsers();

    User findUserById(String id) throws NotFoundException;

    boolean userExists(String id);

    void addUser(User user);

    void updateUser(String id, User user) throws NotFoundException;

    void deleteUser(String id) throws NotFoundException;

}

package com.lietsoft.bookings.repositories;

import com.lietsoft.bookings.model.User;
import com.lietsoft.bookings.model.exception.NotFoundException;

import java.util.Map;

public interface UsersRepository {

    Map<String, User> findAllUsers();

    User findUserById(String id) throws NotFoundException;

    boolean userExists(String id);

    void addUser(User user);

    void updateUser(String id, User user) throws NotFoundException;

    void deleteUser(String id) throws NotFoundException;

}

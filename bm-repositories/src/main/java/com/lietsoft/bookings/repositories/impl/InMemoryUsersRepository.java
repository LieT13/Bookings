package com.lietsoft.bookings.repositories.impl;

import com.lietsoft.bookings.model.User;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.UsersRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryUsersRepository implements UsersRepository {

    private static final Map<String, User> users =  Collections.synchronizedMap(new HashMap<String, User>());

    @Override
    public Map<String, User> findAllUsers() {
        return users;
    }

    @Override
    public User findUserById(String id) throws NotFoundException {
        synchronized (users) {
            if (users.get(id) == null) {
                throw new NotFoundException("User does not exist!");
            }
            return users.get(id);
        }
    }

    @Override
    public boolean userExists(String id) {
        return users.containsKey(id);
    }

    @Override
    public void addUser(User user) {
        users.put(UUID.randomUUID().toString(), user);
    }

    @Override
    public void updateUser(String id, User user) throws NotFoundException {
        synchronized (users) {
            if (users.get(id) == null) {
                throw new NotFoundException("User does not exist!");
            }
            users.put(id, user);
        }
    }

    @Override
    public void deleteUser(String id) throws NotFoundException {
        synchronized (users) {
            if (users.get(id) == null) {
                throw new NotFoundException("User does not exist!");
            }
            users.remove(id);
        }
    }

    public static void setUsers(Map<String, User> newUsers) {
        users.clear();
        users.putAll(newUsers);
    }

}

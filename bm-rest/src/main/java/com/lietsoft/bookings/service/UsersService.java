package com.lietsoft.bookings.service;

import com.lietsoft.bookings.model.User;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.impl.InMemoryUsersRepository;
import com.lietsoft.bookings.repositories.UsersRepository;

import java.util.Map;

public class UsersService {

    private UsersRepository usersRepository;

    public UsersService() {
        usersRepository = new InMemoryUsersRepository();
    }

    public Map<String, User> getAllUsers() {
        return usersRepository.findAllUsers();
    }

    public User getUser(String userId) throws NotFoundException {
        if (!usersRepository.userExists(userId)) {
            throw new NotFoundException("User does not exist");
        }
        return usersRepository.findUserById(userId);
    }

    public void addUser(User user) {
        usersRepository.addUser(user);
    }

    public void updateUser(String userId, User user) throws NotFoundException {
        if (!usersRepository.userExists(userId)) {
            throw new NotFoundException("User does not exist");
        }
        User oldUser = usersRepository.findUserById(userId);
        updateUserData(oldUser, user);
    }

    public void deleteUser(String userId) throws NotFoundException {
        if (!usersRepository.userExists(userId)) {
            throw new NotFoundException("User does not exist");
        }
        usersRepository.deleteUser(userId);
    }

    private void updateUserData(User oldUser, User user) {
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getAddress() != null) {
            oldUser.setAddress(user.getAddress());
        }
    }

    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

}

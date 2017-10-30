package com.lietsoft.bookings.service;

import com.lietsoft.bookings.model.User;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.UsersRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersServiceTest {

    private static final int NUM_USERS = 2;

    private UsersService usersService;

    private UsersRepository mockUsersRepository;

    @Before
    public void setUp() {
        usersService = new UsersService();
        mockUsersRepository = mock(UsersRepository.class);
        usersService.setUsersRepository(mockUsersRepository);
    }

    @Test
    public void testGetAllUsers() {
        when(mockUsersRepository.findAllUsers()).thenReturn(initUsers());
        Map<String, User> allUsers = usersService.getAllUsers();
        assertTrue(allUsers.size() == NUM_USERS);
    }

    @Test
    public void testUpdateUser() {
        when(mockUsersRepository.findAllUsers()).thenReturn(initUsers());
        try {
            usersService.updateUser("0", new User("newUser", "address1"));
            assertEquals("newUser", usersService.getAllUsers().get("0").getName());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateNotFoundUser() {
        when(mockUsersRepository.findAllUsers()).thenReturn(initUsers());
        try {
            usersService.updateUser("5", new User("newUser", "address1"));
            fail("NotFoundException not thrown for not found user!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testDeleteUser() {
        when(mockUsersRepository.findAllUsers()).thenReturn(initUsers());
        try {
            usersService.deleteUser("0");
            assertNull(usersService.getAllUsers().get("0"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteNotFoundUser() {
        when(mockUsersRepository.findAllUsers()).thenReturn(initUsers());
        try {
            usersService.deleteUser("5");
            fail("NotFoundException not thrown for not found user!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    private HashMap<String, User> initUsers() {
        HashMap<String, User> users = new HashMap<>();
        for (long i=0; i<NUM_USERS; i++) {
            users.put(String.valueOf(i), new User("user" + i, "address" + i));
        }
        return users;
    }

}

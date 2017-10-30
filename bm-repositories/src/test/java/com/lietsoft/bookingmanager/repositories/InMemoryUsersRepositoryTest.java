package com.lietsoft.bookingmanager.repositories;

import com.lietsoft.bookingmanager.model.User;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;
import com.lietsoft.bookingmanager.repositories.impl.InMemoryUsersRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class InMemoryUsersRepositoryTest {

    private static final long NUM_USERS = 2;
    private InMemoryUsersRepository usersRepository;

    @Before
    public void setUp() {
        usersRepository = new InMemoryUsersRepository();
    }

    @Test
    public void testFindAllUsers() {
        InMemoryUsersRepository.setUsers(initUsers());
        Map<String, User> allUsers = usersRepository.findAllUsers();
        assertTrue(allUsers.size() == NUM_USERS);
    }

    @Test
    public void testFindUserById() {
        InMemoryUsersRepository.setUsers(initUsers());
        try {
            User user = usersRepository.findUserById("0");
            assertEquals("user0", user.getName());
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent user!");
        }
    }

    @Test
    public void testFindNotFoundUserById() {
        InMemoryUsersRepository.setUsers(initUsers());
        try {
            usersRepository.findUserById("5");
            fail("NotFoundException not thrown for not found user!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testAddUser() {
        InMemoryUsersRepository.setUsers(initUsers());
        User u = new User("user5", "address0");
        usersRepository.addUser(u);
        assertTrue(usersRepository.findAllUsers().values().contains(u));
    }

    @Test
    public void testUpdateUser() {
        InMemoryUsersRepository.setUsers(initUsers());
        try {
            usersRepository.updateUser("0", createUser("newUser", "address1"));
            assertEquals("newUser", usersRepository.findAllUsers().get("0").getName());
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent user!");
        }
    }

    @Test
    public void testUpdateNotFoundUser() {
        InMemoryUsersRepository.setUsers(initUsers());
        try {
            usersRepository.updateUser("5", createUser("newUser", "address1"));
            fail("NotFoundException not thrown for not found user!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testDeleteUser() {
        InMemoryUsersRepository.setUsers(initUsers());
        try {
            usersRepository.deleteUser("0");
            assertNull(usersRepository.findAllUsers().get("0"));
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent user!");
        }
    }

    @Test
    public void testDeleteNotFoundUser() {
        InMemoryUsersRepository.setUsers(initUsers());
        try {
            usersRepository.deleteUser("5");
            fail("NotFoundException not thrown for not found user!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    private HashMap<String, User> initUsers() {
        HashMap<String, User> users = new HashMap<>();
        for (long i=0; i<NUM_USERS; i++) {
            users.put(String.valueOf(i), createUser("user" + i, "address" + i));
        }
        return users;
    }

    private User createUser(String name, String address) {
        return new User(name, address);
    }

}

package com.lietsoft.bookingmanager.controller;

import com.lietsoft.bookingmanager.model.exception.NotFoundException;
import com.lietsoft.bookingmanager.model.User;
import com.lietsoft.bookingmanager.service.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

public class UsersControllerTest {

    private static final int NUM_USERS = 2;

    private UsersController controller;

    private UsersService mockUsersService;

    @Before
    public void setUp() {
        controller = new UsersController();

        mockUsersService = mock(UsersService.class);
        controller.setUsersService(mockUsersService);
    }

    @Test
    public void testGetAllUsers() {
        when(mockUsersService.getAllUsers()).thenReturn(initUsers());
        Response responseUsers = controller.getAllUsers();
        assertEquals(200, responseUsers.getStatus());
        assertTrue(((HashMap<String, User>)responseUsers.getEntity()).size() == NUM_USERS);
    }

    @Test
    public void testGetUser() {
        try {
            when(mockUsersService.getUser(any())).thenReturn(new User("user1", "myhome"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseUsers = controller.getUser("1");
        assertEquals(200, responseUsers.getStatus());
        assertTrue("user1".equals(((User)responseUsers.getEntity()).getName()));
    }

    @Test
    public void testGetNotFoundUser() {
        try {
            Mockito.doThrow(new NotFoundException("User not found")).when(mockUsersService).getUser(any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseUsers = controller.getUser("1");
        assertEquals(404, responseUsers.getStatus());
    }

    @Test
    public void testCreateUser() {
        Response responseUsers = controller.createUser(new User("user2", "address2"));
        assertEquals(200, responseUsers.getStatus());
    }

    @Test
    public void testUpdateUser() {
        Response responseUsers = controller.updateUser("2", new User("user2", "address2"));
        assertEquals(200, responseUsers.getStatus());
    }

    @Test
    public void testUpdateNotFoundUser() {
        try {
            Mockito.doThrow(new NotFoundException("User does not exist")).when(mockUsersService).updateUser(any(), any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseUsers = controller.updateUser("2", new User("user2", "address2"));
        assertEquals(404, responseUsers.getStatus());
    }

    @Test
    public void testDeleteUser() {
        Response responseUsers = controller.deleteUser("2");
        assertEquals(200, responseUsers.getStatus());
    }

    @Test
    public void testDeleteNotFoundUser() {
        try {
            Mockito.doThrow(new NotFoundException("User does not exist")).when(mockUsersService).deleteUser(any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseUsers = controller.deleteUser("2");
        assertEquals(404, responseUsers.getStatus());
    }

    private HashMap<String, User> initUsers() {
        HashMap<String, User> users = new HashMap<>();
        for (long i=0; i<NUM_USERS; i++) {
            users.put(String.valueOf(i), new User("user" + i, "address" + i));
        }
        return users;
    }

}

package service;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import dao.UserDao;
import exception.NotFoundException;
import model.User;
import model.implementation.UserImpl;
import service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByIdTest() {
        long userId = 1;
        User expected = new UserImpl(userId, "user1", "user1@gmail.com");
        doReturn(expected).when(userDao).getById(userId);
        User actual = userService.getUserById(userId);
        assertEquals(expected, actual);
    }

    @Test
    void getNotExistingUserByIdTest() {
        long userId = 1;
        doReturn(null).when(userDao).getById(userId);
        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getUsersByEmailTest() {
        String email = "user1@gmail.com";
        User expected = new UserImpl(1, "user1", email);
        doReturn(singletonList(expected)).when(userDao).getUsersByEmail(email, 1, 1);
        List<User> actualUsers = userService.getUsersByEmail(email, 1, 1);
        assertEquals(1, actualUsers.size());
        assertEquals(expected, actualUsers.get(0));
    }

    @Test
    void getNotExistingUserByEmailTest() {
        String email = "invalid email";
        doThrow(NotFoundException.class).when(userDao).getUsersByEmail(email, 1, 1);
        assertThrows(NotFoundException.class, () -> userService.getUsersByEmail(email, 1, 1));
    }

    @Test
    void getUsersByNameTest() {
        String name = "user1";
        User expected = new UserImpl(1, name, "user1@gmail.com");
        doReturn(singletonList(expected)).when(userDao).getUsersByName(name, 1, 1);
        List<User> actualUsers = userService.getUsersByName(name, 1, 1);
        assertEquals(1, actualUsers.size());
        assertEquals(expected, actualUsers.get(0));
    }

    @Test
    void getNotExistingUserByNameTest() {
        String name = "invalid name";
        doThrow(NotFoundException.class).when(userDao).getUsersByName(name, 1, 1);
        assertThrows(NotFoundException.class, () -> userService.getUsersByName(name, 1, 1));
    }

    @Test
    void createUserTest() {
        User expected = new UserImpl(1, "user1", "user1@gmail.com");
        doReturn(expected).when(userDao).create(expected);
        User actual = userService.createUser(expected);
        assertEquals(expected, actual);
    }


    @Test
    void createAlreadyExistingUserTest() {
        when(userDao.create(any())).thenThrow(IllegalStateException.class);
        assertThrows(IllegalStateException.class, () -> userService.createUser(any()));
    }

    @Test
    void updateUserTest() {
        User expected = new UserImpl(1, "user", "user@gmail.com");
        doReturn(expected).when(userDao).update(expected);
        User actual = userService.updateUser(expected);
        assertEquals(expected, actual);
    }

    @Test
    void updateNotExistingUserTest() {
        when(userDao.update(any())).thenThrow(IllegalStateException.class);
        assertThrows(IllegalStateException.class, () -> userService.updateUser(any()));
    }

    @Test
    void deleteUserByIdTest() {
        when(userDao.deleteById(1)).thenReturn(true);
        assertTrue(userService.deleteUserById(1));
    }
}
package service;

import model.User;

import java.util.List;

public interface UserService {
    User getUserById(long userId);

    List<User> getUsersByEmail(String name, int pageSize, int pageNum);

    List<User> getUsersByName(String name, int pageSize, int pageNum);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUserById(long userId);
}
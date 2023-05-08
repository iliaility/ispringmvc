package service.implementation;

import dao.UserDao;
import exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.List;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class IUserService implements UserService {
    private final UserDao userDao;

    @Override
    public User getUserById(long userId) {
        return ofNullable(userDao.getById(userId))
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " was not found"));
    }

    @Override
    public List<User> getUsersByEmail(String email, int pageSize, int pageNum) {
        return userDao.getUsersByEmail(email, pageSize, pageNum);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userDao.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        return userDao.create(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean deleteUserById(long userId) {
        return userDao.deleteById(userId);
    }
}
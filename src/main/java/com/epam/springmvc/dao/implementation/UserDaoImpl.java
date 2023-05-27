package com.epam.springmvc.dao.implementation;

import com.epam.springmvc.dao.UserDao;
import com.epam.springmvc.exception.NotFoundException;
import lombok.Setter;
import com.epam.springmvc.model.User;
import com.epam.springmvc.model.implementation.UserImpl;
import com.epam.springmvc.storage.BookingStorage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
@Component
public class UserDaoImpl implements UserDao {
    private BookingStorage bookingStorage;

    public UserDaoImpl(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }

    private List<UserImpl> getUsersData() {
        return (List<UserImpl>) bookingStorage.getData(UserImpl.class);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(bookingStorage.getUsers().values());
    }

    @Override
    public User getById(long id) {
        return getUsersData().stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User create(User user) {
        user.setId(bookingStorage.getNextId(User.class));
        bookingStorage.getUsers().put(user.getId(), user);
        return bookingStorage.getUsers().get(user.getId());
    }

    @Override
    public User update(User user) {
        if (!bookingStorage.getUsers().containsKey(user.getId())){
            throw new NotFoundException("User not found");
        }
        bookingStorage.getUsers().replace(user.getId(),user);
        return user;
    }

    @Override
    public boolean deleteById(long id) {
        if (!bookingStorage.getUsers().containsKey(id)){
            throw new NotFoundException("User not found");
        }
        bookingStorage.getUsers().remove(id);
        return true;
    }

    @Override
    public List<User> getUsersByEmail(String email, int pageSize, int pageNum) {
        List<User> filteredUsers = getUsersData().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        if (filteredUsers.isEmpty()) {
            throw new NotFoundException("User with email " + email + " doesn't exist");
        }
        return filteredUsers;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> filteredUsers = getUsersData().stream()
                .filter(user -> Objects.equals(user.getName(), name))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        if (filteredUsers.isEmpty()) {
            throw new NotFoundException("User with name  " + name + " doesn't exist");
        }
        return filteredUsers;
    }

    public void setBookingStorage(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }
}
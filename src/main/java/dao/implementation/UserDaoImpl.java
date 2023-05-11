package dao.implementation;

import dao.UserDao;
import exception.NotFoundException;
import lombok.Setter;
import model.User;
import model.implementation.UserImpl;
import storage.BookingStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class UserDaoImpl implements UserDao {
    private BookingStorage bookingStorage;

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
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User create(User obj) {
        return bookingStorage.getUsers().put(obj.getId(), obj);
    }

    @Override
    public User update(User obj) {
        return bookingStorage.getUsers().replace(obj.getId(), obj);
    }

    @Override
    public boolean deleteById(long id) {
        bookingStorage.getUsers().remove(id);
        return true;
    }

    @Override
    public List<User> getUsersByEmail(String email, int pageSize, int pageNum) {
        List<User> users = new ArrayList<>(bookingStorage.getUsers().values());
        List<User> filteredUsers = users.stream()
                .filter(user -> user.getEmail().equals(email))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        if (filteredUsers.isEmpty()) {
            throw new NotFoundException("User doesn't exist");
        }
        return filteredUsers;
    }


    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> users = new ArrayList<>(bookingStorage.getUsers().values());
        return users.stream()
                .filter(user -> user.getEmail().equals(name))
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
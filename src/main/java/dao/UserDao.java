package dao;

import model.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    @Override
    User getById(long id);

    @Override
    User create(User obj);

    @Override
    User update(User obj);

    @Override
    boolean deleteById(long id);

    List<User> getUsersByEmail(String name, int pageSize, int pageNum);

    List<User> getUsersByName(String name, int pageSize, int pageNum);
}

package dao;

import java.util.List;

/** Interface for data access objects. */
public interface Dao<T> {

  T getById(long id);

  List<T> readAll();

  T create(T obj);

  T update(T obj);

  boolean deleteById(long id);

}
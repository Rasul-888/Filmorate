package kz.filmorate.storage;

import kz.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User add(User user);
    User update(User user);
    void delete(Long id);
    Collection<User> getAll();
    User getById(Long id);
}

package kz.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.filmorate.exception.NotFoundException;
import kz.filmorate.model.User;
import kz.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.getAll();
    }

    public User create(User user) {
        validateUser(user);
        log.info("Создан новый пользователь: {}", user.getLogin());
        return userStorage.add(user);
    }

    public User update(User user) {
        if (userStorage.getById(user.getId()) == null) {
            log.error("Пользователь с id {} не найден для обновления", user.getId());
            throw new NotFoundException("Пользователь с id " + user.getId() + " не найден.");
        }
        validateUser(user);
        log.info("Обновлен пользователь с id: {}", user.getId());
        return userStorage.update(user);
    }

    public User getById(Long id) {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
        }
        return user;
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Пользователю принудительно установлено имя, равное логину: {}", user.getLogin());
        }
    }
}
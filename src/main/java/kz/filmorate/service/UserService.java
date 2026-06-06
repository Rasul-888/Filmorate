package kz.filmorate.service;

import kz.filmorate.model.User;
import kz.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId); // Дружба взаимная по ТЗ
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public Collection<User> getFriends(Long userId) {
        User user = userStorage.getById(userId);
        return user.getFriends().stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(Long userId, Long otherId) {
        User user = userStorage.getById(userId);
        User other = userStorage.getById(otherId);

        return user.getFriends().stream()
                .filter(other.getFriends()::contains)
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        return userStorage.add(user);
    }
    public User updateUser(User user) {
        return userStorage.update(user);
    }
    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }
    public User getUserById(Long id) {
        return userStorage.getById(id);
    }
}
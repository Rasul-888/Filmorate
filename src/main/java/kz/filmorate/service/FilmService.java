package kz.filmorate.service;

import kz.filmorate.model.Film;
import kz.filmorate.storage.FilmStorage;
import kz.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        userStorage.getById(userId); // Выбросит 404, если юзера нет
        film.getLikes().add(userId);
    }

    public void removeLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        userStorage.getById(userId);
        film.getLikes().remove(userId);
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmStorage.getAll().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(),
                        f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public Film getFilmById(Long id) {
        return filmStorage.getById(id);
    }
}
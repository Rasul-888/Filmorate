package kz.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.filmorate.exception.NotFoundException;
import kz.filmorate.exception.ValidationException;
import kz.filmorate.model.Film;
import kz.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.getAll();
    }

    public Film create(Film film) {
        validateFilm(film);
        log.info("Добавлен новый фильм: {}", film.getName());
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (filmStorage.getById(film.getId()) == null) {
            log.error("Фильм с id {} не найден для обновления", film.getId());
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден.");
        }
        validateFilm(film);
        log.info("Обновлен фильм с id: {}", film.getId());
        return filmStorage.update(film);
    }

    public Film getById(Long id) {
        Film film = filmStorage.getById(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + id + " не найден.");
        }
        return film;
    }

    private void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            log.warn("Валидация провалена: дата релиза раньше 28.12.1895");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года.");
        }
    }
}
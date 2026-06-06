package kz.filmorate.storage;

import kz.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film add(Film film);
    Film update(Film film);
    void delete(Long id);
    Collection<Film> getAll();
    Film getById(Long id);
}

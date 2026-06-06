package kz.filmorate.storage;

import kz.filmorate.exception.NotFoundException;
import kz.filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 1;

    @Override
    public Film add(Film film) {
        film.setId(idCounter++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void delete(Long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        films.remove(id);
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Film getById(Long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        return films.get(id);
    }
}
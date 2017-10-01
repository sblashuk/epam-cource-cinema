package com.epam.cinema.dao;

import com.epam.cinema.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticDataUserDao implements UserDao {

    private Map<Long, User> users = new HashMap<>();

    private static long ID_COUNTER = 0;

    public void setStartValues(List<User> users) {
        users.forEach(this::save);
    }

    @Override
    public Long save(User user) {
        user.setId(ID_COUNTER++);
        users.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public void remove(Long id) {
        users.remove(id);
    }

    @Override
    public User getById(Long id) {
        return users.get(id);
    }

    @Override
    public User getByEmail(String email) {
        return users.values().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }
}
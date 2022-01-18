package dao;

import models.User;

import java.util.List;

public interface UserDao {

    //CREATE
    void save(User user);

    //READ
    User findById(int id);
    List<User> getAll();

    //DELETE
    void clearAll();
}


package dao;

import models.News;

import java.util.List;

public interface NewsDao {

    //CREATE
    void save(News news);

    //READ
    News findById(int id);
    List<News> getAll();

    //DELETE
    void clearAll();

}

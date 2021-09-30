package dao;

import java.util.List;

public interface NewsDao {
    List<MyNews> getAllNews();
    List<MyNews> getNews();
    List<DepartmentMyNews> getDepartmentNews();

    void addGeneralNews(MyNews myNews);
    void addDepartmentNews(DepartmentMyNews dptNews);

    MyNews findGeneralNewsById(int id);
    DepartmentMyNews findDepartmentNewsById(int id);

    void updateGeneralNews(MyNews myNews, int userId, String content);
    void updateDepartmentNews(DepartmentMyNews dptNews, int userId, String content, int departmentId);

    void clearAllNews();
    void clearNews();
    void clearDepartmentNews();
}

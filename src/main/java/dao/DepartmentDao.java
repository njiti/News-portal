package dao;

import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface DepartmentDao {

    //CREATE
    void save(Department department);
    void saveNewsAndDepartment(Department department, News news);
    void saveUsersAndDepartment(Department department, User user);

    //READ
    Department findById(int id);
    List<Department> getAll();
    List<News> getAllDepartmentNews(int departmentId);
    List<User> getAllDepartmentUsers(int departmentId);

    //DESTROY
    void clearAll();
    void clearAllNewsAndDepartments();
    void clearAllUsersAndDepartments();
}


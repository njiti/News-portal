package dao;
import models.DepartmentMyNews;
import models.MyDepartment;
import models.MyUser;

import java.util.List;
public interface DepartmentDao {
    List<MyDepartment> getAllDepartments();
    List<MyUser> getMyDepartmentUsersById(int id);
    List<DepartmentMyNews> getDepartmentNewsById(int id);
    void addDepartment(MyDepartment myDepartment);
    MyDepartment findDepartmentById(int id);
    void updateDepartment(MyDepartment myDepartment, String name, String description);
    void clearAllDepartments();
}


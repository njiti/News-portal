package dao;

import models.Department;
import models.News;
import models.User;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao {

    private final Sql2o sql2o;

    public Sql2oDepartmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void save(Department department) {
        try(Connection con = sql2o.open()) {
            String sql = "INSERT INTO departments (name, description, noofemployees) VALUES (:name, :description, :noOfEmployees)";
            int id = (int) con.createQuery(sql)
                    .bind(department)
                    .addParameter("name", department.getName())
                    .addParameter("description", department.getDescription())
                    .addParameter("noOfEmployees", department.getNoOfEmployees())
                    .executeUpdate().getKey();
            department.setId(id);
        }
    }

    @Override
    public Department findById(int id) {
        try(Connection con = sql2o.open()) {
            String sql = "SELECT * FROM departments WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", id).executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public List<Department> getAll() {
        try(Connection con = sql2o.open()) {
            String sql = "SELECT * FROM departments";
            return con.createQuery(sql).executeAndFetch(Department.class);
        }
    }

    @Override
    public void clearAll() {
        String sql = "TRUNCATE TABLE departments";
        String sql2 = "ALTER SEQUENCE departments_id_seq RESTART";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
            con.createQuery(sql2).executeUpdate();
        } catch (Sql2oException e) {
            System.out.println(e);
        }
    }

    @Override
    public void saveNewsAndDepartment(Department department, News news) {
        try(Connection con = sql2o.open()) {
            String sql = "INSERT INTO departments_news (departmentid, newsid) VALUES (:departmentId, :newsId)";
            con.createQuery(sql).addParameter("departmentId", department.getId())
                    .addParameter("newsId", news.getId()).executeUpdate();
        } catch(Sql2oException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<News> getAllDepartmentNews(int departmentId) {
        List<News> departmentNews = new ArrayList<>();
        String sql1 = "SELECT newsid FROM departments_news WHERE departmentid = :departmentId";
        try(Connection con = sql2o.open()) {
            List<Integer> newsIds = con.createQuery(sql1)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Integer.class);

            for(Integer newsId : newsIds) {
                String sql2 = "SELECT * FROM news WHERE id = :newsId";
                departmentNews.add(con.createQuery(sql2)
                        .addParameter("newsId", newsId).executeAndFetchFirst(News.class));
            }
        } catch (Sql2oException e) {
            System.out.println(e);
        }
        return departmentNews;
    }

    @Override
    public void saveUsersAndDepartment(Department department, User user) {
        try(Connection con = sql2o.open()) {
            String sql = "INSERT INTO departments_users (departmentid, userid) VALUES (:departmentId, :userId)";
            con.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("userId", user.getId())
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<User> getAllDepartmentUsers(int departmentId) {
        List<User> departmentUsers = new ArrayList<>();
        String sql1 = "SELECT userid FROM departments_users WHERE departmentid = :departmentId";
        try(Connection con = sql2o.open()) {
            List<Integer> userIds = con.createQuery(sql1)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Integer.class);

            for (Integer userId : userIds) {
                String sql2 = "SELECT * FROM users WHERE id = :userId";
                departmentUsers.add(con.createQuery(sql2).addParameter("userId", userId).executeAndFetchFirst(User.class));
            }
        } catch (Sql2oException e) {
            System.out.println(e);
        }
        return departmentUsers;
    }

    @Override
    public void clearAllNewsAndDepartments() {
        String sql1 = "TRUNCATE TABLE departments_news";
        String sql2 = "ALTER SEQUENCE departments_news_id_seq RESTART";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql1).executeUpdate();
            con.createQuery(sql2).executeUpdate();
        } catch (Sql2oException e) {
            System.out.println(e);
        }
    }

    @Override
    public void clearAllUsersAndDepartments() {
        String sql1 = "TRUNCATE TABLE departments_users";
        String sql2 = "ALTER SEQUENCE departments_users_id_seq RESTART";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql1).executeUpdate();
            con.createQuery(sql2).executeUpdate();
        }
    }

}

package dao;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

public class sql20NewsDao implements NewsDao{
    private final Sql2o sql2o;
    public static final String GENERAL_NEWS="general";
    public static final String DEPARTMENT_NEWS="department";

    public MySql2OMyNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<MyNews> getAllNews() {

        List<MyNews> myNews = new ArrayList<>();
        myNews.addAll(getNews());
        myNews.addAll(getDepartmentNews());
        return myNews;
    }

    @Override
    public List<MyNews> getNews() {
        String sql = "select * from news where type=:type";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("type",GENERAL_NEWS)
                    .executeAndFetch(MyNews.class);
        }

    }

    @Override
    public List<DepartmentMyNews> getDepartmentNews() {
        String sql = "select * from news where type=:type";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("type",DEPARTMENT_NEWS)
                    .executeAndFetch(DepartmentMyNews.class);
        }
    }

    @Override
    public void addGeneralNews(MyNews myNews) {
        String sql = "insert into news (userId,type,content,postdate) values (:userId,:type,:content,now()) ";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                    .addParameter("userId", myNews.getUserId())
                    .addParameter("type", myNews.getType())
                    .addParameter("content", myNews.getContent())
                    .executeUpdate().getKey();
            myNews.setId(id);
        }
    }

    @Override
    public void addDepartmentNews(DepartmentMyNews dptNews) {
        String sql =" insert into news (userId,type,content,postdate,departmentId) values (:userId,:type,:content,now(),:departmentId) ";
        try(Connection con = sql2o.open()){
            int id = (int)  con.createQuery(sql,true)
                    .addParameter("userId", dptNews.getUserId())
                    .addParameter("type",dptNews.getType())
                    .addParameter("content",dptNews.getContent())
                    .addParameter("departmentId",dptNews.getDepartmentId())
                    .executeUpdate().getKey();
            dptNews.setId(id);
        }
    }

    @Override
    public MyNews findGeneralNewsById(int id) {
        String sql = " select * from news where type=:type and id=:id";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("type",GENERAL_NEWS)
                    .addParameter("id",id)
                    .executeAndFetchFirst(MyNews.class);
        }

    }

    @Override
    public DepartmentMyNews findDepartmentNewsById(int id) {
        String sql = " select * from news where type=:type and id=:id";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("type",DEPARTMENT_NEWS)
                    .addParameter("id",id)
                    .executeAndFetchFirst(DepartmentMyNews.class);
        }
    }

    @Override
    public void updateGeneralNews(MyNews myNews, int userId, String content) {
        String sql = "update news set (userId, content) = (:userId, :content)  where id=:id ";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId",userId)
                    .addParameter("content",content)
                    .addParameter("id", myNews.getId())
                    .executeUpdate();
            myNews.setUserId(userId);
            myNews.setContent(content);
        }

    }

    @Override
    public void updateDepartmentNews(DepartmentMyNews dptNews, int userId, String content, int departmentId) {
        String sql = "update news set (userId, content,departmentId) = (:userId,  :content,:departmentId)  where id=:id ";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId",userId)
                    .addParameter("content",content)
                    .addParameter("departmentId",departmentId)
                    .addParameter("id",dptNews.getId())
                    .executeUpdate();
            dptNews.setUserId(userId);
            dptNews.setContent(content);
            dptNews.setDepartmentId(departmentId);
        }
    }

    @Override
    public void clearAllNews() {
        String sql="delete from news";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }

    @Override
    public void clearNews() {
        String sql="delete from news where type = :type";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("type",GENERAL_NEWS)
                    .executeUpdate();
        }
    }

    @Override
    public void clearDepartmentNews() {
        String sql="delete from news where type = :type";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("type",DEPARTMENT_NEWS)
                    .executeUpdate();
        }
    }
}

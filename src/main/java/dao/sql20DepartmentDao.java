package dao;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.stream.Collectors;

public class sql20DepartmentDao implements DepartmentDao {
    private final Sql2o sql2o;
    private final MySql2OMyUserDao userDao;
    private final MySql2OMyNewsDao newsDao;

    public MySql2OMyDepartmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.userDao = new MySql2OMyUserDao(sql2o);
        this.newsDao = new MySql2OMyNewsDao(sql2o);

    }



    @Override
    public List<MyUser> getMyDepartmentUsersById(int id) {
        return userDao.getAllUsers().stream()
                .filter(user -> user.getDepartmentId()==id )
                .collect(Collectors.toList());
    }
    @Override
    public List<MyDepartment> getAllDepartments() {
        String sql ="select * from departments";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(MyDepartment.class);
        }

    }
    @Override
    public List<DepartmentMyNews> getDepartmentNewsById(int id) {
        return newsDao.getDepartmentNews().stream()
                .filter(dpt->dpt.getDepartmentId()==id)
                .collect(Collectors.toList());
    }

    @Override
    public void addDepartment(MyDepartment myDepartment) {
        String sql = "insert into departments (name,description) values (:name,:description) ";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                    .bind(myDepartment)
                    .executeUpdate()
                    .getKey();
            myDepartment.setId(id);
        }
    }

    @Override
    public MyDepartment findDepartmentById(int id) {
        String sql ="select * from departments where id=:id";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(MyDepartment.class);
        }

    }

    @Override
    public void updateDepartment(MyDepartment myDepartment, String name, String description) {
        String sql ="update departments set (name, description) = (:name, :description) ";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("description",description)
                    .executeUpdate();
            myDepartment.setName(name);
            myDepartment.setDescription(description);
        }
    }

    public List<MyDepartment.MyDepartmentWithUserCount> getDepartmentWithUserCount(){
        return getAllDepartments().stream()
                .map(dpt->
                        new MyDepartment.MyDepartmentWithUserCount(
                                dpt.getId(),
                                dpt.getName(),
                                dpt.getDescription(),
                                getMyDepartmentUsersById(dpt.getId()).size()
                        )).collect(Collectors.toList());
    }
    @Override
    public void clearAllDepartments() {
        String sql =" delete from departments";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
}

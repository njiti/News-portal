package dao;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class sqy20UserDao implements UserDao{
    private final Sql2o sql2o;
    public MySql2OMyUserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<MyUser> getAllUsers() {
        String sql="select * from users";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql).executeAndFetch(MyUser.class);
        }

    }

    @Override
    public void addUser(MyUser myUser) {
        String sql ="insert into users (name, position, role, departmentId) values (:name, :position, :role, :departmentId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true)
                    .bind(myUser)
                    .executeUpdate()
                    .getKey();
            myUser.setId(id);
        }
    }

    @Override
    public MyUser findUserById(int id) {
        String sql ="select * from users where id = :id ";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(MyUser.class);
        }
    }

    @Override
    public void updateUser(MyUser myUser, String name, String position, String role, int departmentId) {
        String sql = "update users set  (name,position,role,departmentId) = (:name,:position,:role,:departmentId) where id= :id ";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("position",position)
                    .addParameter("role",role)
                    .addParameter("departmentId",departmentId)
                    .addParameter("id", myUser.getId())
                    .executeUpdate();

            myUser.setName(name);
            myUser.setPosition(position);
            myUser.setRole(role);
            myUser.setDepartmentId(departmentId);
        }
    }

    @Override
    public void clearAllUsers() {
        String sql = "delete from users";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
}

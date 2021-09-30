package dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class sql20DepertmentDaoTest {
    private static MySql2OMyDepartmentDao dptDao;
    private static MySql2OMyUserDao userDao;
    private static MySql2OMyNewsDao newsDao;
    private static Connection con;
    @BeforeClass
    public static void setUp() throws Exception {
        String connectionStr="jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionStr,"japhethnyaranga","34120648");

        dptDao = new MySql2OMyDepartmentDao(sql2o);
        userDao = new MySql2OMyUserDao(sql2o);
        newsDao = new MySql2OMyNewsDao(sql2o);
        con = sql2o.open();

        dptDao.clearAllDepartments();
        userDao.clearAllUsers();
    }

    @After
    public void tearDown() throws Exception { dptDao.clearAllDepartments(); userDao.clearAllUsers();}

    @AfterClass
    public static void shutDown() throws Exception { con.close(); }

    @Test
    public void if_method_returns_all_departments_True() {
        MyDepartment d1 = setupDepartment();
        MyDepartment d2 = setupDepartment();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        assertEquals(2, dptDao.getAllDepartments().size());
        assertTrue(dptDao.getAllDepartments().containsAll(Arrays.asList(d1,d2)));
    }

    @Test
    public void if_getDepartmentUsersById_ReturnsDepartmentsUsers_True() {

        MyDepartment department1 = setupDepartment();
        MyDepartment department2 = setupDepartment();

        dptDao.addDepartment(department1);
        dptDao.addDepartment(department2);

        MyUser myUser1 = setupUser();
        MyUser myUser2 = setupUser();
        MyUser myUser3 = setupUser();
        MyUser myUser4 = setupUser();
        userDao.addUser(myUser1);
        userDao.addUser(myUser2);
        userDao.addUser(myUser3);
        userDao.addUser(myUser4);

        userDao.updateUser(myUser1, myUser1.getName(), myUser1.getPosition(), myUser1.getRole(),department1.getId());
        userDao.updateUser(myUser2, myUser2.getName(), myUser2.getPosition(), myUser2.getRole(),department1.getId());
        userDao.updateUser(myUser3, myUser3.getName(), myUser3.getPosition(), myUser3.getRole(),department2.getId());

        int dc = dptDao.getAllDepartments().size();
        int uc = userDao.getAllUsers().size();

        assertEquals(2, dptDao.getMyDepartmentUsersById(department1.getId()).size());
        assertTrue(dptDao.getMyDepartmentUsersById(department1.getId()).containsAll(Arrays.asList(myUser1, myUser2)));


        assertEquals(1, dptDao.getMyDepartmentUsersById(department2.getId()).size());
        assertTrue(dptDao.getMyDepartmentUsersById(department2.getId()).containsAll(Arrays.asList(myUser3)));
        assertFalse(dptDao.getMyDepartmentUsersById(department2.getId()).contains(myUser1));
        assertFalse(dptDao.getMyDepartmentUsersById(department2.getId()).contains(myUser2));
        assertFalse(dptDao.getMyDepartmentUsersById(department2.getId()).contains(myUser4));
    }

    @Test
    public void if_getDepartmentNewsById_returns_True() {
        MyDepartment d1 = setupDepartment();
        MyDepartment d2 = setupDepartment();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        DepartmentMyNews dn = setupDepartmentNews();
        DepartmentMyNews dn2 = setupDepartmentNews();
        DepartmentMyNews dn3 = setupDepartmentNews();
        DepartmentMyNews dn4 = setupDepartmentNews();

        newsDao.addDepartmentNews(dn);
        newsDao.addDepartmentNews(dn2);
        newsDao.addDepartmentNews(dn3);

        newsDao.updateDepartmentNews(dn,dn.getUserId(),dn.getContent(),d1.getId());
        newsDao.updateDepartmentNews(dn2,dn2.getUserId(),dn2.getContent(),d1.getId());
        newsDao.updateDepartmentNews(dn3,dn3.getUserId(),dn3.getContent(),d2.getId());

        assertEquals(2,dptDao.getDepartmentNewsById(d1.getId()).size());
        assertTrue(dptDao.getDepartmentNewsById(d1.getId()).containsAll(Arrays.asList(dn,dn2)));

        assertEquals(1,dptDao.getDepartmentNewsById(d2.getId()).size());
        assertTrue(dptDao.getDepartmentNewsById(d2.getId()).contains(dn3));
        assertFalse(dptDao.getDepartmentNewsById(d2.getId()).contains(dn));
        assertFalse(dptDao.getDepartmentNewsById(d2.getId()).contains(dn2));
        assertFalse(dptDao.getDepartmentNewsById(d2.getId()).contains(dn4));

    }



    @Test
    public void if_findDepartmentById_returns_True() {
        MyDepartment d1 = setupDepartment();
        MyDepartment d2 = setupDepartment();

        dptDao.addDepartment(d1);
        dptDao.addDepartment(d2);

        MyDepartment foundDpt = dptDao.findDepartmentById(d1.getId());

        assertEquals(foundDpt,d1);
    }

    @Test
    public void updateDepartment_updatesNameDescription_True() {
        MyDepartment d = setupDepartment();
        MyDepartment d2 = setupDepartment();

        dptDao.addDepartment(d);
        dptDao.addDepartment(d2);

        String ol_name = d.getName();
        String ol_desc =d.getDescription();

        String ol_name2 = d2.getName();
        String ol_desc2 =d2.getDescription();

        dptDao.updateDepartment(d,"Technology","Efficiency");

        assertNotEquals(ol_name,d.getName());
        assertNotEquals(ol_desc,d.getDescription());

        assertEquals(ol_name2,d2.getName());
        assertEquals(ol_desc2,d2.getDescription());
    }

    @Test
    public void clearAllDepartments() {
        MyDepartment d = setupDepartment();
        MyDepartment d2 = setupDepartment();

        dptDao.addDepartment(d);
        dptDao.addDepartment(d2);

        assertEquals(2,dptDao.getAllDepartments().size());
        dptDao.clearAllDepartments();
        assertEquals(0,dptDao.getAllDepartments().size());
    }

    private MyDepartment setupDepartment(){
        return new MyDepartment(1,"Finance","Everything accounting");
    }

    private MyUser setupUser(){
        return new MyUser(1,"Ann","Senior","CFO",1);
    }

    private DepartmentMyNews setupDepartmentNews(){
        return new DepartmentMyNews(1,1, MySql2OMyNewsDao.DEPARTMENT_NEWS,"Oceans",new Timestamp(new Date().getTime()),1);
    }
}

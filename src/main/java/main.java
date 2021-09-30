import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;

public class main {
    private static MySql2OMyNewsDao newsDao;
    private static MySql2OMyDepartmentDao dptDao;
    private static MySql2OMyUserDao userDao;
    private static Sql2o sql2o;
    private static URI dbUri;
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static Gson gson = new Gson();
    private  static Connection con;

    public static void main(String[] args) {

        ProcessBuilder process = new ProcessBuilder();

        Integer port = (process.environment().get("PORT") != null) ?
                Integer.parseInt(process.environment().get("PORT")):7654;
        port(port);

        String connectionStr="jdbc:postgresql://localhost:5432/newsportal";

        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://localhost:5432/wildlife_tracker");
                sql2o = new Sql2o(connectionStr,"japhethnyaranga","34120648");

            } else {

                dbUri = new URI(System.getenv("DATABASE_URL"));
                int dbport = dbUri.getPort();
                String host = dbUri.getHost();
                String path = dbUri.getPath();
                String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
                String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];
                sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + dbport + path, username, password);
            }

        } catch (URISyntaxException e ) {
            logger.error("Unable to connect to database.");
        }

        con = sql2o.open();

        newsDao = new MySql2OMyNewsDao(sql2o);
        dptDao = new MySql2OMyDepartmentDao(sql2o);
        userDao = new MySql2OMyUserDao(sql2o);

        staticFileLocation("/public");


        get("/users", (req,res)->{
            return  gson.toJson(userDao.getAllUsers());
        });
        get("/departments", (req,res)->{
            return  gson.toJson(dptDao.getDepartmentWithUserCount());
        });
        get("/users/:id",(req,res)->{
            int user_id = Integer.parseInt(req.params("id"));
            return gson.toJson(userDao.findUserById(user_id));
        });
        get("/departments/:id",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.findDepartmentById(dpt_id));
        });
        get("/departments/:id/users",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.getMyDepartmentUsersById(dpt_id));
        });
        get("/departments/:id/news",(req,res)->{
            int dpt_id = Integer.parseInt(req.params("id"));
            return gson.toJson(dptDao.getDepartmentNewsById(dpt_id));
        });
        get("/news", (req,res)->{
            return  gson.toJson(newsDao.getAllNews());
        });
        get("/news/general", (req,res)->{
            return  gson.toJson(newsDao.getNews());
        });
        get("/news/department", (req,res)->{
            return  gson.toJson(newsDao.getDepartmentNews());
        });

        post("/Departments/new", "application/json", (req,res)->{
            MyDepartment dpt = gson.fromJson(req.body(), MyDepartment.class);

            dptDao.addDepartment(dpt);
            res.status(201);
            res.type("application/json");
            res.redirect("/departments");
            return null;//gson.toJson(dpt);
        });
        post("/Users/new", "application/json", (req,res)->{
            MyUser myUser = gson.fromJson(req.body(), MyUser.class);

            userDao.addUser(myUser);
            res.status(201);
            res.type("application/json");

            res.redirect("/users");
            return null; //gson.toJson(user);
        });
        post("/News/new", "application/json", (req,res)->{
            MyNews myNews = gson.fromJson(req.body(), MyNews.class);

            newsDao.addGeneralNews(myNews);
            res.status(201);
            res.type("application/json");
            res.redirect("/news/general");
            return null; //gson.toJson(news);
        });
        post("/DepartmentNews/new", "application/json", (req,res)->{
            DepartmentMyNews dnews = gson.fromJson(req.body(), DepartmentMyNews.class);

            newsDao.addDepartmentNews(dnews);
            res.status(201);
            res.type("application/json");

            res.redirect("/news/department");
            return null; //gson.toJson(dnews);
        });

        //FILTERS
        after((req, res) ->{
            //res.type("application/json");
        });

    }
}

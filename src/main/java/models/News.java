package models;

import java.sql.Timestamp;
import java.util.Objects;

public class News {
    //id, userID, type, content, postdate
    private int id;
    private int userid;
    private String type;
    private String content;
    private Timestamp postdate;

    public MyNews(int id, int userId, String type, String content, Timestamp postdate) {
        this.id = id;
        this.userid = userId;
        this.type = type;
        this.content = content;
        this.postdate = postdate;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userid;
    }
    public void setUserId(int userId) {
        this.userid = userId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Timestamp getPostdate() {
        return postdate;
    }
    public void setPostdate(Timestamp postdate) {
        this.postdate = postdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyNews myNews = (MyNews) o;
        return id == myNews.id &&
                userid == myNews.userid &&
                Objects.equals(type, myNews.type) &&
                Objects.equals(content, myNews.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, type, content);
    }
}

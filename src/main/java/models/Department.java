package models;

import java.sql.Timestamp;
import java.util.Objects;

public class Department extends News{
    //departmentID
    private int departmentId;

    public DepartmentMyNews(int id, int userId, String type, String content, Timestamp postdate, int departmentId) {
        super(id, userId, type, content, postdate);
        this.departmentId = departmentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepartmentMyNews that = (DepartmentMyNews) o;
        return departmentId == that.departmentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), departmentId);
    }
}

package models;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String position;
    private String role;
    private int departmentId;

    public MyUser(int id, String name, String position, String role, int departmentId) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.role = role;
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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
        MyUser myUser = (MyUser) o;
        return id == myUser.id &&
                departmentId == myUser.departmentId &&
                Objects.equals(name, myUser.name) &&
                Objects.equals(position, myUser.position) &&
                Objects.equals(role, myUser.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, role, departmentId);
    }
}

package com.exa.back_users.api.users;

public class User {
    
    private Integer id;
    private String name;
    private String cc;
    private boolean status;
    
    public User() {
    }

    public User(String name, String cc, boolean status) {
        this.name = name;
        this.cc = cc;
        this.status = status;
    }

    public User(Integer id, String name, String cc, boolean status) {
        this.id = id;
        this.name = name;
        this.cc = cc;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User { id=" + id + ", name=" + name + ", cc=" + cc + ", status=" + status + " }";
    }
}

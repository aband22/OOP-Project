package Accounts;

import java.util.List;

public class Account {
    private static final int NO_ID = -1;


    private int id;
    private String email;
    private String password;
    private String username;
    private String status;

    public Account(){

    }

    public Account(String email, String passwordHash, String username){
        this.email = email;
        this.password = passwordHash;
        this.username = username;
        this.id = NO_ID;
        this.status = "";
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}

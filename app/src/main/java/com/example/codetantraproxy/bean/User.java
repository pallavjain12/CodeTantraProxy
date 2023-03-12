package com.example.codetantraproxy.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = {@Index(value = {"emailId"},
        unique = true)}, tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "emailId")
    private String emailId;

    @ColumnInfo(name = "password")
    private String password;

    @Ignore
    public User(int id, String emailId, String password) {
        this.id = id;
        this.emailId = emailId;
        this.password = password;
    }

    public User(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

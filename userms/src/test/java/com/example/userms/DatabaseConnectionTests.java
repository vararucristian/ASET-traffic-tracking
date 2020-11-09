package com.example.userms;

import Data.User;
import DatabaseConnection.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseConnectionTests {

    @Test
    void insertUserTest() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int nextId = dbInstance.getUsersId();

        String username = "user" + String.valueOf(nextId);
        dbInstance.insertUser("user","user","user",username);
        int nextId2 = dbInstance.getUsersId();

        assertEquals(nextId+1, nextId2);
    }

    @Test
    void authenticateUserTest() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int nextId = dbInstance.getUsersId();

        String username = "user" + String.valueOf(nextId);
        String password = "user";
        dbInstance.insertUser("user","user",password,username);

        assertEquals(true, dbInstance.authenticateUser("user", username));
    }

    @Test
    void getUserByUsernameTest() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int nextId = dbInstance.getUsersId();

        String username = "user" + String.valueOf(nextId);
        String password = "user";
        String fname = "user";
        String lname = "user";
        dbInstance.insertUser(fname,lname,password, username);
        User user = new User(username, password, fname, lname, nextId);

        assertEquals(user, dbInstance.getUserByUsername(username));
    }
}

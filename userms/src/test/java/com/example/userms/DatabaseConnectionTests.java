package com.example.userms;

import DatabaseConnection.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseConnectionTests {

    @Test
    void insertUserAndGetFName() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int nextId = dbInstance.getUsersId();

        String username = "user" + String.valueOf(nextId);
        dbInstance.insertUser("user","user","user",username);
        int nextId2 = dbInstance.getUsersId();

        assertEquals(nextId+1, nextId2);
    }
}

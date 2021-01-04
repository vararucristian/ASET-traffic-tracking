package com.traffic;

import Data.Intersection;
import DatabaseConnection.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class DatabaseConnectionTests {

    @Test
    void getAllItersectionsTest() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        List<Intersection> intersections = dbInstance.getAllIntersections();
        assertTrue("Intersections list should not be null", intersections != null);
    }

    @Test
    void AddTrafficTest() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int maxId = dbInstance.getTrafficId();
        Boolean result = dbInstance.addTraffic(maxId , 10, "");
        assertTrue("Result should be true",  result == true);
    }

    @Test
    void getStreetIdTest() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int maxId = dbInstance.getTrafficId();
        assertTrue("Id should be grater then 0",  maxId > 0);
    }

    @Test
    void getTrafficIdTest() throws SQLException {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int maxId = dbInstance.getTrafficId();
        dbInstance.addTraffic(maxId , 10, "");
        int newMaxId = dbInstance.getTrafficId();
        assertTrue("newMaxId should be grater with 1 then maxId",  newMaxId == maxId +1);
    }

}

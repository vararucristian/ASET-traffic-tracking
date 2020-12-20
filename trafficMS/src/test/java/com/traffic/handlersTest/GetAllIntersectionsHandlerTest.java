package com.traffic.handlersTest;

import DTOs.Queries.GetAllIntersectionQuerry;
import Handlers.GetAllIntersectionsHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.springframework.test.util.AssertionErrors.assertFalse;

public class GetAllIntersectionsHandlerTest {

    @Test
    void handleTest() throws SQLException {

        GetAllIntersectionQuerry querry= new GetAllIntersectionQuerry();

        GetAllIntersectionsHandler handler = new GetAllIntersectionsHandler(querry);
        String response = handler.handle();
        assertFalse( "Json should not be empty list", response.equals("[]"));
    }
}

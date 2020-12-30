package com.traffic.handlersTest;

import DTOs.Queries.GetAllIntersectionsQuery;
import DTOs.Queries.GetIntersectionQuery;
import Handlers.GetAllIntersectionsHandler;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.springframework.test.util.AssertionErrors.assertFalse;

public class GetAllIntersectionsHandlerTest {

    @Test
    void handleTest() throws SQLException {

        GetAllIntersectionsQuery query= new GetAllIntersectionsQuery();

        GetAllIntersectionsHandler handler = new GetAllIntersectionsHandler(query);
        String response = handler.handle();
        assertFalse( "Json should not be empty list", response.equals("[]"));
    }
}

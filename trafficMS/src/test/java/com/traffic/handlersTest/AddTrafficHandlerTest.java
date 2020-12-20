package com.traffic.handlersTest;

import DTOs.Commands.AddTrafficCommand;
import Data.Intersection;
import DatabaseConnection.DatabaseConnection;
import Handlers.AddTrafficHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class AddTrafficHandlerTest {

    private static class SuccessResponse{
        boolean success = true;
    }

    @Test
    void handleTest() throws SQLException {
        Gson gson = new Gson();
        String successReponse = gson.toJson(new AddTrafficHandlerTest.SuccessResponse());
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int maxId = dbInstance.getTrafficLightId();
        int nrCars = 10;
        String image = "";
        dbInstance.addTraffic(maxId , nrCars, image);

        AddTrafficCommand command= new AddTrafficCommand(maxId, nrCars, image);

        AddTrafficHandler handler = new AddTrafficHandler(command);
        String response = handler.handle();
        assertEquals( successReponse , response);
    }
}

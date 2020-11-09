package com.example.userms.HandlersTests;
import DTOs.Commands.CreateUserCommand;
import DatabaseConnection.DatabaseConnection;
import Handlers.CreateUserHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateUserHandlerTest {

    private static class SuccessResponse{
        boolean success = true;
    }

    @Test
    void handleTest(){
        Gson gson = new Gson();
        String successReponse = gson.toJson(new SuccessResponse());
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        int nextId = dbConnection.getUsersId();
        String username = "user" + String.valueOf(nextId);
        CreateUserCommand command= new CreateUserCommand(username, "user", "user", "user");
        CreateUserHandler handler = new CreateUserHandler(command);
        String response = handler.handle();
        assertEquals( successReponse, response);
    }
}

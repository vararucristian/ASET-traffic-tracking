package com.example.userms.HandlersTests;

import DTOs.Commands.AuthenticateUserCommand;
import DatabaseConnection.DatabaseConnection;
import Handlers.AuthenticateUserHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticateUserHandlerTest {
    private static class SuccessResponse{
        boolean success = true;
    }

    @Test
    void handleTest(){
        Gson gson = new Gson();
        String successReponse = gson.toJson(new AuthenticateUserHandlerTest.SuccessResponse());
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        int nextId = dbInstance.getUsersId();

        String username = "user" + String.valueOf(nextId);
        String password = "user";
        dbInstance.insertUser("user","user",password,username);
        AuthenticateUserCommand command= new AuthenticateUserCommand(username, password);

        AuthenticateUserHandler handler = new AuthenticateUserHandler(command);
        String response = handler.handle();
        assertEquals(successReponse, response);
    }
}

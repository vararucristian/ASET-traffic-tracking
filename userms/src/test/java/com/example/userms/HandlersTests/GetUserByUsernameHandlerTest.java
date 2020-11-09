package com.example.userms.HandlersTests;

import DTOs.Querys.GetUserByUsernameQuery;
import DatabaseConnection.DatabaseConnection;
import Handlers.GetUserByUsernameHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserByUsernameHandlerTest {

        private static class SuccessResponse{
            boolean success = true;
        }

        @Test
        void handleTest(){
            Gson gson = new Gson();
            String successReponse = gson.toJson(new GetUserByUsernameHandlerTest.SuccessResponse());
            DatabaseConnection dbInstance = DatabaseConnection.getInstance();
            int nextId = dbInstance.getUsersId();

            String username = "user" + String.valueOf(nextId);
            String password = "user";
            String fname = "user";
            String lname = "user";
            dbInstance.insertUser(fname,lname,password, username);
            GetUserByUsernameQuery querry= new GetUserByUsernameQuery(username);

            GetUserByUsernameHandler handler = new GetUserByUsernameHandler(querry);
            String response = handler.handle();
            assertEquals(successReponse, response);
        }
}

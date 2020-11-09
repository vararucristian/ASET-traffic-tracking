package Handlers;

import DTOs.Commands.AuthenticateUserCommand;
import DTOs.DTOOperation;
import DTOs.Querys.GetUserByUsernameQuery;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class GetUserByUsernameHandler implements Handler {
    public static class Response {
        boolean success;
    }

    GetUserByUsernameQuery querry;
    DatabaseConnection databaseConnection;

    public GetUserByUsernameHandler(GetUserByUsernameQuery querry) {
        this.querry= querry;
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        GetUserByUsernameHandler.Response response = new GetUserByUsernameHandler.Response();
        response.success = databaseConnection.getUserByUsername(querry.getUserName()) != null;
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}

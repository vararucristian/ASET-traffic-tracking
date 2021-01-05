package Handlers;

import DTOs.Commands.AuthenticateUserCommand;
import DTOs.DTOOperation;
import DTOs.Querys.GetUserByUsernameQuery;
import Data.UserRepository;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class GetUserByUsernameHandler implements Handler {
    public static class Response {
        boolean success;
    }

    GetUserByUsernameQuery querry;
    UserRepository repo;
    public GetUserByUsernameHandler(GetUserByUsernameQuery querry) {
        this.querry= querry;
        repo = new UserRepository();
    }

    @Override
    public String handle() {
        GetUserByUsernameHandler.Response response = new GetUserByUsernameHandler.Response();
        response.success = repo.getUserByUsername(querry.getUserName()) != null;
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}

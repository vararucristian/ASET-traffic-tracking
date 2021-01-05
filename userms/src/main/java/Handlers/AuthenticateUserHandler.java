
package Handlers;

import DTOs.Commands.AuthenticateUserCommand;
import Data.UserRepository;
import DatabaseConnection.DatabaseConnection;
import Response.ResponseTemplate;
import com.google.gson.Gson;

public class AuthenticateUserHandler implements Handler {

    AuthenticateUserCommand command;
    UserRepository repo;
    public AuthenticateUserHandler(AuthenticateUserCommand command) {
        this.command = command;
        repo = new UserRepository();
    }

    @Override
    public String handle() {
        ResponseTemplate response = repo.authenticateUser(command.getPassword(), command.getUserName());
        Gson gson = new Gson();
        return gson.toJson(response);
    }

}


package Handlers;

import DTOs.Commands.AuthenticateUserCommand;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class AuthenticateUserHandler implements Handler {

    public static class Response {
        boolean success;
    }

    AuthenticateUserCommand command;
    DatabaseConnection databaseConnection;

    public AuthenticateUserHandler(AuthenticateUserCommand command) {
        this.command = command;
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        AuthenticateUserHandler.Response response = new AuthenticateUserHandler.Response();
        response.success = databaseConnection.authenticateUser(command.getPassword(), command.getUserName());
        Gson gson = new Gson();
        return gson.toJson(response);
    }

}


package Handlers;

import DTOs.Commands.AuthenticateUserCommand;
import DatabaseConnection.DatabaseConnection;
import Response.ResponseTemplate;
import com.google.gson.Gson;

public class AuthenticateUserHandler implements Handler {

    AuthenticateUserCommand command;
    DatabaseConnection databaseConnection;

    public AuthenticateUserHandler(AuthenticateUserCommand command) {
        this.command = command;
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        ResponseTemplate response = databaseConnection.authenticateUser(command.getPassword(), command.getUserName());
        Gson gson = new Gson();
        return gson.toJson(response);
    }

}

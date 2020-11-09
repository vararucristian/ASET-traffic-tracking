package Handlers;

import DTOs.Commands.CreateUserCommand;
import DTOs.DTOOperation;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class CreateUserHandler implements Handler {

    public static class Response {
        boolean success;
    }

    CreateUserCommand command;
    DatabaseConnection databaseConnection;

    public CreateUserHandler(CreateUserCommand command) {
        this.command = command;
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        Response response = new Response();
        response.success = databaseConnection.insertUser(command.getfName(),
                command.getlName(),
                command.getPassword(),
                command.getUserName());
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}

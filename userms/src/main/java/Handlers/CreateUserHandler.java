package Handlers;

import DTOs.Commands.CreateUserCommand;
import DTOs.DTOOperation;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class CreateUserHandler implements Handler {

    public static class Response {
        boolean success;
        String reason;
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
        if(command.getPassword().equals(command.getConfirmPassword())){
            response.success = databaseConnection.insertUser(command.getfName(),
                    command.getlName(),
                    command.getPassword(),
                    command.getUserName());
            if (response.success)
                response.reason = "";
            else
                response.reason = "Username already exists";
        }
        else{
            response.success = false;
            response.reason = "Please make sure your password match";
        }
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}

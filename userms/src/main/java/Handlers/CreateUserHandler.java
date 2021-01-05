package Handlers;

import DTOs.Commands.CreateUserCommand;
import DTOs.DTOOperation;
import Data.UserRepository;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class CreateUserHandler implements Handler {

    public static class Response {
        boolean success;
        String reason;
    }

    CreateUserCommand command;
    UserRepository repo;
    public CreateUserHandler(CreateUserCommand command) {
        this.command = command;
        repo= new UserRepository();
    }

    @Override
    public String handle() {
        Response response = new Response();
        if(command.getPassword().equals(command.getConfirmPassword())){
            response.success = repo.addUser(command.getfName(),
                                            command.getlName(),
                                            command.getPassword(),
                                            command.getUserName()
                    );

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

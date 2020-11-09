package Handlers;

import DTOs.Commands.CreateUserCommand;
import DTOs.Commands.AuthenticateUserCommand;
import DTOs.Querys.GetUserByUsernameQuery;

public class HandlerFactory {

    public AuthenticateUserHandler createHandler(AuthenticateUserCommand command){
        return new AuthenticateUserHandler(command);
    }

    public CreateUserHandler createHandler(CreateUserCommand command){
        return new CreateUserHandler(command);
    }

    public GetUserByUsernameHandler createHandler(GetUserByUsernameQuery querry) {
        return new GetUserByUsernameHandler(querry);
    }
}

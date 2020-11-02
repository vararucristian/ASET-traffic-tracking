package Handlers;

import DTOs.Commands.CreateUserCommand;
import DTOs.Commands.AuthenticateUserCommand;
import DTOs.Querys.GetUserByUsernameQuery;

public class HandlerFactory {

    public AuthenticateUserHandler createHandler(AuthenticateUserCommand query){
        return new AuthenticateUserHandler();
    }

    public CreateUserHandler createHandler(CreateUserCommand command){
        return new CreateUserHandler();
    }

    public GetUserByUsernameHandler createHandler(GetUserByUsernameQuery query) {
        return new GetUserByUsernameHandler();
    }
}

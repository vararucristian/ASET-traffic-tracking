package Handlers;

import DTOs.Querys.AuthenticateUserQuery;
import DTOs.Querys.GetUserByUsernameQuery;

public class HandlerFactory {

    public AuthenticateUserHandler createHandler(AuthenticateUserQuery query){
        return new AuthenticateUserHandler();
    }

    public CreateUserHandler createHandler(CreateUserHandler command){
        return new CreateUserHandler();
    }

    public GetUserByUsernameHandler createHandler(GetUserByUsernameQuery query) {
        return new GetUserByUsernameHandler();
    }
}

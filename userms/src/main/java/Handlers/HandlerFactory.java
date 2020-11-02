package Handlers;

import Queries.AuthenticateUserQuery;

public class HandlerFactory {

    public AuthenticateUserHandler createHandler(AuthenticateUserQuery query){
        return new AuthenticateUserHandler();
    }

    public CreateUserHandler createHandler(CreateUserHandler query){
        return new CreateUserHandler();
    }
}

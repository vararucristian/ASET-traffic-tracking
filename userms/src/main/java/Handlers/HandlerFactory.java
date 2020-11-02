<<<<<<< HEAD
package com.example.userms;public class HandlerFactory {
=======
package Handlers;

import Queries.AuthenticateUserQuery;

public class HandlerFactory {

    public AuthenticateUserHandler createHandler(AuthenticateUserQuery query){
        return new AuthenticateUserHandler();
    }

    public CreateUserHandler createHandler(CreateUserHandler query){
        return new CreateUserHandler();
    }

>>>>>>> eed65b5... API GATEWAY patter (FACADE) + Handler factory
}

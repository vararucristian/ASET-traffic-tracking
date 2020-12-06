package com.example.userms;

import DTOs.Commands.AuthenticateUserCommand;
import DTOs.Commands.CreateUserCommand;
import DTOs.Querys.GetUserByUsernameQuery;
import Handlers.CreateUserHandler;
import Handlers.HandlerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/getUser/{username}")
    public String getUser(String username)
    {
        GetUserByUsernameQuery query = new GetUserByUsernameQuery(username);
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }

    @PostMapping(path = "/authenticateUser", consumes = "application/json", produces = "application/json")
    public String authenticate(@RequestBody AuthenticateUserCommand command)
    {
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(command).handle();
    }

    @PostMapping(path = "/createUser", consumes = "application/json", produces = "application/json")
    public String createUser(@RequestBody CreateUserCommand command)
    {
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(command).handle();
    }

}

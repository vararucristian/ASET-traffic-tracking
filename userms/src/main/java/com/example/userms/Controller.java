package com.example.userms;

import DTOs.Commands.AuthenticateUserCommand;
import DTOs.Querys.GetUserByUsernameQuery;
import Handlers.HandlerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/authenticateUser")
    public String createUser(AuthenticateUserCommand command)
    {
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(command).handle();
    }

}

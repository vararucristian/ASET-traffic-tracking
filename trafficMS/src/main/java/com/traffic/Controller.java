package com.traffic;

import DTOs.Queries.GetIntersectionQuery;
import Handlers.HandlerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/getIntersection/{name}")
    public String getUser(String name) {
        GetIntersectionQuery query = new GetIntersectionQuery(name);
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }
}
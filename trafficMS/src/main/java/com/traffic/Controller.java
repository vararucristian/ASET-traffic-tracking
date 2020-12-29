package com.traffic;

import DTOs.Commands.AddTrafficCommand;
import DTOs.Queries.GetAllIntersectionQuerry;
import DTOs.Queries.GetTrafficLightsByIntersectionIdQuery;
import Handlers.HandlerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/getIntersectionById/{id}")
    public String getIntersectionById(int id) {
        GetTrafficLightsByIntersectionIdQuery query = new GetTrafficLightsByIntersectionIdQuery(id);
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }

    @GetMapping("/getAllIntersections")
    public String getAllIntersections() {
        GetAllIntersectionQuerry query = new GetAllIntersectionQuerry();
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }

    @PostMapping(path = "/addTraffic", consumes = "application/json", produces = "application/json")
    public String addTraffic(@RequestBody AddTrafficCommand command)
    {
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(command).handle();
    }
}
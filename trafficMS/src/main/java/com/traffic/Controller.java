package com.traffic;

import DTOs.Commands.AddTrafficCommand;
import DTOs.Queries.GetAllIntersectionsQuery;
import DTOs.Queries.GetIntersectionQuery;
import DTOs.Queries.GetTrafficByIntersectionQuery;
import Handlers.HandlerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Resource;

@RestController
public class Controller {

    @GetMapping("/getIntersection/{name}")
    public String getIntersection(@PathVariable String name) {
        GetIntersectionQuery query = new GetIntersectionQuery(name);
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }

    @GetMapping("/getAllIntersections")
    public String getIntersections(){
        GetAllIntersectionsQuery query = new GetAllIntersectionsQuery();
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }

    @GetMapping("/getTrafficByIntersection/{id}")
    public String getTrafficByIntersection(@PathVariable Integer id){
        System.out.println(id);
        GetTrafficByIntersectionQuery query = new GetTrafficByIntersectionQuery(id);
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }

    @PostMapping(path = "/addTraffic", consumes = "application/json", produces = "application/json")
    public String createIntersection(@RequestBody Resource<AddTrafficCommand> command)
    {
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(command.getContent()).handle();
    }


}
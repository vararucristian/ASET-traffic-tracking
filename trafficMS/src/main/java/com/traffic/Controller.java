package com.traffic;

import DTOs.Commands.AddTrafficCommand;
import DTOs.Queries.GetAllIntersectionsQuery;
import DTOs.Queries.GetIntersectionQuery;
import DTOs.Queries.GetTrafficByLightQuery;
import Handlers.HandlerFactory;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getTrafficByLight/{id}")
    public String getTrafficByLight(@PathVariable Integer id){
        System.out.println(id);
        GetTrafficByLightQuery query = new GetTrafficByLightQuery(id);
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(query).handle();
    }

    @PostMapping(path = "/addTraffic", consumes = "application/json", produces = "application/json")
    public String createIntersection(@RequestBody AddTrafficCommand command)
    {
        HandlerFactory factory = new HandlerFactory();
        return factory.createHandler(command).handle();
    }


}
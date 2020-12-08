package Handlers;


import DTOs.Commands.AddTrafficCommand;
import DTOs.Queries.GetIntersectionQuery;

public class HandlerFactory {

    public GetIntersectionHandler createHandler(GetIntersectionQuery query){
        return new GetIntersectionHandler(query);
    }

    public AddTrafficHandler createHandler(AddTrafficCommand command){
        return new AddTrafficHandler(command);
    }
}

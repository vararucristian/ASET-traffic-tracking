package Handlers;


import DTOs.Commands.AddTrafficCommand;
import DTOs.Queries.GetAllIntersectionsQuery;
import DTOs.Queries.GetIntersectionQuery;
import DTOs.Queries.GetTrafficByIntersectionQuery;

public class HandlerFactory {

    public GetIntersectionHandler createHandler(GetIntersectionQuery query){
        return new GetIntersectionHandler(query);
    }

    public GetAllIntersectionsHandler createHandler(GetAllIntersectionsQuery query){
        return new GetAllIntersectionsHandler(query);
    }

    public GetTrafficByIntersectionHandler createHandler(GetTrafficByIntersectionQuery query){
        return new GetTrafficByIntersectionHandler(query);
    }

    public AddTrafficHandler createHandler(AddTrafficCommand command){
        return new AddTrafficHandler(command);
    }
}

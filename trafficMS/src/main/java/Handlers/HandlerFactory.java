package Handlers;


import DTOs.Commands.AddTrafficCommand;
import DTOs.Queries.GetAllIntersectionQuerry;
import DTOs.Queries.GetTraffficLightsByIntersectionIdQuery;

public class HandlerFactory {

    public GetTrafficLightsByIntersectionIdHandler createHandler(GetTraffficLightsByIntersectionIdQuery query){
        return new GetTrafficLightsByIntersectionIdHandler(query);
    }

    public AddTrafficHandler createHandler(AddTrafficCommand command){
        return new AddTrafficHandler(command);
    }

    public GetAllIntersectionsHandler createHandler(GetAllIntersectionQuerry query){
        return new GetAllIntersectionsHandler(query);
    }
}

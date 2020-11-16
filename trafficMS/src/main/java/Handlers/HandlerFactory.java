package Handlers;


import DTOs.Queries.GetIntersectionQuery;

public class HandlerFactory {

    public GetIntersectionHandler createHandler(GetIntersectionQuery query){
        return new GetIntersectionHandler(query);
    }
}

package Handlers;

import DTOs.Queries.GetTraffficLightsByIntersectionIdQuery;

public class GetTrafficLightsByIntersectionIdHandler implements Handler {

    GetTraffficLightsByIntersectionIdQuery querry;

    public GetTrafficLightsByIntersectionIdHandler(GetTraffficLightsByIntersectionIdQuery querry) {
        this.querry= querry;
    }

    @Override
    public String handle() {
        return "succes";
    }

}

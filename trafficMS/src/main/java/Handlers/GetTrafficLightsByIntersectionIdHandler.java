package Handlers;

import DTOs.Queries.GetTrafficLightsByIntersectionIdQuery;

public class GetTrafficLightsByIntersectionIdHandler implements Handler {

    GetTrafficLightsByIntersectionIdQuery querry;

    public GetTrafficLightsByIntersectionIdHandler(GetTrafficLightsByIntersectionIdQuery querry) {
        this.querry= querry;
    }

    @Override
    public String handle() {
        return "succes";
    }

}

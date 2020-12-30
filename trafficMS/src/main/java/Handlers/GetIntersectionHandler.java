package Handlers;

import DTOs.Queries.GetIntersectionQuery;
import DatabaseConnection.DatabaseConnection;

public class GetIntersectionHandler implements Handler {

    GetIntersectionQuery querry;

    public GetIntersectionHandler(GetIntersectionQuery querry) {
        this.querry= querry;
    }

    @Override
    public String handle() {
        return "succes";
    }

}

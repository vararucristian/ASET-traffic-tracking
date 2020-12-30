package Handlers;

import DTOs.Queries.GetAllIntersectionsQuery;
import Data.Intersection;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GetAllIntersectionsHandler implements Handler {

    public static class Response {
        ArrayList<Intersection> intersections;
        boolean success;
    }

    GetAllIntersectionsQuery querry;
    DatabaseConnection databaseConnection;

    public GetAllIntersectionsHandler(GetAllIntersectionsQuery querry) {
        this.querry= querry;
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        Response response = new Response();
        response.intersections = databaseConnection.getAllIntersections();
        response.success = true;
        Gson gson = new Gson();
        return gson.toJson(response);
    }

}

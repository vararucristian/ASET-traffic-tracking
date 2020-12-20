package Handlers;

import DTOs.Commands.AddTrafficCommand;
import DTOs.Queries.GetAllIntersectionQuerry;
import Data.Intersection;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

import java.util.List;

public class GetAllIntersectionsHandler implements Handler{

    public static class Response {
        boolean success;
        String reason;
    }

    GetAllIntersectionQuerry command;
    DatabaseConnection databaseConnection;

    public GetAllIntersectionsHandler(GetAllIntersectionQuerry command) {
        this.command= command;
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        List<Intersection> intersections = databaseConnection.getAllIntersections();
        Gson gson = new Gson();
        return gson.toJson(intersections);
    }
}

package Handlers;

import DTOs.Queries.GetTrafficByIntersectionQuery;
import Data.Traffic;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;
import java.util.*;

public class GetTrafficByIntersectionHandler implements Handler {
    private static class Response{
        List<Traffic> traffic;
        boolean success;
    }

    GetTrafficByIntersectionQuery querry;
    DatabaseConnection databaseConnection;

    public GetTrafficByIntersectionHandler(GetTrafficByIntersectionQuery querry) {
        this.querry= querry;
        databaseConnection= DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        Response response = new Response();
        response.traffic = databaseConnection.getTrafficByIntersectionId(querry.getId());
        response.success = response.traffic != null;
        Gson gson = new Gson();
        return gson.toJson(response);
    }

}

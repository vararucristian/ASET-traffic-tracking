package Handlers;

import DTOs.Queries.GetTrafficByLightQuery;
import Data.Traffic;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class GetTrafficByLightHandler implements Handler {
    private static class Response{
        Traffic traffic;
        boolean success;
    }

    GetTrafficByLightQuery querry;
    DatabaseConnection databaseConnection;

    public GetTrafficByLightHandler(GetTrafficByLightQuery querry) {
        this.querry= querry;
        databaseConnection= DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        Response response = new Response();
        response.traffic = databaseConnection.getTrafficByLightId(querry.getId());
        response.success = response.traffic != null;
        Gson gson = new Gson();
        return gson.toJson(response);
    }

}

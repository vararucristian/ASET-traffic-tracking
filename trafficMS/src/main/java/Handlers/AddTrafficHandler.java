package Handlers;

import DTOs.Commands.AddTrafficCommand;
import DatabaseConnection.DatabaseConnection;
import com.google.gson.Gson;

public class AddTrafficHandler implements Handler{

    public static class Response {
        boolean success;
        String reason;
    }

    AddTrafficCommand command;
    DatabaseConnection databaseConnection;

    public AddTrafficHandler(AddTrafficCommand command) {
        this.command= command;
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public String handle() {
        Response response = new Response();
        response.success = databaseConnection.addTraffic(command.getIdTrafficLight(),
                command.getNrCars(),
                command.getImage());
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}

package Handlers;

import DTOs.Commands.AddTrafficCommand;
import com.google.gson.Gson;

public class AddTrafficHandler implements Handler{

    public static class Response {
        boolean success;
        String reason;
    }

    AddTrafficCommand command;

    public AddTrafficHandler(AddTrafficCommand command) {
        this.command= command;
    }

    @Override
    public String handle() {
        Response response = new Response();

        Gson gson = new Gson();
        return gson.toJson(response);
    }
}

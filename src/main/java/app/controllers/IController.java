package app.controllers;

import io.javalin.http.Context;

public interface IController {

    public void getAll(Context ctx);
    public void getById(Context ctx);
    public void doctorBySpecialty(Context ctx);
    public void doctorByBirthdayRange(Context ctx);
    public void createDoctor(Context ctx);
    public void update(Context ctx);
    public void delete(Context ctx);

}

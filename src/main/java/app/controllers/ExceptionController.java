package app.controllers;

import app.entities.Message;
import app.exceptions.ApiException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionController {

    private final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    public void apiExceptionHandler(ApiException e, Context ctx) {

        log.error("{} {}", ctx.res().getStatus(), e.getMessage());
        ctx.status(e.getStatusCode());
        ctx.json(new Message(e.getStatusCode(), e.getMessage(), e.getTimeStamp()));

    }

    public void exceptionHandler(Exception e, Context ctx) {
        log.error("{}: {}", ctx.res().getStatus(), e.getMessage());
        ctx.status(500);
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        ctx.json(new Message(500, e.getMessage(), timeStamp));
    }
}

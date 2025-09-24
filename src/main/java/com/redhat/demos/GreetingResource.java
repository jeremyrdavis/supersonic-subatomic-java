package com.redhat.demos;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "greeting", defaultValue = "Hello, World!")
    String greeting;

    @Inject
    GreetingRepository greetingRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return greeting;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Greeting addGreeting(Greeting greeting) {

        Log.debugf("Adding greeting %s", greeting);

        greetingRepository.persist(greeting);

        Log.debugf("Added greeting %s", greeting);

        return greeting;
    }

    @GET
    @Path("/all")
    public List<Greeting> getAllGreetings() {

        Log.debugf("Returning %s greetings", greetingRepository.count());

        return greetingRepository.listAll();
    }
}

package com.redhat.demos;

import io.quarkus.logging.Log;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/greetings")
public class ApiResource {

    @Inject
    GreetingRepository greetingRepository;

    @Inject
    KafkaAdapter kafkaAdapter;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Greeting addGreeting(Greeting greeting) {

        QuarkusTransaction.requiringNew().run(() -> {
            greetingRepository.persist(greeting);
        });
        Log.debugf("Added greeting %s", greeting);

        kafkaAdapter.sendGreeting(greeting);
        Log.debugf("Sent greeting %s", greeting);

        return greeting;
    }

    @GET
    public List<Greeting> getAllGreetings() {
        Log.debugf("Returning %s greetings", greetingRepository.count());
        return greetingRepository.listAll();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Greeting updateGreeting(@PathParam("id") Long id, Greeting updatedGreeting) {
        Greeting greeting = greetingRepository.findById(id);
        if (greeting == null) {
            throw new NotFoundException("Greeting not found");
        }
        greeting.text = updatedGreeting.text;
        greeting.author = updatedGreeting.author;
        greetingRepository.persist(greeting);
        Log.debugf("Updated greeting %s", greeting);
        return greeting;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteGreeting(@PathParam("id") Long id) {
        Greeting greeting = greetingRepository.findById(id);
        if (greeting == null) {
            throw new NotFoundException("Greeting not found");
        }
        greetingRepository.delete(greeting);
        Log.debugf("Deleted greeting %s", greeting);
    }
}

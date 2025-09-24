package com.redhat.demos;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class KafkaAdapter {

    @Channel("greetings-out")
    Emitter<Greeting> greetingsEmitter;

    public void sendGreeting(Greeting greeting) {
        greetingsEmitter.send(greeting);
    }
}

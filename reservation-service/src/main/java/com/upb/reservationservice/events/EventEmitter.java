package com.upb.reservationservice.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventEmitter {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void emitEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
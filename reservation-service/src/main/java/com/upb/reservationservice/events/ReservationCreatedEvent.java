package com.upb.reservationservice.events;


import com.upb.reservationservice.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationCreatedEvent implements Event {
    private final Reservation reservation;
}

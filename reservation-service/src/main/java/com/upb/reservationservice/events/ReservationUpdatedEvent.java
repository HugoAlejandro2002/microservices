package com.upb.reservationservice.events;

import com.upb.reservationservice.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationUpdatedEvent implements Event{
    private final Reservation reservation;
    private String newStatus;
}

package com.upb.reservationservice.handlers;


import com.upb.reservationservice.client.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ReservationEventHandler implements EventHandler {
    @Autowired
    private NotificationService notificationService;
//    @Override
//    public void handle(Event event) {
//        if (event instanceof ReservationCreatedEvent) {
//            handleReservationCreatedEvent((ReservationCreatedEvent) event);
//        } else if (event instanceof ReservationUpdatedEvent) {
//            handleReservationUpdatedEvent((ReservationUpdatedEvent) event);
//        }
//    }

//    private void handleReservationUpdatedEvent(ReservationUpdatedEvent reservationUpdatedEvent) {
//        Instant i = Instant.now();
//        long reservationId = reservationUpdatedEvent.getReservation().getId();
//        String newStatus = reservationUpdatedEvent.getNewStatus();
//        if(Objects.equals(newStatus, "Rejected")){
//            notificationService.createNotification(reservationUpdatedEvent.getReservation().getId(),reservationUpdatedEvent.getReservation().getClientId(), "Reserva Rechazada :(", Date.from(i) );
//        }else if(Objects.equals(newStatus, "Approved")){
//            notificationService.createNotification(reservationUpdatedEvent.getReservation().getId(),reservationUpdatedEvent.getReservation().getClientId(), "Reserva Aceptada! :)", Date.from(i) );
//        }
//    }

//    private void handleReservationCreatedEvent(ReservationCreatedEvent reservationCreatedEvent) {
//        System.out.println("Reservation created with ID: " + reservationCreatedEvent.getReservation().getId());
//        Instant i = Instant.now();
//        notificationService.createNotification(reservationCreatedEvent.getReservation().getId(), reservationCreatedEvent.getReservation().getClientId(), "Reserva Creada, espera a que sea aprobada por un admin, gracias!", Date.from(i));
//    }
}
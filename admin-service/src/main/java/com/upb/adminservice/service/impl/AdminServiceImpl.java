package com.upb.adminservice.service.impl;

import com.upb.adminservice.client.NotificationService;
import com.upb.adminservice.entity.DiningTable;
import com.upb.adminservice.entity.EStatus;
import com.upb.adminservice.entity.Reservation;
import com.upb.adminservice.model.NotificationRequest;
import com.upb.adminservice.model.ReservationResponse;
import com.upb.adminservice.model.TableResponse;
import com.upb.adminservice.repository.DiningTableRepository;
import com.upb.adminservice.repository.ReservationRepository;
import com.upb.adminservice.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ReservationRepository reservationRepository;
    private final DiningTableRepository diningTableRepository;
    private final NotificationService notificationService;


    @Override
    @Transactional
    public boolean acceptReservation(Long id) {
        // Encuentra la reserva por ID
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        // Comprueba si la reserva ya está confirmada o cancelada
        if (!reservation.getStatus().equals(EStatus.PENDING)) {
            return false; // O maneja según sea necesario
        }

        // Acepta la reserva y actualiza la mesa correspondiente
        reservation.setStatus(EStatus.CONFIRMED);
        DiningTable table = reservation.getTable();
        table.setAvailable(false);
        diningTableRepository.save(table);
        reservationRepository.save(reservation);

        // Enviar notificación de reserva aceptada al usuario correspondiente
        sendNotification(reservation, "Tu reserva ha sido aceptada.");

        // Rechaza todas las otras reservaciones para esa mesa y tiempo
        List<Reservation> conflictingReservations = reservationRepository
                .findByTable_IdAndDatetime(table.getId(), reservation.getDatetime());
        for (Reservation conflicting : conflictingReservations) {
            if (!conflicting.getId().equals(id)) { // Evita rechazar la reserva que estamos aceptando
                conflicting.setStatus(EStatus.CANCELLED);
                reservationRepository.save(conflicting);

                // Envía notificación de reserva rechazada
                sendNotification(conflicting, "Tu reserva ha sido rechazada debido a la sobreposición con otra reserva.");
            }
        }

        return true;
    }

    private void sendNotification(Reservation reservation, String message) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAdminId(213L);
        notificationRequest.setUserId(reservation.getUserId());
        notificationRequest.setTableId(reservation.getTable().getId());
        notificationRequest.setTimestamp(new Date());
        notificationRequest.setMessage(message);
        notificationService.sendNotification(notificationRequest);
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        // Obtener todas las entidades de reservación de la base de datos
        List<Reservation> reservations = reservationRepository.findAll();

        // Convertir las entidades de reservación a modelos de respuesta
        return reservations.stream().map(this::convertToReservationResponse).collect(Collectors.toList());
    }

    private ReservationResponse convertToReservationResponse(Reservation reservation) {
        // Aquí asumimos que tienes un constructor en ReservationResponse que acepta una entidad Reservation
        // y realiza la conversión. Si no es así, necesitarás asignar manualmente cada campo.
        return ReservationResponse.builder()
                .id(reservation.getId())
                .datetime(reservation.getDatetime())
                .table(convertToTableResponse(reservation.getTable()))
                .userId(reservation.getUserId())
                .status(reservation.getStatus())
                .build();
    }

    private TableResponse convertToTableResponse(DiningTable table) {
        // Similar a la conversión de reservación, asumimos que hay un constructor o método de construcción
        return TableResponse.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .capacity(table.getCapacity())
                .availableTime(table.getAvailableTime())
                .build();
    }
}

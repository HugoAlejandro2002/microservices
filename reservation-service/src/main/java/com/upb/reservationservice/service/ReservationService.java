package com.upb.reservationservice.service;

import com.upb.reservationservice.model.ReservationRequest;
import com.upb.reservationservice.model.ReservationResponse;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservationRequest, long userId);
//    ReservationResponse getReservationById(long reservationId);
//
//    List<TableDTO> getAvailableTablesByDate(String date);
//
//    List<ReservationResponse> getReservationsByDate(long scheduleId);
//    List<ReservationResponse> getReservationsByTable( long tableId);
//
//    boolean approveReservation(long id);
//
//    boolean rejectReservation(long id);
}

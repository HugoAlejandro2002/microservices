package com.upb.adminservice.service;

import com.upb.adminservice.model.ReservationResponse;

import java.util.List;

public interface AdminService {
    boolean acceptReservation(Long id);

    List<ReservationResponse> getAllReservations();
}

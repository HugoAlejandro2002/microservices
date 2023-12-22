package com.upb.adminservice.controller;


import com.upb.adminservice.model.ReservationResponse;
import com.upb.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> allReservations = adminService.getAllReservations();
        return ResponseEntity.ok(allReservations);
    }

    @PatchMapping("/reservations/{id}/accept")
    public ResponseEntity<Void> acceptReservation(@PathVariable Long id) {
        boolean isAccepted = adminService.acceptReservation(id);
        if (isAccepted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

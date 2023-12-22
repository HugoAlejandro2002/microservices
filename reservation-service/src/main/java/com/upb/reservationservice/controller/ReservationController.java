package com.upb.reservationservice.controller;

import com.upb.reservationservice.model.ReservationRequest;
import com.upb.reservationservice.model.ReservationResponse;
import com.upb.reservationservice.service.ReservationService;
import com.upb.reservationservice.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TableService availabilityService;

    @PostMapping
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequest reservationRequest, @RequestParam("userId") long userId) {
        try {
            ReservationResponse reservation = reservationService.createReservation(reservationRequest, userId);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.severe("Error creating reservation");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @GetMapping("/{id}")
//    public ResponseEntity <ReservationResponse> getReservationById(@PathVariable long id){
//        ReservationResponse reservationResponse = reservationService.getReservationById(id);
//        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
//    }
//    @GetMapping("/date/{scheduleId}")
//    public ResponseEntity <List<ReservationResponse>> getReservationsByDate(@PathVariable long scheduleId){
//        List<ReservationResponse> reservations = reservationService.getReservationsByDate(scheduleId);
//        if (reservations.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(reservations, HttpStatus.OK);
//    }
//    @GetMapping("/table/{tableId}")
//    public ResponseEntity <List<ReservationResponse>> getReservationsByTable(@PathVariable long tableId){
//        List<ReservationResponse> reservations = reservationService.getReservationsByTable(tableId);
//        if (reservations.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(reservations, HttpStatus.OK);
//    }
//    @GetMapping("/availableOn/{scheduleId}")
//    public ResponseEntity<List<TableDTO>> getAvailableTablesByDate(@PathVariable String date) {
//        List<TableDTO> availableTables = reservationService.getAvailableTablesByDate(date);
//
//        if (availableTables.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(availableTables, HttpStatus.OK);
//    }
//    @PutMapping("/{id}/approve")
//    public ResponseEntity<String> approveReservation(@PathVariable long id) {
//        // Assuming you have a method in your service to handle the approval
//        boolean isApproved = reservationService.approveReservation(id);
//
//        if (isApproved) {
//            return new ResponseEntity<>("Reservation approved successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failed to approve reservation", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PutMapping("/{id}/reject")
//    public ResponseEntity<String> rejectReservation(@PathVariable long id) {
//        boolean isRejected = reservationService.rejectReservation(id);
//
//        if (isRejected) {
//            return new ResponseEntity<>("Reservation rejected successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failed to reject reservation (not found or invalid state)", HttpStatus.NOT_FOUND);
//        }
//    }

//        @GetMapping("/available/")
//    public ResponseEntity <List<DiningTable>> getAvailableTables(@RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
//                                                                 @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
//        List<DiningTable> tables = availabilityService.getAvailableTables(scheduleId);
//        if (tables.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(tables, HttpStatus.OK);
//    }

//    @GetMapping("/{id}/{scheduleId}")
//    public ResponseEntity <TableEntity> getTableAvailabilityByIdAndDate(@PathVariable long id, @PathVariable long scheduleId){
//        TableEntity tableEntity = availabilityService.getTableAvailabilityByIdAndDate(id, scheduleId);
//        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
//    }


}


package com.upb.reservationservice.controller;

import com.upb.reservationservice.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/reservations")
public class TablesController {
    private static final Logger logger = Logger.getLogger(TablesController.class.getName());

    @Autowired
    private TableService tableService;


    @GetMapping("/available")
    public ResponseEntity<?> getAvailableTablesByDateRange(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try{
            if (startDate == null || endDate == null) {
                return ResponseEntity.badRequest().body("Start date and end date are required");
            }
            if (startDate.after(endDate)) {
                return ResponseEntity.badRequest().body("Start date cannot be after end date");
            }
            return ResponseEntity.ok(tableService.getAvailableTablesByDateRange(startDate, endDate));
        } catch (Exception e) {
            logger.severe("Error getting available tables by date range");
            return ResponseEntity.badRequest().body("Error getting available tables by date range");
        }
    }

}


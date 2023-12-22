package com.upb.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ReservationRequest {
    private Long tableId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datetime;
}

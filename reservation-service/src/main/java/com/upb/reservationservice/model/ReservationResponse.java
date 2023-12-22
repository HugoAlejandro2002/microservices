package com.upb.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upb.reservationservice.entity.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date datetime;
    private TableResponse table;
    private Long userId;
    private EStatus status;
}

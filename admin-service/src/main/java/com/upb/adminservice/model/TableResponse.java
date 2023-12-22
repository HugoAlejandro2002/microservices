package com.upb.adminservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableResponse {
    private Long id;
    private Long tableNumber;
    private Long capacity;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date availableTime;
}

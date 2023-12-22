package com.upb.adminservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="DINING_TABLE")
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "table_number")
    private Long tableNumber;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "available_time")
    private Date availableTime;

}

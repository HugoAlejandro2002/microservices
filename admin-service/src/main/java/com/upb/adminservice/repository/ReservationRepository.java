package com.upb.adminservice.repository;


import com.upb.adminservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByTable_IdAndDatetime(Long tableId, Date datetime);
}

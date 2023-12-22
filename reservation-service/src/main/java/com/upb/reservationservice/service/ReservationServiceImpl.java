package com.upb.reservationservice.service;

import com.upb.reservationservice.entity.DiningTable;
import com.upb.reservationservice.entity.EStatus;
import com.upb.reservationservice.entity.Reservation;
import com.upb.reservationservice.events.EventEmitter;
import com.upb.reservationservice.model.ReservationRequest;
import com.upb.reservationservice.model.ReservationResponse;
import com.upb.reservationservice.model.TableResponse;
import com.upb.reservationservice.repository.ReservationRepository;
import com.upb.reservationservice.repository.TableRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableRepository tableRepository;

    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest, long userId) {

        DiningTable table = tableRepository.findById(reservationRequest.getTableId()).orElseThrow(() -> new RuntimeException("Table not found"));

        Reservation reservation = Reservation.builder()
                .userId(userId)
                .table(table)
                .datetime(reservationRequest.getDatetime())
                .status(EStatus.PENDING)
                .build();

        Reservation reservationEntity = reservationRepository.save(reservation);

        TableResponse tableResponse = TableResponse.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .capacity(table.getCapacity())
                .availableTime(table.getAvailableTime())
                .build();

        return ReservationResponse.builder()
                .id(reservationEntity.getId())
                .datetime(reservationEntity.getDatetime())
                .table(tableResponse)
                .userId(reservationEntity.getUserId())
                .status(reservationEntity.getStatus())
                .build();
    }
//
//    @Override
//    public ReservationResponse getReservationById(long reservationId) {
//        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
//        ReservationResponse reservationResponse = new ReservationResponse();
//        BeanUtils.copyProperties(reservation,reservationResponse);
//        return reservationResponse;
//    }
//
//    @Override
//    public List<TableDTO> getAvailableTablesByDate(String date) {
//        // Get all reservations for the given date
//        List<Reservation> reservations = reservationRepository.findAll().stream()
//                .filter(reservation -> reservation.getReservationTime().equals(date))
//                .collect(Collectors.toList());
//
//        // Get all tables
//        List<TableDTO> allTables = availabilityService.getAllTables();
//
//        // Filter out tables that have reservations for the given date
//        List<TableDTO> availableTables = allTables.stream()
//                .filter(table -> reservations.stream().noneMatch(reservation -> reservation.getTableId().equals(table.getId())))
//                .collect(Collectors.toList());
//
//        return availableTables;
//    }
//
//    @Override
//    public List<ReservationResponse> getReservationsByDate(long scheduleId) {
//        List<Reservation> reservationEntities = reservationRepository.findAll();
//        List<ReservationResponse> reservationsList = reservationEntities .stream()
//                .filter((reservationEntity -> reservationEntity.getReservationTime().equals(scheduleService.getScheduleTime(scheduleId))))
//                .map(reservationEntity -> {
//                    ReservationResponse reservation = new ReservationResponse();
//                    BeanUtils.copyProperties(reservationEntity, reservation );
//                    return reservation;
//                }).collect(Collectors.toList());
//        return reservationsList;
//    }
//
//    @Override
//    public List<ReservationResponse> getReservationsByTable(long tableId) {
//        List<Reservation> reservationEntities = reservationRepository.findAll();
//        List<ReservationResponse> reservationsList = reservationEntities .stream()
//                .filter((reservationEntity -> reservationEntity.getTableId().equals(tableId)))
//                .map(reservationEntity -> {
//                    ReservationResponse reservation = new ReservationResponse();
//                    BeanUtils.copyProperties(reservationEntity, reservation );
//                    return reservation;
//                }).collect(Collectors.toList());
//        return reservationsList;
//    }
//
//    @Override
//    public boolean approveReservation(long id) {
//        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
//
//        if (optionalReservation.isPresent()) {
//            Reservation reservation = optionalReservation.get();
//
//            if (reservation.getStatus().equals("Pending")) {
//                reservation.setStatus("Approved");
//                ReservationUpdatedEvent reservationUpdatedEvent = new ReservationUpdatedEvent(reservation,reservation.getStatus());
//                eventEmitter.emitEvent(reservationUpdatedEvent);
//                reservationRepository.save(reservation);
//
//                return true;
//            }
//        }
//        return false;
//    }
}

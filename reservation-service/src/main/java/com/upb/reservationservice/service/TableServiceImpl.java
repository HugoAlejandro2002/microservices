package com.upb.reservationservice.service;

import com.upb.reservationservice.entity.DiningTable;
import com.upb.reservationservice.model.TableResponse;
import com.upb.reservationservice.repository.TableRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Override
    public List<TableResponse> getAvailableTablesByDateRange(Date startDate, Date endDate) {
        List<DiningTable> tables = tableRepository.findAll();
        endDate.setTime(endDate.getTime() + 1000 * 60 * 60 * 24);
        List<DiningTable> filteredTables = tables.stream().filter(table -> table.getAvailableTime().after(startDate) && table.getAvailableTime().before(endDate) && table.getAvailable()).toList();
        List<TableResponse> tableResponse = new ArrayList<>();
        for (DiningTable table : filteredTables) {
            TableResponse tableResponse1 = new TableResponse();
            BeanUtils.copyProperties(table, tableResponse1);
            tableResponse.add(tableResponse1);
        }
        return tableResponse;
    }
}


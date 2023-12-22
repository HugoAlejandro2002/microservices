package com.upb.reservationservice.service;


import com.upb.reservationservice.model.TableResponse;

import java.util.Date;
import java.util.List;

public interface TableService {

    List<TableResponse> getAvailableTablesByDateRange(Date startDate, Date endDate);
}


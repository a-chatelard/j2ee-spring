package com.esgi.tp_spring.helpers;

import com.esgi.tp_spring.dto.requests.TripRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class DTOService {

    public TripRequestDTO GenerateTripDTO(String username) {
        var tripDTO = new TripRequestDTO();
        tripDTO.setName("Sortie test");
        tripDTO.setDuration(10);
        tripDTO.setSupervisorUsername(username);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 10);
        tripDTO.setDate(c.getTime());

        return tripDTO;
    }
}

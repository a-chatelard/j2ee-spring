package com.esgi.tp_spring.helpers;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.entities.TripComplexity;
import com.esgi.tp_spring.entities.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

@Service
public class TripHelper {

    public Trip createTrip(User user) {
        var trip = new Trip();
        trip.setName("Sortie test");
        trip.setDescription("Sortie test description");
        trip.setLocation("Lyon");
        trip.setComplexity(TripComplexity.MEDIUM);
        trip.setDuration(8);
        trip.setSupervisor(user);
        trip.setParticipants(new HashSet<>());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 10);
        trip.setDate(c.getTime());

        return trip;
    }
}

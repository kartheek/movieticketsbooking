package com.app.movieticket.dtos;

import com.app.movieticket.models.Cinema;
import com.app.movieticket.models.Hall;
import com.app.movieticket.models.ShowSeat;
import com.app.movieticket.models.Show;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class CreateBookingDTO {

    // By the time user starts to book the tickets, we have
    // Cinema(theater), Hall, Show and seats to selected for the booking

    @NonNull
    Cinema cinema;
    @NonNull Hall hall;
    @NonNull
    Show show;
    @NonNull
    List<ShowSeat> showSeats;

    public CreateBookingDTO(Cinema theCinema, Hall theHall, Show theShow, List<ShowSeat> theShowSeats){
        this.cinema = theCinema;
        this.hall = theHall;
        this.show = theShow;
        this.showSeats = theShowSeats;
    }




}

package com.app.movieticket.dtos;

import com.app.movieticket.models.ShowSeat;
import com.app.movieticket.models.Show;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CreateBookingDTO {
    @NonNull
    Show show;
    @NonNull
    List<ShowSeat> showSeats;
}

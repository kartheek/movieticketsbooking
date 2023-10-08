package com.app.movieticket.services;

import com.app.movieticket.models.Cinema;
import com.app.movieticket.models.Movie;
import com.app.movieticket.models.Show;
import com.app.movieticket.models.ShowSeat;
import com.app.movieticket.repositories.CinemaRepository;
import com.app.movieticket.repositories.MovieRepository;
import com.app.movieticket.repositories.ShowRepository;
import com.app.movieticket.repositories.ShowSeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrowseMoviesUsecases {
    private final CinemaRepository cinemaRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;

    public BrowseMoviesUsecases(CinemaRepository cinemaRepository, ShowSeatRepository showSeatRepository, ShowRepository showRepository, MovieRepository movieRepository) {
        this.cinemaRepository = cinemaRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
    }

    public List<Cinema> listCinemas() {
        return cinemaRepository.findAll();
    }

    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    public List<Show> listShows() {
        return showRepository.findAll();
    }

    public List<ShowSeat> listShowSeats() {
        return showSeatRepository.findAll();
    }
}

package com.galvanize.gmdb.Service;

import com.galvanize.gmdb.Domain.GMDBMovie;
import com.galvanize.gmdb.Repository.GMDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GMDBService {
    @Autowired
    GMDBRepository repository;
    public Iterable<GMDBMovie> findAll(){
        return repository.findAll();
    }

    public GMDBMovie createMovie(GMDBMovie movie) {
        return repository.save(movie);
    }

    public Optional<GMDBMovie> findMovieByTitle(String title) {
        return repository.findMovieByTitle(title);
    }

    public GMDBMovie updateRating(String title, int rating) {
        GMDBMovie movie=repository.findMovieByTitle(title).get();
        if(movie.getRating()>=0){
            int newRating=(movie.getRating()+rating)/2;
            movie.setRating(newRating);
            repository.save(movie);
        }
        return movie;
    }
}

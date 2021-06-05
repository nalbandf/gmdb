package com.galvanize.gmdb.Controller;

import com.galvanize.gmdb.Domain.GMDBMovie;
import com.galvanize.gmdb.Domain.Review;
import com.galvanize.gmdb.Service.GMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.AttributedCharacterIterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gmdb")
public class GMDBController {

    @Autowired
    GMDBService service;
    @GetMapping("/movies")
    public ResponseEntity<Iterable<GMDBMovie>>  getAllMovies(){
        return new ResponseEntity(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/movies")
    public ResponseEntity<GMDBMovie> createMovie(@RequestBody GMDBMovie movie){
        return new ResponseEntity(service.createMovie(movie),HttpStatus.CREATED);
    }
    @GetMapping("/movies/{title}")
    public ResponseEntity<GMDBMovie> findMovieById(@PathVariable("title") String title){
        Optional<GMDBMovie> movie =service.findMovieByTitle(title);
        if(movie.isPresent())
            return new ResponseEntity(movie,HttpStatus.OK);
        return new ResponseEntity("The movie you searched on is not found",HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/movies/rating")
    public ResponseEntity<GMDBMovie> updateRating(@RequestParam("title") String title, @RequestBody Review review) {
        if (review.getRating() >= 0 && review.getRating() <= 5) {
            GMDBMovie movie = service.updateRating(title, review);
            return new ResponseEntity(movie, HttpStatus.OK);

        }
        return new ResponseEntity("Rating not provided", HttpStatus.OK);
    }
}

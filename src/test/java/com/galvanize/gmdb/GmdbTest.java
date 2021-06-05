package com.galvanize.gmdb;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.Domain.GMDBMovie;
import com.galvanize.gmdb.Domain.Review;
import com.galvanize.gmdb.Service.GMDBService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class GmdbTest {
    ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    // Mock beans help Spring automate injecting other Autowired objects
    @MockBean
//    @InjectMocks
            GMDBService service;

    @Test
    void getEmptyMovie() throws Exception {
        List<GMDBMovie> movieList = new ArrayList<>();
        // this method is from the Mockito library
        when(service.findAll()).thenReturn(movieList);
        mvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk());

    }

    @Test
    void postMovie() throws Exception {
        GMDBMovie movie = new GMDBMovie("abc", "tim", "actors", "2016", "description");

        when(service.createMovie(movie)).thenReturn(movie);
        String json = " {\n\"title\": \"Kanguri\",\n \"director\": \"Joss Whedon\",\n\"actors\": \"Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth\",\n    \"release\": \"2012\",\n    \"description\": \"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.\" \n   }";
        mvc
                .perform(
                        post("/gmdb/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void getMovieByTitleFound() throws Exception {
        GMDBMovie movie = new GMDBMovie("abc", "tim", "actors", "2016", "description");
        // this method is from the Mockito library
        when(service.findMovieByTitle("abc")).thenReturn(java.util.Optional.of(movie));
        mvc.perform(get("/gmdb/movies/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("abc"));

    }

    @Test
    void getAllMovie() throws Exception {
        List<GMDBMovie> movieList = new ArrayList<>();
        GMDBMovie movie1 = new GMDBMovie("abc", "tim", "actors", "2016", "description");
        GMDBMovie movie2 = new GMDBMovie("abc2", "tim", "actors", "2016", "description");
        movieList.add(movie1);
        movieList.add(movie2);
        when(service.findAll()).thenReturn(movieList);
        mvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("abc"))
                .andExpect(jsonPath("$[1].title").value("abc2"));
    }

    @Test
    void getMovieByTitleNotFound() throws Exception {
        GMDBMovie movie = new GMDBMovie("abc", "tim", "actors", "2016", "description");
        // Movie Found Senario
        when(service.createMovie(movie)).thenReturn(movie);
        when(service.findMovieByTitle("abc")).thenReturn(Optional.of(movie));
        mvc.perform(get("/gmdb/movies/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("abc"));

        //Movie Not Found Senario
        mvc.perform(get("/gmdb/movies/abc2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The movie you searched on is not found"));
    }

    @Test
    void updateMovieRatingTest() throws Exception {
        List<Review> reviewList = new ArrayList<>();
        Review review = new Review();
        review.setRating(4);
        review.setReviewText("Funny movie");
        reviewList.add(review);
        GMDBMovie movie = new GMDBMovie("abc", "tim", "actors", "2016", "description", reviewList);
        when(service.createMovie(movie)).thenReturn(movie);
        when(service.findMovieByTitle("abc")).thenReturn(Optional.of(movie));

        mvc.perform(get("/gmdb/movies/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("abc"));

        GMDBMovie movie1 = new GMDBMovie("abc", "tim", "actors", "2016", "description", reviewList);

       // when(service.updateRating("abc",4)).thenReturn(movie1);
        String json = " {\n\"rating\": \"4\",\n \"reviewText\": \"JFunny movie\"\n}";

        mvc.perform(patch("/gmdb/movies/rating?title=abc").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isOk());

        }
}


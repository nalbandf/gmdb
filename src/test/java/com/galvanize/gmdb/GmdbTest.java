package com.galvanize.gmdb;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.Domain.GMDBMovie;
import com.galvanize.gmdb.Service.GMDBService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
    List<GMDBMovie> movieList;
    @Autowired
    private MockMvc mvc;

    // Mock beans help Spring automate injecting other Autowired objects
    @MockBean
//    @InjectMocks
    GMDBService service;

    @Test
    void getAllMovie() throws Exception {
        // this method is from the Mockito library
        when(service.findAll()).thenReturn(movieList);
        mvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
        .andExpect(content().string(""));

    }
    @Test
    void postMovie()throws Exception{
        mapper=new ObjectMapper();

        String json=" {\n\"title\": \"Kanguri\",\n \"director\": \"Joss Whedon\",\n\"actors\": \"Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth\",\n    \"release\": \"2012\",\n    \"description\": \"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.\",\n    \"rating\": 2\n  }";
        mvc
                .perform(
                        post("/gmdb/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andExpect(status().isCreated());
                //.andExpect(jsonPath("$.title").exists())
                //.andExpect(jsonPath("$.GMDBMovie.director").value("Joss Whedon"))
                //.andExpect(jsonPath("$.GMDBMovie.rating").value(2));
    }



    }


package com.galvanize.gmdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.Domain.GMDBMovie;
import com.galvanize.gmdb.Service.GMDBService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
class GmdbApplicationTests {
	ObjectMapper mapper;
	Iterable<GMDBMovie> movieList;
	@Autowired
	private MockMvc mvc;

	// Mock beans help Spring automate injecting other Autowired objects
	@MockBean
	GMDBService service;

	@Test
	void contextLoads() {
	}

	@Test
	void getAllMovie() throws Exception {
		// this method is from the Mockito library
		when(service.findAll()).thenReturn(movieList);

		mvc.perform(get("/gmdb/movies"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

}

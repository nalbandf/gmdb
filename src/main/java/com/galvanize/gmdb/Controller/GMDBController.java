package com.galvanize.gmdb.Controller;

import com.galvanize.gmdb.Domain.GMDBMovie;
import com.galvanize.gmdb.Service.GMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gmdb")
public class GMDBController {

    @Autowired
    GMDBService service;
    @GetMapping("/movies")
    public ResponseEntity<Iterable<GMDBMovie>>  getAllMovies(){
        return new ResponseEntity(service.findAll(), HttpStatus.OK);
    }
}

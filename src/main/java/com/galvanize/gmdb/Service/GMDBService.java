package com.galvanize.gmdb.Service;

import com.galvanize.gmdb.Domain.GMDBMovie;
import com.galvanize.gmdb.Repository.GMDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GMDBService {
    @Autowired
    GMDBRepository repository;
    public Iterable<GMDBMovie> findAll(){
        return repository.findAll();
    }
}

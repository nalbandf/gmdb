package com.galvanize.gmdb.Repository;

import com.galvanize.gmdb.Domain.GMDBMovie;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GMDBRepository extends CrudRepository<GMDBMovie, Long> {
    Optional<GMDBMovie> findMovieByTitle(String title);

}

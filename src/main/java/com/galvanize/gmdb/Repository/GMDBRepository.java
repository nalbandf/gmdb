package com.galvanize.gmdb.Repository;

import com.galvanize.gmdb.Domain.GMDBMovie;
import org.springframework.data.repository.CrudRepository;

public interface GMDBRepository extends CrudRepository<GMDBMovie, Long> {

}

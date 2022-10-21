package com.doclab.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface doclabRepository extends CrudRepository<doclab, Long> {

    List<doclab> findAllByOrderByIdAsc();

}

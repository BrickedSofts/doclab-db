package com.doclab.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class doclabService implements IdoclabService {

    @Autowired
    private doclabRepository repository;

    @Override
    public List<doclab> find() {
        return repository.findAllByOrderByIdAsc();
    }

    @Override
    public doclab create(doclab doclab) {
        return repository.save(doclab);
    }

    @Override
    public doclab update(Long id, int quantity) {
        return repository.findById(id)
            .map(existingdoclab -> {
                existingdoclab.setQuantity(quantity);
                return repository.save(existingdoclab);
            })
            .orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}

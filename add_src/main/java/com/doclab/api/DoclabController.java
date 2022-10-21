package com.doclab.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class doclabController {

    @Autowired
    private doclabService service;

    @GetMapping("/doclab")
    public List<doclab> find() {
        return service.find();
    }

    @PostMapping("/doclab")
    @ResponseStatus(HttpStatus.CREATED)
    public doclab create(@RequestBody doclab doclab) {
        return service.create(doclab);
    }

    @PutMapping("/doclab/{id}")
    public doclab update(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        return service.update(id, (int) payload.get("quantity"));
    }

    @DeleteMapping("/doclab/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}

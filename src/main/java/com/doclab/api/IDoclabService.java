package com.doclab.api;

import java.util.List;

public interface IdoclabService {

    List<doclab> find();
    doclab create(doclab doclab);
    doclab update(Long id, int quantity);
    void delete(Long id);

}

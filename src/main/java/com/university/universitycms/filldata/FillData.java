package com.university.universitycms.filldata;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("fill-dev-data")
public class FillData {
    private final List<DataFiller> dataFillers;

    @Autowired
    public FillData(List<DataFiller> dataFillers) {
        this.dataFillers = dataFillers;
    }

    @PostConstruct
    public void fill(){
        dataFillers.forEach(DataFiller::fillData);
    }
}

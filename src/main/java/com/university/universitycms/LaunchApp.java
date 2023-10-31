package com.university.universitycms;

import com.university.universitycms.services.datafilling.DataFiller;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaunchApp {

    private final List<DataFiller> dataFillers;

    @Autowired
    public LaunchApp(List<DataFiller> dataFillers) {
        this.dataFillers = dataFillers;
    }

//    @PostConstruct
    public void launch(){
        dataFillers.forEach(DataFiller::fillData);
    }
}

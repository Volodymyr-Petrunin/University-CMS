package com.university.universitycms.generations;

import java.util.List;

public interface GenerationData<T> {
    List<T> generateData();
}

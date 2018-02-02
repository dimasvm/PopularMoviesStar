package com.example.dimas.popular_movies_star.data.model;

import java.util.List;

/**
 * Created by       : dimas on 01/02/18.
 * Email            : araymaulana66@gmail.com
 */

public class ResponseTrailer {

    private int id;
    private List<Trailer> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}

package com.example.springboot.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.lang.String.format;

public class Pagination {

    @JsonProperty("next_url")
    private final String nextUrl;

    public Pagination(Integer limit, Integer afterId) {
        nextUrl = format("/payments?limit=%s&after_id=%s", limit, afterId + limit);
    }

    public String getNextUrl() {
        return nextUrl;
    }
}

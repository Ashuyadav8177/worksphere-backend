package com.worksphere.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GlobalSearchResponse {

    private List<SearchResult> employees;
    private List<SearchResult> departments;
    private List<SearchResult> tasks;
    private List<SearchResult> leaves;

    @Getter
    @Setter
    public static class SearchResult {

        private Long id;
        private String title;
        private String subtitle;
        private String type;

        public SearchResult(Long id, String title, String subtitle, String type) {
            this.id = id;
            this.title = title;
            this.subtitle = subtitle;
            this.type = type;
        }
    }
}
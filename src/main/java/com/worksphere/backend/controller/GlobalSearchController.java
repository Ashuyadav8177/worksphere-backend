package com.worksphere.backend.controller;

import com.worksphere.backend.dto.GlobalSearchResponse;
import com.worksphere.backend.service.GlobalSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class GlobalSearchController {

    private final GlobalSearchService globalSearchService;

    public GlobalSearchController(GlobalSearchService globalSearchService) {
        this.globalSearchService = globalSearchService;
    }

    @GetMapping
    public ResponseEntity<GlobalSearchResponse> search(
            @RequestParam String q
    ) {
        GlobalSearchResponse response =
                globalSearchService.search(q);

        return ResponseEntity.ok(response);
    }
}
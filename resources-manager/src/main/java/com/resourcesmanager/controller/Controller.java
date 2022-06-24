package com.resourcesmanager.controller;

import com.resourcesmanager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    TestService testService;

    @PostMapping("/csv-days-combinations")
    public ResponseEntity<ByteArrayResource> retrieveCombinations(@RequestBody byte[] csv, @RequestParam("budget") double budget) throws Exception {
        byte[] csvOutput = testService.retrieveDaysCombinationCsv(csv, budget);
        ByteArrayResource resource = new ByteArrayResource(csvOutput);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=output.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(csvOutput.length)
                .body(resource);
    }


}

package com.resourcesmanager.service;

import com.resourcesmanager.models.Job;
import com.resourcesmanager.models.Subset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestService {
    @Autowired
    KnapSackService knapSackService;
    @Autowired
    OpenCsvService openCsvService;

    public byte[] retrieveDaysCombinationCsv(byte[] csv, double budget) throws Exception {
        Reader reader = new InputStreamReader(new ByteArrayInputStream(csv));
        List<Job> jobs = openCsvService.mapCsvToListOfJobs(reader);
        List<Subset> subsets = new ArrayList<>();
        Subset initialSubset = new Subset();
        initialSubset.setPickedJobList(new ArrayList<>());
        subsets.add(initialSubset);
        knapSackService.knapsackAlgorithm(budget, budget, jobs, subsets, 0, 0);
        List<Subset> noDuplicatesSubsets = new ArrayList<>(new HashSet<>(subsets));
        Collections.sort(noDuplicatesSubsets);
        List<Subset> filtered = noDuplicatesSubsets.stream().filter(subset -> subset.getPickedJobList().size() > 0).collect(Collectors.toList());
        byte[] csvOutput = openCsvService.csvWriterOneByOne(filtered, jobs, budget);
        return csvOutput;
    }
}

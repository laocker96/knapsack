package com.resourcesmanager.service;

import com.resourcesmanager.models.Job;
import com.resourcesmanager.models.Subset;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KnapSackService {

    private void countDaysPerJob(Subset subset) {
        Map<String, Long> daysPerJob = subset.getPickedJobList().stream()
                .collect(Collectors.groupingBy(job -> job.getJobTitle(),
                        Collectors.counting()));
        subset.setDaysPerJob(daysPerJob);
    }

    private void setBudgetLeftAndProfit(final double BUDGET, Subset subset) {
        double budgetUsed = 0;
        int maxProfit = 0;
        for (Job job : subset.getPickedJobList()) {
            budgetUsed += job.getDailyCost();
            maxProfit += job.getPriority();
        }
        subset.setBudgetLeft(BUDGET - budgetUsed);
        subset.setMaxProfit(maxProfit);
    }

    public void knapsackAlgorithm(final double BUDGET, double budget, List<Job> jobs, List<Subset> subsets, int index, int node) {

        // LIMIT CASE
        if(budget <= 0) {
            System.out.println("Last node: " + node);
        }

        if (budget > 0) {
            for (int i = 0; i < jobs.size(); i++) {
                double budgetLeft = budget - jobs.get(i).getDailyCost();
                if (budgetLeft > 0) {
                    subsets.get(index).getPickedJobList().add(jobs.get(i));
                    knapsackAlgorithm(BUDGET, budgetLeft, jobs, subsets, index, node + 1);
                }
                Collections.sort(subsets.get(index).getPickedJobList()); // Order Subset pickedJobs ascending by jobTitle
                setBudgetLeftAndProfit(BUDGET, subsets.get(index));
                countDaysPerJob(subsets.get(index)); // Set days per job of Subset

                if (i < jobs.size() - 1) {
                    Subset subset = new Subset();
                    subset.setPickedJobList(new ArrayList<>());
                    subsets.add(subset);
                    index = subsets.size() - 1;
                    for (int j = 0; j < node; j++) {
                        if (subsets.get(index).getPickedJobList().size() > 0) {
                            subsets.get(index).getPickedJobList().add(subsets.get(index - 1).getPickedJobList().get(i));
                        }
                    }
                }
            }
        }
    }
}

// TEST MAIN
//    public static void main(String args[]) throws Exception {
//        final double BUDGET = 3;
//        double budget = 3;
//
//        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("static/test.csv").toURI()));
//        OpenCsvUtil openCsvUtil = new OpenCsvUtil();
//        List<Job> jobs = openCsvUtil.mapCsvToListOfJobs(reader);
//
//        List<Subset> subsets = new ArrayList<>();
//        Subset initialSubset = new Subset();
//        initialSubset.setPickedJobList(new ArrayList<>());
//        subsets.add(initialSubset);
//
//        knapsackAlgorithm(BUDGET, budget, jobs, subsets, 0, 0);
//
//        List<Subset> noDuplicatesSubsets = new ArrayList<>(new HashSet<>(subsets));
//        Collections.sort(noDuplicatesSubsets);
//
//        noDuplicatesSubsets.stream()
//                .filter(subset -> subset.getPickedJobList().size() > 0).forEach(subset -> {
//                    subset.getPickedJobList().forEach(job -> {
//                        System.out.print(job);
//                    });
//                    System.out.println("\nBUDGET LEFT: " + subset.getBudgetLeft());
//                    System.out.println("MAX PROFIT: " + subset.getMaxProfit() + "\n");
//                });
//
////        subsets.stream()
////                .filter(subset ->
////                        subset.getCapacityLeft() <= budget * 0.05 // 5% del budegt rimasto è accettabile
////                ).forEach(subset -> {
////                    System.out.println(subset.getCapacityLeft());
////                });
//
//    }
//}


//        List<Job> jobs = new ArrayList<>();
//        Job job1 = new Job();
//        job1.setJobTitle("ab");
//        job1.setDailyCost(1);
//        job1.setPriority(1);
//        Job job2 = new Job();
//        job2.setJobTitle("b");
//        job2.setDailyCost(2);
//        job2.setPriority(5);
//        Job job3 = new Job();
//        job3.setJobTitle("c");
//        job3.setDailyCost(4);
//        job3.setPriority(2);
//        jobs.add(job1);
//        jobs.add(job2);
//        jobs.add(job3);

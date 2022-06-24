package com.resourcesmanager.models;

import java.util.List;
import java.util.Map;

public class Subset implements Comparable<Subset> {
    private List<Job> pickedJobList;
    private Map<String, Long> daysPerJob;
    private int maxProfit;
    private double budgetLeft;

    public List<Job> getPickedJobList() {
        return pickedJobList;
    }

    public void setPickedJobList(List<Job> pickedJobList) {
        this.pickedJobList = pickedJobList;
    }

    public Map<String, Long> getDaysPerJob() {
        return daysPerJob;
    }

    public void setDaysPerJob(Map<String, Long> daysPerJob) {
        this.daysPerJob = daysPerJob;
    }

    public int getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(int maxProfit) {
        this.maxProfit = maxProfit;
    }

    public double getBudgetLeft() {
        return budgetLeft;
    }

    public void setBudgetLeft(double budgetLeft) {
        this.budgetLeft = budgetLeft;
    }

    @Override
    public int compareTo(Subset o) {
        return Double.compare(this.budgetLeft, o.budgetLeft);
    }

    // TODO: Implements compare with multiple fields with comparator
}
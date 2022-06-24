package com.resourcesmanager.models;

import com.opencsv.bean.CsvBindByPosition;

public class Job implements Comparable<Job> {
    @CsvBindByPosition(position = 0)
    private String jobTitle;
    @CsvBindByPosition(position = 1)
    private double dailyCost;
    @CsvBindByPosition(position = 2)
    private int priority;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(double dailyCost) {
        this.dailyCost = dailyCost;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Job o) {
        return this.jobTitle.compareTo(o.jobTitle);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobTitle='" + jobTitle + '\'' +
                ", dailyCost=" + dailyCost +
                ", priority=" + priority +
                '}';
    }
}

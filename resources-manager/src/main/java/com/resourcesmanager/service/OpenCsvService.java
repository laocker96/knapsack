package com.resourcesmanager.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.resourcesmanager.models.Job;
import com.resourcesmanager.models.Subset;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenCsvService {

    public List<Job> mapCsvToListOfJobs(Reader reader) throws Exception {
        List<Job> jobs = new ArrayList<>();
        CSVReader csvReader = new CSVReader(reader, ';');
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            Job job = new Job();
            job.setJobTitle(line[0]);
            job.setDailyCost(Double.valueOf(line[1].replace(',', '.')));
            job.setPriority(Integer.valueOf(line[2]));
            jobs.add(job);
        }
        reader.close();
        csvReader.close();
        return jobs;
    }

    public byte[] csvWriterOneByOne(List<Subset> subsets, List<Job> jobs, double budget) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
        CSVWriter writer = new CSVWriter(streamWriter, ';');

// https://stackoverflow.com/questions/35884180/opencsv-avoid-using-filewriter-and-return-inputstream
        String headers[] = {"Job position", "Daily cost", "Priority", "Days", "Max profit", "Budget left", "Budget"};
        writer.writeNext(headers);
        subsets.forEach(subset -> {
            jobs.forEach(job -> {
                        writer.writeNext(new String[]{
                                job.getJobTitle(),
                                String.valueOf(job.getDailyCost()).replace('.', ','),
                                String.valueOf(job.getPriority()),
                                String.valueOf(subset.getDaysPerJob().get(job.getJobTitle()) == null ? 0 : subset.getDaysPerJob().get(job.getJobTitle()))});
                    }
            );
            writer.writeNext(new String[]{
                    "",
                    "",
                    "",
                    "",
                    String.valueOf(subset.getMaxProfit()),
                    String.valueOf(subset.getBudgetLeft()).replace('.', ','),
                    String.valueOf(budget).replace('.', ',')});
            writer.writeNext(new String[]{});
        });

        writer.close();
        //        streamWriter.flush();
        byte[] byteArrayOutputStream = stream.toByteArray();
        return byteArrayOutputStream;
    }

}

//TEST MAIN
//    public static void main(String args[]) throws Exception {
//        Reader reader = Files.newBufferedReader(Paths.get(
//                ClassLoader.getSystemResource("static/test.csv").toURI()));
//        mapCsvToListOfJobs(reader).forEach(job -> {
//            System.out.println(job);
//        });
//
//    }
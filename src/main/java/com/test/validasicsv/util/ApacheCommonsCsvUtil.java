package com.test.validasicsv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.test.validasicsv.model.Report;

public class ApacheCommonsCsvUtil {
private static String csvExtension = "csv";
	
	public static void customersToCsv(Writer writer, List<Report> customers) throws IOException {

		try (CSVPrinter csvPrinter = new CSVPrinter(writer,
				CSVFormat.DEFAULT.withHeader("ProjectID", "Year", "TotalValue", "Status"));) {
			for (Report customer : customers) {
				List<String> data = Arrays.asList(String.valueOf(customer.getProjectId()), String.valueOf(customer.getYear()),
						String.valueOf(customer.getTotalValue()), customer.getStatus());

				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
		} catch (Exception e) {
			System.out.println("Writing CSV error!");
			e.printStackTrace();
		}
	}

	public static List<Report> parseCsvFile(InputStream is) {
		BufferedReader fileReader = null;
		CSVParser csvParser = null;

		List<Report> reports = new ArrayList<Report>();

		try {
			fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			csvParser = new CSVParser(fileReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
//
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {

				String[] values = csvRecord.get(0).split(",");
//				System.out.println(values[3]);
				Report report = new Report(
						values[0], 
						Integer.parseInt(values[1]),
						Integer.parseInt(values[2]), 
						values[3]);

				reports.add(report);
			}

		} catch (Exception e) {
			System.out.println("Reading CSV Error!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				csvParser.close();
			} catch (IOException e) {
				System.out.println("Closing fileReader/csvParser Error!");
				e.printStackTrace();
			}
		}

		return reports;
	}
	
	public static boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];
		
		if(!extension.equals(csvExtension)) {
			return false;
		}
		
		return true;
	}
}

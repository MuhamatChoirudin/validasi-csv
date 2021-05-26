package com.test.validasicsv.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.test.validasicsv.model.Report;

public class OpenCsvUtil {
	public static List<Report> parseCsvFile(InputStream is) {
		String[] CSV_HEADER = { "id", "name", "address", "age" };
		Reader fileReader = null;
		CsvToBean<Report> csvToBean = null;
	
		List<Report> customers = new ArrayList<Report>();
		
		try {
			fileReader = new InputStreamReader(is);
	
			ColumnPositionMappingStrategy<Report> mappingStrategy = new ColumnPositionMappingStrategy<Report>();
	
			mappingStrategy.setType(Report.class);
			mappingStrategy.setColumnMapping(CSV_HEADER);
	
			csvToBean = new CsvToBeanBuilder<Report>(fileReader).withMappingStrategy(mappingStrategy).withSkipLines(1)
					.withIgnoreLeadingWhiteSpace(true).build();
	
			customers = csvToBean.parse();
			
			return customers;
		} catch (Exception e) {
			System.out.println("Reading CSV Error!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Closing fileReader/csvParser Error!");
				e.printStackTrace();
			}
		}
		
		return customers;
	}

	public static void customersToCsv(Writer writer, List<Report> customers) {
		String[] CSV_HEADER = { "id", "name", "address", "age" };
	    
	    StatefulBeanToCsv<Report> beanToCsv = null;
	 
	    try {
	      // write List of Objects
	      ColumnPositionMappingStrategy<Report> mappingStrategy = 
	                new ColumnPositionMappingStrategy<Report>();
	      
	      mappingStrategy.setType(Report.class);
	      mappingStrategy.setColumnMapping(CSV_HEADER);
	      
	      beanToCsv = new StatefulBeanToCsvBuilder<Report>(writer)
	          .withMappingStrategy(mappingStrategy)
	                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	                    .build();
	 
	      beanToCsv.write(customers);
	    } catch (Exception e) {
	      System.out.println("Writing CSV error!");
	      e.printStackTrace();
	    }
	}
}

package com.test.validasicsv.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.test.validasicsv.model.Report;
import com.test.validasicsv.util.ApacheCommonsCsvUtil;

@Service
public class ReportService {
	public void store(InputStream file) {
		try {
			String projectIdNext = null;
			int yearNext = 0;
			int totalValueNext = 0;
			List<Report> listCustomers = ApacheCommonsCsvUtil.parseCsvFile(file);
			for (int a=0;a<listCustomers.size();a++) {
				if(a<listCustomers.size()-1) {
				projectIdNext = listCustomers.get(a+1).getProjectId();
				yearNext = listCustomers.get(a+1).getYear();
				totalValueNext = listCustomers.get(a+1).getTotalValue();
				}
//				if(a==0) {
//				projectIdAwal = listCustomers.get(a).getProjectId();
//				yearAwal = listCustomers.get(a).getYear();
//				totalValueAwal = listCustomers.get(a).getTotalValue();
//				}
				if(listCustomers.get(a).getProjectId().equals(projectIdNext)
						&&yearNext>=listCustomers.get(a).getYear()
						&&totalValueNext>=listCustomers.get(a).getTotalValue()) {
					listCustomers.get(a).setStatus("1");
				}else if (!listCustomers.get(a).getProjectId().equals(projectIdNext)||a==listCustomers.size()-1) {
					listCustomers.get(a).setStatus("1");
				}else {
					listCustomers.get(a).setStatus("0");
				}
				System.out.println("data ke-"+listCustomers.get(a).getProjectId()+" "+listCustomers.get(a).getStatus());

			}
			
		} catch(Exception e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}
	
	// Load Data to CSV File
    public void loadFile(Writer writer) throws IOException {
    	try {
        //	List<Report> customers = (List<Report>) customerRepository.findAll();
        	
        	// Using ApacheCommons Csv Utils to write Customer List objects to a Writer
       //      ApacheCommonsCsvUtil.customersToCsv(writer, customers);
        	
        	// Using Open CSV Utils to write Customer List objects to a Writer
        	// OpenCsvUtil.customersToCsv(writer, customers);    		
    	} catch(Exception e) {
    		throw new RuntimeException("Fail! -> Message = " + e.getMessage());
    	}
    }
}

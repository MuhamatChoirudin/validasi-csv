package com.test.validasicsv.model;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Report {
	
	@CsvBindByName(column = "projectId")
	String projectId;
	
	@CsvBindByName(column = "year")
	int year;
	
	@CsvBindByName(column = "totalValue")
	int totalValue;
	
	@CsvBindByName(column = "status")
	String status;
}

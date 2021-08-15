package com.au.pei.express.mapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.au.pei.express.exception.CounterException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CsvGenerationMapper {

	public void generateCSV(List<List<String>> csvBody,HttpServletResponse response) {

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= TopCount.csv");
		response.setContentType("text/csv");
		// closing resources by using a try with resources
		try (
				// defining the CSV printer
				CSVPrinter csvPrinter = new CSVPrinter(
						new PrintWriter(response.getWriter()),
						CSVFormat.EXCEL.withDelimiter('|')
						);
				) {
			// populating the CSV content
			for (List<String> record : csvBody)
				csvPrinter.printRecord(record);

			// writing the underlying stream
			csvPrinter.flush();
		} catch (IOException e) {
			log.error("Error while generating CSV: ",e.getMessage());
			throw new CounterException(e.getMessage());
		}
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= TopCount.csv");
		response.setContentType("text/csv");

	}

}

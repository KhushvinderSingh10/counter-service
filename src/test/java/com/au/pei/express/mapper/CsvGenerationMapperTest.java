package com.au.pei.express.mapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.au.pei.express.exception.CounterException;

@ExtendWith(MockitoExtension.class)
class CsvGenerationMapperTest {

	
	CsvGenerationMapper csvMapper = new CsvGenerationMapper();
	
	@Mock
    private HttpServletResponse response;
	
	@Test
	void WhenCsvMapper_WithValidRequest_ThenSuccess() throws IOException {
		
		Mockito.when(response.getWriter()).thenReturn(Mockito.mock(PrintWriter.class));
		Assertions.assertDoesNotThrow(() -> csvMapper.generateCSV(getList(), response));
	}
	
	@Test
	void WhenCsvMapper_WithIOException_ThenExpectCustomException() throws IOException {
		
		Mockito.when(response.getWriter()).thenThrow(IOException.class);
		Assertions.assertThrows(CounterException.class, () -> csvMapper.generateCSV(getList(), response));
	}
	
	
	private List<List<String>> getList() {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("Vel","1"));
		return list;
	}

}

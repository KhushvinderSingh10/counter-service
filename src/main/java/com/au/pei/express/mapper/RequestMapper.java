package com.au.pei.express.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.au.pei.express.model.CountResponse;

public class RequestMapper {

	private RequestMapper() {
		// TODO Auto-generated constructor stub
	}

	public static CountResponse toCountResponse(Map<String,Integer> resultMap) {

		CountResponse  response = new CountResponse();
		response.getCounts().addAll(resultMap.entrySet());
		return response;

	}

	public static List<List<String>> toCSVObject(List<Entry<String, Integer>> request) {

		List<List<String>> response = new ArrayList<>();
		request.forEach( requestRow -> {
			List<String> responseRow = new ArrayList<>();
			responseRow.add(requestRow.getKey());
			responseRow.add(requestRow.getValue().toString());
			response.add(responseRow);
		});

		return response;
	}

}

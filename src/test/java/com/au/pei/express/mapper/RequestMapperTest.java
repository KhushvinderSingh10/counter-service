package com.au.pei.express.mapper;


import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.au.pei.express.exception.TopParamException;
import com.au.pei.express.model.SearchRequest;
import com.au.pei.express.repository.CounterRepository;
import com.au.pei.express.model.CountResponse;

class RequestMapperTest {

	@Test
	void WhentoCountResponse_HasValidRequest_ThenValidMapping() {
		CountResponse response = RequestMapper.toCountResponse(getMap());
		Assert.assertNotNull(response);
		Assert.assertEquals(5, response.getCounts().size());
		Assert.assertEquals(9, response.getCounts().get(1).getValue().intValue());
		Assert.assertEquals("Test", response.getCounts().get(3).getKey());
	}
	
	
	@Test
	void WhentoCSVObject_HasValidRequest_ThenValidMapping() {
		
		List<Entry<String, Integer>> list = getMap().entrySet()
		.stream()
		.collect(Collectors.toList());
		List<List<String>> response = RequestMapper.toCSVObject(list);
		Assert.assertNotNull(response);
		Assert.assertEquals("11", response.get(0).get(1));
		Assert.assertEquals("abc", response.get(1).get(0));
		Assert.assertEquals("String", response.get(4).get(0));
	}
	
	private Map<String,Integer> getMap() {
		Map<String,Integer> resultMap = new LinkedHashMap<>();
		resultMap.put("vget", 11);
		resultMap.put("abc", 9);
		resultMap.put("xyz", 7);
		resultMap.put("Test", 5);
		resultMap.put("String", 2);
		return resultMap;
	}

}

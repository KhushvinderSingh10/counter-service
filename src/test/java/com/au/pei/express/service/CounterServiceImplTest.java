package com.au.pei.express.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.au.pei.express.exception.TopParamException;
import com.au.pei.express.model.SearchRequest;
import com.au.pei.express.repository.CounterRepository;


@ExtendWith(MockitoExtension.class)
public class CounterServiceImplTest {

    @Mock
    private CounterRepository counterRepo;
    
    @InjectMocks
    private CounterServiceImpl mockService;
     
    @Test
    public void whenGetTop_ValueIsNull_ThenExpectCustomException() {    	
        Assertions.assertThrows(TopParamException.class, () -> mockService.getTopWords(null));
        
    }
    
    @Test
    public void whenGetTop_ValueIsNegative_ThenExpectCustomException() {
    	
        Assertions.assertThrows(TopParamException.class, () -> mockService.getTopWords(-1));
        
    }
    
    @Test
    public void whenGetTop_ValueIsBig_ThenExpectCustomException() {
    	
        Assertions.assertThrows(TopParamException.class, () -> mockService.getTopWords(6));
        
    }
    
    @Test
    public void whenGetTop_ValueIsCorrect_ThenSuccess() throws IOException {
        Mockito.when(counterRepo.getWordCountMap())
                .thenReturn(mockSearchMap());
    	List<Entry<String, Integer>> listResponse = mockService.getTopWords(2);
    	Assert.assertNotNull(listResponse);
    	Assert.assertEquals(2,listResponse.size());
    	Assert.assertEquals("vel",listResponse.get(0).getKey());
    	Assert.assertEquals(16,listResponse.get(1).getValue().intValue());
    	Assertions.assertDoesNotThrow(() -> mockService.getTopWords(2));
        
    }
    
    
    @Test
    public void whenSearch_Correct_ThenOccurancesRetrieved() throws IOException {
        Mockito.when(counterRepo.getWordCountMap())
                .thenReturn(mockSearchMap());
    	Map<String,Integer> response = mockService.searchWords(mockSearchRequest());
        Assert.assertNotNull(response);
        Assert.assertEquals(3,response.size());
        Assert.assertEquals(17,response.entrySet().stream().filter(s -> s.getKey().equalsIgnoreCase("vel")).findAny().get().getValue().intValue());
        Assert.assertEquals(15,response.entrySet().stream().filter(s -> s.getKey().equalsIgnoreCase("in")).findAny().get().getValue().intValue());
        Assert.assertEquals(0,response.entrySet().stream().filter(s -> s.getKey().equalsIgnoreCase("bar")).findAny().get().getValue().intValue());
        
    }

    private  Map<String,Integer> mockSearchMap() {
    	
    	Map<String, Integer> map = new LinkedHashMap<>();
    	map.put("vel", 17);
    	map.put("sed", 16);
    	map.put("in",15);
    	map.put("eget",16);
    	map.put("et",14);
    	
    	return map;
    }
    
    private  SearchRequest mockSearchRequest() {
    	SearchRequest searchRequest = new SearchRequest();
    	searchRequest.setSearchText(Arrays.asList("Vel","iN","bar",null,null));
    	return searchRequest;
    }
}
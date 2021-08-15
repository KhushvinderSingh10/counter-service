package com.au.pei.express.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.au.pei.express.model.SearchRequest;

public interface CounterService {

	List<Entry<String, Integer>> getTopWords(Integer top);

	Map<String,Integer> searchWords(SearchRequest searchRequest);
}

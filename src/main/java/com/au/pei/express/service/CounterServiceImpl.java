package com.au.pei.express.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.au.pei.express.exception.TopParamException;
import com.au.pei.express.model.SearchRequest;
import com.au.pei.express.repository.CounterRepository;

/**
 * Service class provides business logic implementation for different Counter Api operations.
 */
@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	CounterRepository counterRepo;

	@Override
	public List<Entry<String, Integer>> getTopWords(Integer top) {

		if(top == null)
			throw new TopParamException("Invalid Param. Param cannot be Null or Empty. Please provide an integer");

		if(top.intValue() <1 || top.intValue() > counterRepo.getWordCountMap().size())
			throw new TopParamException("Invalid Param. Please select a number between 1 and "+counterRepo.getWordCountMap().size()+ " (Both Inclusive).");

		return counterRepo.getWordCountMap()		
				.entrySet()
				.stream()
				.limit(top)
				.collect(Collectors.toList());
	}


	@Override
	public Map<String,Integer> searchWords(SearchRequest searchRequest) {
		Map<String,Integer> searchMap = counterRepo.getWordCountMap();
		Map<String,Integer> resultMap = new HashMap<>();

		searchRequest.getSearchText().stream().filter(req->req!=null).forEach( req -> {
			if (req!=null && searchMap.get(req.toLowerCase()) != null ) {
				resultMap.put(req,searchMap.get(req.toLowerCase()));
			} else {
				resultMap.put(req,0);
			}
		});

		return resultMap;

	}

}

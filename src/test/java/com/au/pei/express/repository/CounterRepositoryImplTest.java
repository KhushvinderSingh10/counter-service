package com.au.pei.express.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CounterRepositoryImplTest {

	CounterRepository repo = new CounterRepositoryImpl();

	@Test
	public void whenGetLoadTextFileSuccessfulThanOccurances() {
		Map <String,Integer> responseMap  = repo.getWordCountMap();
		Assert.assertEquals(175,responseMap.size());

		Assert.assertEquals(3,responseMap.get("hendrerit").intValue());
		Assert.assertEquals(13,responseMap.get("ut").intValue());
		Assert.assertEquals(null,responseMap.get("123"));

	}

	@Test
	public void whenGetLoadTextFileSuccessfulThanOrder() {

		List<Entry<String, Integer>> topList = repo.getWordCountMap().entrySet()
				.stream()
				.limit(6)
				.collect(Collectors.toList());

		Assert.assertEquals(6,topList.size());
		Assert.assertEquals("vel",topList.get(0).getKey());
		Assert.assertEquals("sed",topList.get(2).getKey());
		Assert.assertEquals("in",topList.get(3).getKey());



	}

}

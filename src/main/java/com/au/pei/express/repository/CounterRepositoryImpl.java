package com.au.pei.express.repository;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.au.pei.express.exception.CounterException;

import lombok.extern.slf4j.Slf4j;
/**
 * Repository class provides data for Counter Api operations.
 */
@Repository
@Slf4j
public class CounterRepositoryImpl  implements CounterRepository{
	private static final String FILE_NAME= "classpath:paragraph.txt";
	private static final Map<String,Integer> WORD_COUNT_MAP = initializeWordCountMap();

	public Map<String,Integer> getWordCountMap(){
		return WORD_COUNT_MAP;
	}

	private static Map<String,Integer> initializeWordCountMap(){

		String textFileContent = loadTextFileAsString(FILE_NAME);
		return Arrays.asList(textFileContent.split("[\\W+]"))
				.parallelStream()
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toMap(s->s,s->1,Integer::sum))
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Entry::getKey,Entry::getValue,(e1,e2) ->e1, LinkedHashMap::new));

	}

	private static String loadTextFileAsString(String filename) {
		try {
			File file = ResourceUtils.getFile(filename);
			return  FileUtils.readFileToString(file, "UTF-8").replaceAll("[,.;]", "").toLowerCase();
		}catch (Exception e) {
			log.error("Error while generating the data objects:",e.getMessage());
			throw new CounterException(e.getMessage());

		}
	}

}

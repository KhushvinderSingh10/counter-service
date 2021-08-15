package com.au.pei.express.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for Project details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountResponse {

	@Builder.Default
	private List<Entry<String, Integer>> counts = new ArrayList<>();

}

package com.au.pei.express.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for Task.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

	@NotNull
	private List<String> searchText;

}

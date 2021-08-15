package com.au.pei.express.controller;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.au.pei.express.mapper.CsvGenerationMapper;
import com.au.pei.express.mapper.RequestMapper;
import com.au.pei.express.model.CountResponse;
import com.au.pei.express.model.ErrorResponse;
import com.au.pei.express.model.SearchRequest;
import com.au.pei.express.service.CounterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
/**
 * This is the controller class that exposes Project end points
 */
@RestController
@Validated
@Slf4j
@RequestMapping("/counter-api")
public class CounterController {

	public static final String APPLICATION_JSON = "application/json";

	public static final String TEXT_CSV = "text/csv";

	@Autowired
	private CsvGenerationMapper csvGenerator;

	@Autowired
	private CounterService  searchService;

	/**
	 * Retrieves the Top Word Occurrence based on the path Param provided.
	 *
	 * @param top {@link Integer}
	 * @return void
	 */
	@Operation(summary = "Provide the top Texts, which has the \n" + 
			"highest counts in the Paragraphs.",security=@SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Successful. returns a CSV file", 
					content = { @Content(mediaType = TEXT_CSV) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = { @Content(mediaType = APPLICATION_JSON, 
			schema = @Schema(implementation = ErrorResponse.class)) }), 
			@ApiResponse(responseCode = "500", description = "Internal Server Error", 
			content = { @Content(mediaType = APPLICATION_JSON, 
			schema = @Schema(implementation = ErrorResponse.class)) }) })
	@GetMapping(path = "/top/{top}", produces = TEXT_CSV)
	public void getTopWords(@PathVariable Integer top,HttpServletResponse response) {

		csvGenerator.generateCSV(RequestMapper.toCSVObject(searchService.getTopWords(top)),response);
	}

	/**
	 * Searches the Words Occurrences and returns the Json.
	 *
	 * @param searchRequest {@link SearchRequest}
	 * @return CountResponse
	 */
	@Operation(summary = "Provide the Json Result with the occurance of the words",security=@SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Successful. Returns the Json Object with Words and occrances", 
					content = { @Content(mediaType = APPLICATION_JSON, 
					schema = @Schema(implementation = CountResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = { @Content(mediaType = APPLICATION_JSON, 
			schema = @Schema(implementation = ErrorResponse.class)) }), 
			@ApiResponse(responseCode = "500", description = "Internal Server Error", 
			content = { @Content(mediaType = APPLICATION_JSON, 
			schema = @Schema(implementation = ErrorResponse.class)) }) })
	@PostMapping(path = "/search",consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
	public CountResponse searchWords(@RequestBody @Valid @NotNull SearchRequest searchRequest) {
		log.debug("Search Request received : {}",searchRequest);
		return RequestMapper.toCountResponse(searchService.searchWords(searchRequest));

	}
}

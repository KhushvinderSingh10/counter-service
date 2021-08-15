package com.au.pei.express.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.au.pei.express.CounterApplication;
import com.au.pei.express.model.CountResponse;
import com.au.pei.express.model.SearchRequest;

@SpringBootTest(classes = CounterApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CounterControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    
    @Test
    public void whenSearch_withValidRequest_thenSuccess() throws Exception {
    	
    	SearchRequest req = new SearchRequest();
    	List<String> list = new ArrayList<>();
    	list.add("Duis");
    	req.setSearchText(list);
    	HttpEntity request = new HttpEntity(req,getValidUserAuthorizationHeader());
        ResponseEntity<CountResponse> response = restTemplate.exchange(
        		"http://localhost:" + port + "/counter-api/search",
                HttpMethod.POST,
                request,
                CountResponse.class
        );
        assertEquals(1, response.getBody().getCounts().size());
        assertEquals(11, response.getBody().getCounts().get(0).getValue().intValue());
        
    }
    
    @Test
    public void whenSearch_withEmptyRequest_thenSuccess() throws Exception {
    	
    	SearchRequest req = new SearchRequest();
    	List<String> list = new ArrayList<>();    	
    	req.setSearchText(list);
    	HttpEntity request = new HttpEntity(req,getValidUserAuthorizationHeader());
        ResponseEntity<CountResponse> response = restTemplate.exchange(
        		"http://localhost:" + port + "/counter-api/search",
                HttpMethod.POST,
                request,
                CountResponse.class
        );
        assertEquals(0, response.getBody().getCounts().size());       
    }
    
    @Test
    public void whenSearch_withNullRequest_thenSuccess() throws Exception {
    	
    	SearchRequest req = new SearchRequest();    	
    	HttpEntity request = new HttpEntity(req,getValidUserAuthorizationHeader());
        ResponseEntity<CountResponse> response = restTemplate.exchange(
        		"http://localhost:" + port + "/counter-api/search",
                HttpMethod.POST,
                request,
                CountResponse.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
    }
    
    @Test
    public void whenSearch_withValidRequest_And_DifferentCasing_thenSuccess() throws Exception {
    	
    	SearchRequest req = new SearchRequest();
    	List<String> list = new ArrayList<>();
    	list.add("dUiS");
    	req.setSearchText(list);
    	HttpEntity request = new HttpEntity(req,getValidUserAuthorizationHeader());
        ResponseEntity<CountResponse> response = restTemplate.exchange(
        		"http://localhost:" + port + "/counter-api/search",
                HttpMethod.POST,
                request,
                CountResponse.class
        );
        assertEquals(1, response.getBody().getCounts().size());
        assertEquals(11, response.getBody().getCounts().get(0).getValue().intValue());
        
    }
    
    @Test
    public void whenSearch_withValidRequest_ButNonExistWord_thenSuccess() throws Exception {
    	
    	SearchRequest req = new SearchRequest();
    	List<String> list = new ArrayList<>();
    	list.add("123");
    	req.setSearchText(list);
    	HttpEntity request = new HttpEntity(req,getValidUserAuthorizationHeader());
        ResponseEntity<CountResponse> response = restTemplate.exchange(
        		"http://localhost:" + port + "/counter-api/search",
                HttpMethod.POST,
                request,
                CountResponse.class
        );
        assertEquals(1, response.getBody().getCounts().size());
        assertEquals(0, response.getBody().getCounts().get(0).getValue().intValue());
        
    }
    
    @Test
    public void whenSearch_withValidRequest_ButUnauthorizedUser_thenError() throws Exception {
    	
    	SearchRequest req = new SearchRequest();
    	List<String> list = new ArrayList<>();
    	list.add("123");
    	req.setSearchText(list);
    	HttpEntity request = new HttpEntity(req,getInvalidUserAuthorizationHeader());
        ResponseEntity<Object> response = restTemplate.exchange(
        		"http://localhost:" + port + "/counter-api/search",
                HttpMethod.POST,
                request,
                Object.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        
    }
    
    
    @Test
    public void whenGetTop_UnAuthorizedUser_thenReturnError() throws Exception {
        HttpEntity request = new HttpEntity(getInvalidUserAuthorizationHeader());
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/counter-api/top/5",
                HttpMethod.GET,
                request,
                Void.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());      
    }
    
   
    
    private HttpHeaders getInvalidUserAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("1000008", "test123");
        return headers;
    }
    
    private HttpHeaders getValidUserAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("optus", "candidates");
        return headers;
    }  
        
    
}
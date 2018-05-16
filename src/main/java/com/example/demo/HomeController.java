package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;  

@RestController  
public class HomeController {  
	
	@Autowired
	protected RestTemplate restTemplate;
	
	@Autowired
    private YAMLConfig  myConfig;
	
	@Autowired
	private MongoRepo mongoRepo;
	
    @RequestMapping( value = "/hello", method = RequestMethod.POST)  
    public <T, K> Object hello(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			@RequestBody Map<String, Object> requestBodyMap, HttpServletRequest request) throws Throwable {
       
    	printConfigEnv();
    	Map<String, Object> requestBody = new HashMap(); 
    	requestBody.put("data", requestBodyMap);
    	ResponseEntity postResponse = post("http://localhost:3210/v1/tenants/SMD/groups/products/402/questionnaires/customPlans", new HttpEntity(requestBody), Map.class);
    	return postResponse.getBody();  
    }
    
    @RequestMapping( value = "/addSports", method = RequestMethod.POST)  
    public ResponseEntity addSports(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			@RequestBody SportsPerson sportsPerson, HttpServletRequest request) throws Throwable {
       
    	System.out.println("sportsperson name:" + sportsPerson);
    	mongoRepo.addSportsData(sportsPerson);
    	ResponseEntity responseEntity = new ResponseEntity("Data saved successfully",HttpStatus.OK);
    	return responseEntity;  
    }
    
    @RequestMapping( value = "/viewSports", method = RequestMethod.GET)  
    public ResponseEntity viewSports(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			HttpServletRequest request) throws Throwable {
       
    	List<SportsPerson> spList = mongoRepo.viewSports();
    	
    	List<SportsPerson> fl1 = GeneralUtil.applyLogic(spList, sp -> sp.getId() < 130);
    	
    	List<SportsPerson> fl2 = GeneralUtil.applyLogic(spList, GeneralUtil::isGreaterThan130);
    	
    	ResponseEntity responseEntity = new ResponseEntity(fl2, HttpStatus.OK);
    	return responseEntity;  
    }
        
    private void printConfigEnv() {
    	System.out.println("using environment: " + myConfig.getEnvironment());
        System.out.println("name: " + myConfig.getName());
        System.out.println("servers: " + myConfig.getServers());
		
	}

	public <T, K> ResponseEntity<T> post(String url, HttpEntity<K> request,
			Class<T> clazz) {
		ResponseEntity postResponse;
		try {
			postResponse = restTemplate.postForEntity(url, request, clazz);
		} finally {
			request = null;
		}

		return postResponse;
	}
}  
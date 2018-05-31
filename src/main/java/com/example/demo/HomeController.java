package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.config.YAMLConfig;
import com.example.entity.Event;
import com.example.entity.SportsPerson;
import com.example.repo.MongoRepo;
import com.example.util.GeneralUtil;
import com.example.util.LogExecutionTime;  

@RestController  
public class HomeController {  
	
	@Autowired 
	protected RestTemplate restTemplate;
	
	@Autowired
    private YAMLConfig  myConfig;
	
	@Autowired
	private MongoRepo mongoRepo;
	
	private static final Logger logger = LogManager.getLogger(HomeController.class);
	
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
    
    /**
     * view sports
     * @param pathParamMap
     * @param reqParamMap
     * @param headerMap
     * @param request
     * @return
     * @throws Throwable
     */
    @LogExecutionTime
    @GetMapping( value = "/viewSports", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewSports(@PathVariable Map<String, Object> pathParamMap,
			@RequestParam Map<String, Object> reqParamMap, @RequestHeader Map<String, Object> headerMap,
			HttpServletRequest request) throws Throwable {
       
    	logger.info("getting sports information ");
    	List<SportsPerson> spList = mongoRepo.viewSports();
    	
    	List<SportsPerson> fl1 = GeneralUtil.applyLogic(spList, sp -> sp.getId() < 130);
    	
    	List<SportsPerson> fl2 = GeneralUtil.applyLogic(spList, GeneralUtil::isGreaterThan130);
    	
    	ResponseEntity responseEntity = new ResponseEntity(fl2, HttpStatus.OK);
    	logger.info("ends sports information api ");
    	return responseEntity;  
    }
    
    /**
     * Akshaya changed this method
     * @param request
     * @return
     * @throws Throwable
     */
    
    @GetMapping(value = "/getEvent")
    public Event getEvent(HttpServletRequest request) throws Throwable {
    	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    	String toParse = "20-12-2014 02:30:00";
    	Date date = df.parse(toParse);
    	Event event = new Event("party", date);
    	return event;
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
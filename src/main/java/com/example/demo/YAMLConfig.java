package com.example.demo;
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
 
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig  {
 
	private String name;
    private String environment;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public List<String> getServers() {
		return servers;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}

	private List<String> servers = new ArrayList<>();
    
    @Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
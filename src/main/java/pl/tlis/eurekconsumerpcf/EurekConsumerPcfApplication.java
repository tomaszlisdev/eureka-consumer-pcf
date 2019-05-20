package pl.tlis.eurekconsumerpcf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class EurekConsumerPcfApplication {
	private static final Logger logger = LoggerFactory.getLogger(EurekConsumerPcfApplication.class);

	@Bean
	@Profile(value = "cloud")
	public Cloud cloud() {
		Cloud cloud =  new CloudFactory().getCloud();
		logger.info(cloud.getCloudProperties().toString());
		return cloud;
	}


	@LoadBalanced
	@Bean
	public RestTemplate produceRestTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekConsumerPcfApplication.class, args);
	}

}

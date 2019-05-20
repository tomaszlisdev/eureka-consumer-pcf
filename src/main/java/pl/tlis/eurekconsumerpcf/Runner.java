package pl.tlis.eurekconsumerpcf;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Service
public class Runner {
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Autowired
    private EurekaClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;


    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void run() {
        URI requestUri = UriComponentsBuilder.fromUriString("//PRODUCER-APP-PCF/test").build().toUri();
        logger.info("Discovery client " + discoveryClient.getApplications().getRegisteredApplications().toString());
        logger.info("Discovery client " + discoveryClient.getApplications().getRegisteredApplications().stream().map(Application::getName).collect(Collectors.joining(", ")));
        logger.info("Discovery client " + discoveryClient.getApplications().getRegisteredApplications().stream().flatMap(application -> application.getInstances().stream().map(instanceInfo -> instanceInfo.getHostName()+":"+instanceInfo.getPort())).collect(Collectors.joining(", ")));
        String result = restTemplate.getForObject(requestUri, String.class);
        logger.info("Calling service "+ requestUri + " returns " + result);
    }
}

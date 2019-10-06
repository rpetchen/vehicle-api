package com.udacity.vehicles;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;

/**
 * Launches a Spring Boot application for the Vehicles API,
 * initializes the car manufacturers in the database,
 * and launches web clients to communicate with maps and pricing.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
public class VehiclesApiApplication {


    @Autowired
    private EurekaClient eurekaClient;
    
    public static void main(String[] args) {
        SpringApplication.run(VehiclesApiApplication.class, args);
    }

    /**
     * Initializes the car manufacturers available to the Vehicle API.
     * @param repository where the manufacturer information persists.
     * @return the car manufacturers to add to the related repository
     */
    @Bean
    CommandLineRunner initDatabase(ManufacturerRepository repository) {
    	
        return args -> {
            repository.save(new Manufacturer(100, "Audi"));
            repository.save(new Manufacturer(101, "Chevrolet"));
            repository.save(new Manufacturer(102, "Ford"));
            repository.save(new Manufacturer(103, "BMW"));
            repository.save(new Manufacturer(104, "Dodge"));
            
           
        };
        
    }
    
    @Bean
    CommandLineRunner initDatabaseCars(CarRepository repository) {
    	Manufacturer manufacturer =  new Manufacturer(100, "Audi");
    	Details details = new Details();
    	details.setBody("Body");
    	details.setManufacturer(manufacturer);
    	details.setModel("Thing");
    	Car car = new Car();
    	car.setDetails(details);
    	car.setCondition(Condition.NEW);
    	
    	Manufacturer manufacturer1 =  new Manufacturer(104, "Dodge");
    	Details details1 = new Details();
    	details1.setBody("Body1");
    	details1.setManufacturer(manufacturer1);
    	details1.setModel("Thing1");
    	Car car1 = new Car();
    	car1.setDetails(details1);
    	car1.setCondition(Condition.USED);
        return args -> {
            repository.save(car);
            repository.save(car1);
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Web Client for the maps (location) API
     * @param endpoint where to communicate for the maps API
     * @return created maps endpoint
     */
    @Bean(name="maps")
    public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    /**
     * Web Client for the pricing API
     * @param endpoint where to communicate for the pricing API
     * @return created pricing endpoint
     */
    @Bean(name="pricing")
    public WebClient webClientPricing() {
 
    	InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("pricing-service", false);
        String serviceBaseUrl = instanceInfo.getHomePageUrl();
        System.out.println("Service Base URL" + serviceBaseUrl);
        return WebClient.create(serviceBaseUrl);
    }

}

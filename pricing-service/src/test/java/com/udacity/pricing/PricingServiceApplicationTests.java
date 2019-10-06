package com.udacity.pricing;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.udacity.pricing.domain.price.Price;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getPrice() {

		ResponseEntity<Price> response = this.restTemplate.getForEntity("http://localhost:" + port + "/prices/1",
				Price.class);

		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

	}

	@Test
	public void getAllPrices() {
		System.out.println(port);
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/prices/",
				String.class);

		Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

	}

}

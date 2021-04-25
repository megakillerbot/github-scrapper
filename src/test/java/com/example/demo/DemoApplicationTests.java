package com.example.demo;

import com.example.demo.dto.FilesByExtensionDTO;
import com.example.demo.dto.RequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder
public class DemoApplicationTests {

	@Value("${GIT_REPO}")
	private String GIT_REPO;

	private static final String EXTENSION = "gitignore";

	@Test
	public void contextLoads() {
		Assert.assertNotNull(GIT_REPO);
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void homeResponse() {
		String body = this.restTemplate.getForObject("/", String.class);
		Assert.assertNotNull(body);
	}

	@Test
	public void selfResponseA() throws IOException {
		RequestDTO request = new RequestDTO(GIT_REPO, true);
		ResponseEntity<String> response = this.restTemplate.postForEntity("/", request, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		List<FilesByExtensionDTO> files = mapper.readValue(response.getBody(), new TypeReference<List<FilesByExtensionDTO>>(){});
		Assert.assertTrue(files.stream().anyMatch(f -> EXTENSION.equals(f.getExtension())));
	}

	@Test(timeout = 3000L)
	@After
	public void selfResponseB() throws IOException {
		RequestDTO request = new RequestDTO(GIT_REPO, false);
		ResponseEntity<String> response = this.restTemplate.postForEntity("/", request, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		List<FilesByExtensionDTO> files = mapper.readValue(response.getBody(), new TypeReference<List<FilesByExtensionDTO>>(){});
		Assert.assertTrue(files.stream().anyMatch(f -> EXTENSION.equals(f.getExtension())));
	}

	@Test
	public void errorResponse(){
		RequestDTO request = new RequestDTO(RandomString.make(10), true);
		ResponseEntity<String> response = this.restTemplate.postForEntity("/", request, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

}

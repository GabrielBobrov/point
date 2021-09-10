package br.com.gabrielbobrov.point.controller;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.gabrielbobrov.point.PointApplication;
import br.com.gabrielbobrov.point.service.PointService;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PointApplication.class)
public class PointControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PointService service;
	
	@Test
	public void autenticationBadRequestTest() throws Exception {
		URI uri = new URI("/batidas");
		String json = "{\"point\":\"2022-12-3022:47:01\",\"userId\":\"2\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
	}
	
	@Test
	public void autenticationTest() throws Exception {
		URI uri = new URI("/batidas");
		String json = "{\"point\":\"2022-12-30T22:47:01\",\"userId\":\"2\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(201));
	}

}

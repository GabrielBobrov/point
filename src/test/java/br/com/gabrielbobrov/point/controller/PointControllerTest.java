package br.com.gabrielbobrov.point.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gabrielbobrov.point.PointApplication;
import br.com.gabrielbobrov.point.dto.MessageDto;
import br.com.gabrielbobrov.point.dto.PointDto;
import br.com.gabrielbobrov.point.repository.PointRepository;
import br.com.gabrielbobrov.point.service.PointService;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PointApplication.class)
public class PointControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@InjectMocks
	private PointService service = new PointService();
	
	@Mock
	private PointRepository repository;
	
	@Test
	public void pointBadRequestWrongFormatTest() throws Exception {
		service = mock(PointService.class);
		String json = "{\"dataHora\":\"2022--11-30T02:57:01\"}";
		PointDto entity = new PointDto();
		entity.setDataHora("2022--11-30T02:57:01");
		URI uri = new URI("/batidas");
		when(service.savePoint(entity, UriComponentsBuilder.newInstance())).thenReturn(ResponseEntity.status(400).body(new MessageDto("Data possui formato inválido")));
		mvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andReturn().getResponse().getContentAsString();
		
	}
	
	@Test
	public void pointBadRequestEmptyPointTest() throws Exception {
		service = mock(PointService.class);
		String json = "{}";
		PointDto entity = new PointDto();
		entity.setDataHora("");
		URI uri = new URI("/batidas");
		when(service.savePoint(entity, UriComponentsBuilder.newInstance())).thenReturn(ResponseEntity.status(400).body(new MessageDto("Data possui formato inválido")));
		mvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andReturn().getResponse().getContentAsString();
		
	}
	@Test
	public void pointCreatedTest() throws Exception {
		String json = "{\"dataHora\":\"2022-11-30T02:57:01\"}";
		PointDto entity = new PointDto();
		entity.setDataHora("2022-11-30T02:57:01");
		URI uri = new URI("/batidas");
		when(service.savePoint(entity, UriComponentsBuilder.newInstance())).thenReturn(ResponseEntity.created(uri).build());
		mvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void pointDuplicateDateTest() throws Exception {
		String json = "{\"dataHora\":\"2021-10-01T15:10:10\"}";
		PointDto entity = new PointDto();
		entity.setDataHora("2022-11-30T02:57:01");
		URI uri = new URI("/batidas");
		when(service.savePoint(entity, UriComponentsBuilder.newInstance())).thenReturn(ResponseEntity.status(409).body(new MessageDto("Horário já registrado")));
		mvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void pointTimeLunchTest() throws Exception {
		String json = "{\"dataHora\":\"2021-10-01T16:30:10\"}";//20 min de almoço de acordo com a data do banco de dados
		PointDto entity = new PointDto();
		entity.setDataHora("2021-10-01T16:30:10");
		URI uri = new URI("/batidas");
		when(service.savePoint(entity, UriComponentsBuilder.newInstance())).thenReturn(ResponseEntity.status(403).body(new MessageDto("Deve haver no mínimo 1 hora de almoço")));
		mvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isForbidden())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void pointWeekendTest() throws Exception {
		service = mock(PointService.class);
		String json = "{\"dataHora\":\"2021-10-02T16:30:10\"}";
		PointDto entity = new PointDto();
		entity.setDataHora("2021-10-02T16:30:10");
		URI uri = new URI("/batidas");
		when(service.savePoint(entity, UriComponentsBuilder.newInstance())).thenReturn(ResponseEntity.status(403).body(new MessageDto("Deve haver no mínimo 1 hora de almoço")));
		mvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isForbidden())
		.andReturn().getResponse().getContentAsString();
	}
	
	

}

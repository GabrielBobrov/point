package br.com.gabrielbobrov.point.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gabrielbobrov.point.model.PointEntity;
import br.com.gabrielbobrov.point.service.PointService;
import br.comgabrielbobrov.point.dto.SuccessMessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Bater ponto")
@RequestMapping(value = "/ponto",produces = MediaType.APPLICATION_JSON_VALUE)
public class PointController {
	

	@Autowired
	private PointService service;
	
	
	@PostMapping
	@Transactional
	@ApiOperation(value = "Bata seu ponto", notes = "Serviço para bater ponto")
	public ResponseEntity<SuccessMessageDto> beatTime( @ApiParam(value = "Hora da batida", required = true) 
	@Valid @RequestBody PointEntity dto, UriComponentsBuilder uriBuilder) {
		return service.savePoint(dto, uriBuilder);
		
	}
	

}

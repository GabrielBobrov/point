package br.com.gabrielbobrov.point.service;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gabrielbobrov.point.dto.MessageDto;
import br.com.gabrielbobrov.point.dto.PointDto;
import br.com.gabrielbobrov.point.model.PointEntity;
import br.com.gabrielbobrov.point.model.PointEntity.Status;
import br.com.gabrielbobrov.point.repository.PointRepository;
import br.com.gabrielbobrov.point.utils.DateTimeUtils;

@org.springframework.stereotype.Service
public class PointService {

	@Autowired
	private PointRepository pointRepository;

	public ResponseEntity<MessageDto> savePoint(PointDto dto, UriComponentsBuilder uriBuilder) {

		PointEntity point = new PointEntity();
		DateTimeUtils dateUtils = new DateTimeUtils();
		LocalDateTime date = dateUtils.convertStringIntoLocalDateTime(dto.getDataHora());
		URI uri = uriBuilder.path("/batidas/{id}").buildAndExpand(dto.getDataHora()).toUri();

		if (!dateUtils.isValidDate(dto.getDataHora())) {
			return ResponseEntity.status(400).body(new MessageDto("Data possui formato inválido"));
		}
		
		//verifica se o dia recebido é no final de semana
		if (dateUtils.isWeekDay(date)) {
			List<PointEntity> entityList = pointRepository.findByDateInterval(
					date.toLocalDate().atStartOfDay().toString(), date.toLocalDate().atTime(23, 59, 59).toString());

			// verifica se não existe ponto batido naquele dia
			if (entityList.isEmpty()) {
				point.setDataHora(dto.getDataHora());
				point.setStatus(Status.BeginWork);
				pointRepository.save(point);
				return ResponseEntity.created(uri).build();

			} else {
				
				// verificando se existe horario registrado
				for (PointEntity pointEntity : entityList) {
					if (dto.getDataHora().equals(pointEntity.getDataHora())) {
						return ResponseEntity.status(409).body(new MessageDto("Horário já registrado"));
					}
				}
				
				if (date.isBefore(LocalDateTime.parse(entityList.get(0).getDataHora()))) {
					return ResponseEntity.status(409).body(new MessageDto("Data informata é anterior ao ultimo ponto registrado"));
				}
				
				// checando qual status foi o ultimo ponto batido
				if (entityList.get(0).getStatus() == Status.BeginWork) {
					point.setDataHora(dto.getDataHora());
					point.setStatus(Status.BeginLunch);
					pointRepository.save(point);
					return ResponseEntity.created(uri).build();
				}

				if (entityList.get(0).getStatus() == Status.BeginLunch) {
					long diferenceInHours = Duration.between(LocalDateTime.parse(entityList.get(0).getDataHora()), date).toHours();
					
					if (diferenceInHours >= 1l) {
						point.setDataHora(dto.getDataHora());
						point.setStatus(Status.EndLunch);
						pointRepository.save(point);
						return ResponseEntity.created(uri).build();
					} else {
						return ResponseEntity.status(403).body(new MessageDto("Deve haver no mínimo 1 hora de almoço"));
					}
				}
				
				if (entityList.get(0).getStatus() == Status.EndLunch) {
					point.setDataHora(dto.getDataHora());
					point.setStatus(Status.EndWork);
					pointRepository.save(point);
					return ResponseEntity.created(uri).build();
				}

				if (entityList.get(0).getStatus() == Status.EndWork) {
					return ResponseEntity.status(403).body(new MessageDto("Apenas 4 horários podem ser registrados por dia"));
				}
			}
		}
		return ResponseEntity.status(403).body(new MessageDto("Sábado e domingo não são permitidos como dia de trabalho"));

	}

}

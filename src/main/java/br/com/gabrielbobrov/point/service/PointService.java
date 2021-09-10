package br.com.gabrielbobrov.point.service;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gabrielbobrov.point.dto.SuccessMessageDto;
import br.com.gabrielbobrov.point.model.PointEntity;
import br.com.gabrielbobrov.point.model.PointEntity.Status;
import br.com.gabrielbobrov.point.repository.PointRepository;

@org.springframework.stereotype.Service
public class PointService {

	@Autowired
	private PointRepository pointRepository;

	public ResponseEntity<SuccessMessageDto> savePoint(PointEntity dto, UriComponentsBuilder uriBuilder) {

		PointEntity point = new PointEntity();
		LocalDateTime initialDate = dto.getPoint();
		List<PointEntity> entityList = pointRepository.findByDateInterval(dto.getUserId(),initialDate.toLocalDate().atStartOfDay(), initialDate.toLocalDate().atTime(23, 59, 59));
		URI uri = uriBuilder.path("/batidas/{id}").buildAndExpand(dto.getPoint()).toUri();
		if (isWeekDay(dto.getPoint())) {

			if (entityList.isEmpty()) {
				point.setPoint(dto.getPoint());
				point.setStatus(Status.BeginWork);
				point.setUserId(dto.getUserId());
				pointRepository.save(point);
				return ResponseEntity.created(uri).build();

			} else {
				for (PointEntity pointEntity : entityList) {
					if (dto.getPoint().equals(pointEntity.getPoint())) {
						return ResponseEntity.status(409).body(new SuccessMessageDto("Horário já registrado"));
					}
				}
				if (dto.getPoint().isBefore(entityList.get(0).getPoint())) {
					return ResponseEntity.status(409).body(new SuccessMessageDto("Data informata é antes do ultimo ponto registrado"));
				}
				// check witch status the last point is.
				if (entityList.get(0).getStatus() == Status.BeginWork) {
					point.setPoint(dto.getPoint());
					point.setStatus(Status.BeginLunch);
					point.setUserId(dto.getUserId());
					pointRepository.save(point);
					return ResponseEntity.created(uri).build();
				}

				if (entityList.get(0).getStatus() == Status.BeginLunch) {
					long diferenceInHours = Duration.between(entityList.get(0).getPoint(), initialDate).toHours();
					if (diferenceInHours >= 1l) {
						point.setPoint(dto.getPoint());
						point.setStatus(Status.EndLunch);
						point.setUserId(dto.getUserId());
						pointRepository.save(point);
						return ResponseEntity.created(uri).build();
					} else {
						return ResponseEntity.status(403).body(new SuccessMessageDto("Deve haver no mínimo 1 hora de almoço"));
					}
				}
				if (entityList.get(0).getStatus() == Status.EndLunch) {
					point.setPoint(dto.getPoint());
					point.setStatus(Status.EndWork);
					point.setUserId(dto.getUserId());
					pointRepository.save(point);
					return ResponseEntity.created(uri).build();
				}

				if (entityList.get(0).getStatus() == Status.EndWork) {
					return ResponseEntity.status(403).body(new SuccessMessageDto("Apenas 4 horários podem ser registrados por dia"));
				}
			}
		} else {
			return ResponseEntity.status(403)
					.body(new SuccessMessageDto("Sábado e domingo não são permitidos como dia de trabalho"));

		}
		return ResponseEntity.created(uri).build();

	}

	public boolean isWeekDay(LocalDateTime dt) {

		switch (dt.getDayOfWeek()) {

		case SATURDAY:
			return false;

		case SUNDAY:
			return false;

		default:
			return true;
		}

	}

}

package br.com.gabrielbobrov.point.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gabrielbobrov.point.model.PointEntity.Status;

public class PointDto  {
	
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime point;
	
	private Status status;

	public PointDto(PointDto dto) {
		this.point = dto.getPoint();
		this.status = dto.getStatus();
	}

	public LocalDateTime getPoint() {
		return point;
	}

	public void setPoint(LocalDateTime point) {
		this.point = point;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	

}

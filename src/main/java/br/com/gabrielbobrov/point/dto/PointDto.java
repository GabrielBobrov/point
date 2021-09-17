package br.com.gabrielbobrov.point.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

public class PointDto {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotEmpty(message = "Campo obrigatório não informado")
	private String dataHora;

	public PointDto(String dataHora) {
		super();
		this.dataHora = dataHora;
	}

	public PointDto() {
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

}

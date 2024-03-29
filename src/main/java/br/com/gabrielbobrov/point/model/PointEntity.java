package br.com.gabrielbobrov.point.model;

import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PONTO")
public class PointEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotEmpty(message = "Campo obrigatório não informado")
	private String dataHora;

	private Status status;

	public enum Status {
		BeginWork, BeginLunch, EndLunch, EndWork
	}

	public String getDataHora() {
		return dataHora;
	}

	public boolean validateDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		try {
			format.parse(date);
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	public PointEntity(PointEntity pointEntity) {
		super();
		this.id = pointEntity.getId();
		this.dataHora = pointEntity.getDataHora();
		this.status = pointEntity.getStatus();
	}

	public PointEntity() {
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointEntity other = (PointEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

}

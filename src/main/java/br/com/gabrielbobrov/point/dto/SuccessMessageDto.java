package br.com.gabrielbobrov.point.dto;

public class SuccessMessageDto {
	
	private String message;
	
	
	
	public SuccessMessageDto(String message) {
		super();
		this.message = message;
	}
	public SuccessMessageDto() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

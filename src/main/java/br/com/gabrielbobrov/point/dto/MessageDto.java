package br.com.gabrielbobrov.point.dto;

public class MessageDto {
	
	private String message;
	
	
	
	public MessageDto(String message) {
		super();
		this.message = message;
	}
	public MessageDto() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

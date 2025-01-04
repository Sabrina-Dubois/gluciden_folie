package co.simplon.glucidenfoliebusiness.dtos;

public record LoginResponse(String token, String message) {

	public LoginResponse(String token, String message) {
		this.token = token;
		this.message = message;
	}

	public String token() {
		return token;
	}

	public String message() {
		return message;
	}

}

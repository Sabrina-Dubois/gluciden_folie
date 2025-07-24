package co.simplon.glucidenfoliebusiness.dtos.account;

public record AccountLogin(String username, String password) {
	@Override
	public String toString() {
		return String.format("{username=%s, password=[PROTECTED]}", username);

	}

}

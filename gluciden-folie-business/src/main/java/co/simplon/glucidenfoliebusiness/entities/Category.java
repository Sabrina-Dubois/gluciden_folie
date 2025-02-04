package co.simplon.glucidenfoliebusiness.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_categories")
public class Category extends AbstractEntity {

	@Column(name = "category_name")
	private String name;

	@ManyToOne // Chaque catégorie appartient à un utilisateur
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	private Account account;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return String.format("Category{name='%s'}", name);
	}

}

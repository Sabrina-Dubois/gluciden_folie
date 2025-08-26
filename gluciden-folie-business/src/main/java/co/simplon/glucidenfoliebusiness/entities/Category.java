package co.simplon.glucidenfoliebusiness.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@ManyToOne
	@JoinColumn(name = "id_account", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Account account;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Category category))
			return false;
		return Objects.equals(name, category.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return String.format("Category{name='%s'}", name);
	}

	// *** Getters & setters ***
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

}

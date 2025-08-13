package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_unities")
public class Unity extends AbstractEntity {

	@Column(name = "unity_name")
	private String name;

	@OneToMany(mappedBy = "unity")
	private Set<RecipeIngredientUnity> recipeIngredients = new HashSet<>();

	public Unity() {
		// Constructeur par défaut requis par JPA
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Unity unity = (Unity) obj;
		return Objects.equals(getId(), unity.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return String.format("Unity{id=%d,name='%s'}", getId(), name);
	}

	// *** Getters & setters ***
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_ingredients")
public class Ingredient extends AbstractEntity {

	@Column(name = "ingredient_name", nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "ingredient") // L'ID composite est référencé ici.
	private Set<RecipeIngredientUnity> recipeIngredients = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Ingredient ingredient = (Ingredient) obj;
		// Si les deux objets ont un ID non nul, on compare les IDs
		if (getId() != null && ingredient.getId() != null)
			return Objects.equals(getId(), ingredient.getId());
		return false;
	}

	public Ingredient() {

	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return String.format("Ingredient{id=%d,name='%s'}", getId(), name);
	}

	// *** Getters & setters ***

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

}

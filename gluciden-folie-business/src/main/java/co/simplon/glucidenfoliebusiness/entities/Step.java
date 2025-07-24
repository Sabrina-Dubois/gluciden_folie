package co.simplon.glucidenfoliebusiness.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_steps")
public class Step extends AbstractEntity {

	@Column(name = "step_number")
	private int number;

	@Column(name = "step_description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "id_recipe", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Recipe recipe;

	public Step() {
	}

	public Step(Integer stepNumber, String description, Recipe recipe) {
		this.number = stepNumber;
		this.description = description;
		this.recipe = recipe;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Step step))
			return false;
		return Objects.equals(getId(), step.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return String.format("Step{number=%d, description='%s'}", number, description);
	}

	// *** Getters & setters ***

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
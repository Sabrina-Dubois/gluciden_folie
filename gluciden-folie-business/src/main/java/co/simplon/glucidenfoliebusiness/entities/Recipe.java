package co.simplon.glucidenfoliebusiness.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import co.simplon.glucidenfoliebusiness.enums.Difficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_recipes")
public class Recipe extends AbstractEntity {

	@Column(name = "recipe_name")
	private String name;

	@Column(name = "recipe_picture")
	private String picture;

	@Column(name = "difficulty")
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

	@OneToMany(mappedBy = "recipe")
	private List<Step> steps = new ArrayList<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "t_recipes_categories", joinColumns = @JoinColumn(name = "id_recipe"), inverseJoinColumns = @JoinColumn(name = "id_category"))
	private Set<Category> categories = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "id_account", referencedColumnName = "id", nullable = false)
	@JsonBackReference
	private Account account;

	public Recipe() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Recipe recipe))
			return false;
		return Objects.equals(getId(), recipe.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return String.format("Recipe{id=%d,name='%s', picture='%s',difficulty='%s', categories=%s, account='%s'}",
				getId(), name, picture, difficulty, categories, account);
	}

	// *** Getters & setters ***

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

}

package co.simplon.glucidenfoliebusiness.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

//visibilté publi visible de n'importe ou donc pas de public
//abstract = absence de mot clé public 
@MappedSuperclass // cette classe contient des éléments de mapping
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	protected AbstractEntity() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AbstractEntity that = (AbstractEntity) o;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{id=" + id + '}';
	}

	// *** Getters & setters ***

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

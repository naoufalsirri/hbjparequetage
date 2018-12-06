package fr.naf.hbjpa.hbjparequetage.fetchlazy;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Guide")
public class GuideLazy {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
    private String nom;
	
	private long salaire;
	
	@OneToMany(mappedBy="guide",fetch=FetchType.LAZY)
	private Set<EleveLazy> eleves=new HashSet<EleveLazy>();

	
	public GuideLazy(String nom, long salaire) {
		super();
		this.nom = nom;
		this.salaire = salaire;
	}

	public GuideLazy() {
		super();		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public long getSalaire() {
		return salaire;
	}

	public void setSalaire(long salaire) {
		this.salaire = salaire;
	}

	public Set<EleveLazy> getEleves() {
		return eleves;
	}

	public void setEleves(Set<EleveLazy> eleves) {
		this.eleves = eleves;
	}

	
}

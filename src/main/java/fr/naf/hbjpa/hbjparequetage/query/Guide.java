package fr.naf.hbjpa.hbjparequetage.query;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Guide")
@NamedQuery(name = "findAllGuides", query = "select  g from Guide g")
public class Guide {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
    private String nom;
	
	private long salaire;
	
	@OneToMany(mappedBy="guide")
	private Set<Eleve> eleves=new HashSet<Eleve>();

	
	public Guide(String nom, long salaire) {
		super();
		this.nom = nom;
		this.salaire = salaire;
	}

	public Guide() {
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

	public Set<Eleve> getEleves() {
		return eleves;
	}

	public void setEleves(Set<Eleve> eleves) {
		this.eleves = eleves;
	}


}

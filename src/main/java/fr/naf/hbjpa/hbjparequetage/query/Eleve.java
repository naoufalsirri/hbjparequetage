package fr.naf.hbjpa.hbjparequetage.query;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Eleve")
public class Eleve {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String nom;
	
	
	@ManyToOne(cascade= CascadeType.PERSIST)
	@JoinColumn(name="guide_id")
	private Guide guide;

	
	public Eleve() {
		super();		
	}


	public Eleve(String nom, Guide guide) {
		super();
		this.nom = nom;
		this.guide = guide;
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


	public Guide getGuide() {
		return guide;
	}


	public void setGuide(Guide guide) {
		this.guide = guide;
	}


	
	
	
}

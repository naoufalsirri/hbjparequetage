package fr.naf.hbjpa.hbjparequetage.fetchlazy;

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
public class EleveLazy {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String nom;
	
	
	@ManyToOne(cascade= CascadeType.PERSIST,fetch=FetchType.LAZY)
	@JoinColumn(name="guide_id")
	private GuideLazy guide;

	
	public EleveLazy() {
		super();		
	}


	public EleveLazy(String nom, GuideLazy guide) {
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


	public GuideLazy getGuide() {
		return guide;
	}


	public void setGuide(GuideLazy guide) {
		this.guide = guide;
	}



	
	
}

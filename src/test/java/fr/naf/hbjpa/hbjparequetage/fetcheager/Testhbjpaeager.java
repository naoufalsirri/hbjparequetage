package fr.naf.hbjpa.hbjparequetage.fetcheager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.naf.hbjpa.hbjparequetage.fetcheager.EleveEager;
import fr.naf.hbjpa.hbjparequetage.fetcheager.GuideEager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Testhbjpaeager {

	private static EntityManager em;
	private static EntityTransaction ts;
	private static EntityManagerFactory emf;
	private static Long idEleve;
	private static Long idGuide;
	
	@BeforeClass
	public static void initialisation() {
		 emf = Persistence.createEntityManagerFactory("persistence");    	          
		 em = emf.createEntityManager();
		 ts= em.getTransaction();
	}
	
	@Test
	public void test1Enregistrer() {
		 GuideEager guide =new GuideEager("Marcos",1200);   
		 EleveEager eleve = new EleveEager("eleve1",guide);
         ts.begin();
         em.persist(eleve);                 
         ts.commit();           
         idEleve = eleve.getId();
         idGuide = guide.getId();
         assertNotNull(eleve.getId());
         assertNotNull(guide.getId());
	}
	
	@Test
	public void test2RechercherEleveParId() {	
		//requete sql:
		//chargement eleve avec le guide (jointure)
		/*select eleveeager0_.id as id1_0_0_, 
		 * eleveeager0_.guide_id as guide_id3_0_0_, 
		 * eleveeager0_.nom as nom2_0_0_, 
		 * guideeager1_.id as id1_1_1_, 
		 * guideeager1_.nom as nom2_1_1_, 
		 * guideeager1_.salaire as salaire3_1_1_ from Eleve eleveeager0_ 
		 * left outer join Guide guideeager1_ on 
		 * eleveeager0_.guide_id=guideeager1_.id where eleveeager0_.id=?
		 */
		
		//chargement des eleves du guide
		/*
		 * select etudiants0_.guide_id as guide_id3_0_0_, 
		 * etudiants0_.id as id1_0_0_, etudiants0_.id as id1_0_1_, 
		 * etudiants0_.guide_id as guide_id3_0_1_, 
		 * etudiants0_.nom as nom2_0_1_ from Eleve etudiants0_ where etudiants0_.guide_id=?
		 */
		EleveEager eleve  =em.find(EleveEager.class, idEleve);
		GuideEager guide = eleve.getGuide();
		assertEquals(eleve.getNom(), "eleve1");
		assertEquals(guide.getNom(), "Marcos");		
	}
	
	@Test
	public void test3RechercherGuideParId() {	
		em = emf.createEntityManager();
		//requete sql:
		//chargement guide  avec les eleves (jointure)
		/*select guideeager0_.id as id1_1_0_, 
		 * guideeager0_.nom as nom2_1_0_, 
		 * guideeager0_.salaire as salaire3_1_0_, 
		 * eleves1_.guide_id as guide_id3_0_1_, 
		 * eleves1_.id as id1_0_1_, 
		 * eleves1_.id as id1_0_2_, 
		 * eleves1_.guide_id as guide_id3_0_2_,
		 * eleves1_.nom as nom2_0_2_ from Guide guideeager0_ left outer join 
		 * Eleve eleves1_ on guideeager0_.id=eleves1_.guide_id where guideeager0_.id=?
		 */
	
		GuideEager guide  =em.find(GuideEager.class, idGuide);
		Set<EleveEager> eleves = guide.getEleves();
		System.out.println(eleves.size());
		assertEquals(guide.getNom(), "Marcos");		
	}
	
}

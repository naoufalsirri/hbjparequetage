package fr.naf.hbjpa.hbjparequetage.fetchlazy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.naf.hbjpa.hbjparequetage.fetchlazy.EleveLazy;
import fr.naf.hbjpa.hbjparequetage.fetchlazy.GuideLazy;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Testhbjpalazy {

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
		 GuideLazy guide =new GuideLazy("Marcos",1200);   
		 EleveLazy eleve = new EleveLazy("eleve1",guide);
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
		//chargement seulement de l'eleve
		/* select elevelazy0_.id as id1_0_0_,
		 * elevelazy0_.guide_id as guide_id3_0_0_, 
		 * elevelazy0_.nom as nom2_0_0_ from Eleve elevelazy0_ where elevelazy0_.id=?
		 */				
		EleveLazy eleve  =em.find(EleveLazy.class, idEleve);
		GuideLazy guide = eleve.getGuide();
		assertEquals(eleve.getNom(), "eleve1");
		
		//chargement seulement du guide
				/*
				 select guidelazy0_.id as id1_1_0_, 
				 guidelazy0_.nom as nom2_1_0_, 
				 guidelazy0_.salaire as salaire3_1_0_ from Guide guidelazy0_ where guidelazy0_.id=?
        */
		assertEquals(guide.getNom(), "Marcos");					
	}
	
	@Test
	public void test3RechercherGuideParId() {	
		 em = emf.createEntityManager();
		 
		//requete sql:
		//chargement seulement du guide
		/*  select guidelazy0_.id as id1_1_0_,
		 *  guidelazy0_.nom as nom2_1_0_, 
		 *  guidelazy0_.salaire as salaire3_1_0_ from Guide guidelazy0_ where guidelazy0_.id=?
		 */		    	         
		GuideLazy guide  =em.find(GuideLazy.class, idGuide);
		Set<EleveLazy> eleves = guide.getEleves();
		
		//chargement seulement des eleves du guide
		/*
		 * select eleves0_.guide_id as guide_id3_0_0_, 
		 * eleves0_.id as id1_0_0_, 
		 * eleves0_.id as id1_0_1_, 
		 * eleves0_.guide_id as guide_id3_0_1_, 
		 * eleves0_.nom as nom2_0_1_ from Eleve eleves0_ where eleves0_.guide_id=?
		 */
		System.out.println(eleves.size());		
		assertEquals(guide.getNom(), "Marcos");				
	}
	
}

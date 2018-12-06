package fr.naf.hbjpa.hbjparequetage.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.naf.hbjpa.hbjparequetage.fetchlazy.EleveLazy;
import fr.naf.hbjpa.hbjparequetage.fetchlazy.GuideLazy;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Testhbjpaquery {

	private static EntityManager em;
	private static EntityTransaction ts;
	
	@BeforeClass
	public static void initialisation() {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");    	          
		 em = emf.createEntityManager();
		 ts= em.getTransaction();
	}
	
	@Test
	public void testAEnregistrerEleve() {
		 Guide guide =new Guide("Queryguide",1200);   
		 Eleve eleve = new Eleve("Queryeleve",guide);
         ts.begin();
         em.persist(eleve);                 
         ts.commit();                         
         assertNotNull(eleve.getId());
         assertNotNull(guide.getId());
	}
	
	@Test
	public void testBAfficherTousLesElevesQueryJpql() {	
		//requete sql:
		//chargement de tous les eleves
		/* select * from Eleve
		 */
		Query queryJpql = em.createQuery("select eleve from Eleve eleve");
		List<Eleve> eleves =queryJpql.getResultList();
		System.out.println(eleves.size());
		assertTrue(eleves.size()>0);								
	}
	
	@Test
	public void testCAfficherLesEleveAvecFiltrageQueryJpql() {	
		//requete sql:
		//chargement des eleves qui ont le nom 'Queryeleve'
		/* select * from Eleve where nom='Queryeleve'
		 */
		Query queryJpql = em.createQuery("select eleve from Eleve eleve where eleve.nom='Queryeleve'");
		List<Eleve> eleves =queryJpql.getResultList();
		System.out.println(eleves.size());
		assertTrue(eleves.size()>0);	
		
		//ou avec le parametre
		Query queryJpqlAvecparametre = em.createQuery("select eleve from Eleve eleve where eleve.nom=:nom");
		queryJpqlAvecparametre.setParameter("nom", "Queryeleve");
		eleves =queryJpqlAvecparametre.getResultList();
		System.out.println(eleves.size());
		assertTrue(eleves.size()>0);
		
	}
	
	
	@Test
	public void testDAfficherDesInfosTousLesGuidesQueryJpql() {	
		//requete sql:
		//chargement de tous les guides
		/* select nom,salaire from Guide 
		 */
		Query queryJpql = em.createQuery("select guide.nom,guide.salaire from Guide guide");
		List<Object[]> guides =queryJpql.getResultList();
		System.out.println(guides.size());
		for(Object[] guide:guides) {
			System.out.println("nom :" +guide[0]);
		    System.out.println("prenom :" +guide[1]);
		}
		assertTrue(guides.size()>0);								
	}
	
	
	@Test
	public void testEAfficherTousLesGuidesQueryNativeSql() {	
		//requete sql:
		//chargement de tous les guides
		/* select * from guide
		 */
		Query queryJpql = em.createNativeQuery("select * from guide",Guide.class);
		List<Guide> guides =queryJpql.getResultList();
		System.out.println(guides.size());
		assertTrue(guides.size()>0);								
	}
	
	@Test
	public void testFAfficherTousLesGuidesNamedQuerySql() {	
		//requete sql:
		//chargement de tous les guides
		/* select * from guide
		 */
		Query queryJpql = em.createNamedQuery("findAllGuides");
		List<Guide> guides =queryJpql.getResultList();
		System.out.println(guides.size());
		assertTrue(guides.size()>0);								
	}
	
	@Test
	public void testGAfficherNbrElevesJpQuery() {	
		//requete sql:
		//chargement de tous les eleves
		/* select count(*) from eleve
		 */
		Query queryJpql = em.createQuery("select count(eleve) from Eleve eleve");
		Long nbrEleves =(Long) queryJpql.getSingleResult();
		System.out.println(nbrEleves);
		assertTrue(nbrEleves>0);								
	}
	
	@Test
	public void testHAfficherInnerJointureElevesetGuidesJpQuery() {	
		//requete sql:
		//chargement de tous les eleves
		/* select * from eleve e inner join guide g on e.guide_id=g.id
		 */
		Query queryJpqlJointure = em.createQuery("select eleve from Eleve eleve join eleve.guide guide");
		
		List<Eleve> eleves =queryJpqlJointure.getResultList();
		System.out.println(eleves.size());
		assertTrue(eleves.size()>0);								
	}
	
	@Test
	public void testIAfficherLeftJointureElevesetGuidesJpQuery() {	
		//requete sql:
		//chargement de tous les eleves
		/* select * from eleve e left outer join guide g on e.guide_id=g.id
		 */
		Query queryJpqlJointure = em.createQuery("select eleve from Eleve eleve left join eleve.guide guide");
		
		List<Eleve> eleves =queryJpqlJointure.getResultList();
		System.out.println(eleves.size());
		assertTrue(eleves.size()>0);								
	}
	
	@Test
	public void testJAfficherRightJointureElevesetGuidesJpQuery() {	
		//requete sql:
		//chargement de tous les eleves
		/* select * from eleve e right outer join guide g on e.guide_id=g.id
		 */
		Query queryJpqlJointure = em.createQuery("select eleve from Eleve eleve right join eleve.guide guide");
		
		List<Eleve> eleves =queryJpqlJointure.getResultList();
		System.out.println(eleves.size());
		assertTrue(eleves.size()>0);								
	}
	
	@Test
	public void testKAfficherFetchJointureElevesetGuidesJpQuery() {	
		//requete sql:
		//chargement de tous les guides
		/* select g.*,e.* from guide g inner  join eleve e on e.guide_id=g.id
		 */
		Query queryJpqlJointure = em.createQuery("select guide from Guide guide join fetch guide.eleves eleve");
		
		List<Guide> guides =queryJpqlJointure.getResultList();
		System.out.println(guides.size());
		assertTrue(guides.size()>0);								
	}
}

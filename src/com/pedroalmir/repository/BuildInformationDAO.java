/**
 * 
 */
package com.pedroalmir.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pedroalmir.model.BuildInformation;

/**
 * The DAO should work as a translator worlds. Suppose one relational database. 
 * The DAO should seek to know the database and convert into objects to be used by the application. 
 * 
 * Similarly, should know how to pick them up, convert and send SQL statements to the database. 
 * This is how a DAO works.
 * 
 * Usually we have a DAO for each domain object system (Product, Customer, Purchase, etc..), 
 * or for each module, or set of closely related entities. Each DAO must have an interface that 
 * specifies the methods of data manipulation. Our codes work only with the interfaces of DAOs, 
 * ignoring the implementation used. This is a good practice, not only in terms of persistence, 
 * but in several other points of an application.
 * 
 * This class represents the build entity DAO.
 * 
 * @author Pedro Almir
 * 
 */
public class BuildInformationDAO {
	
	/**
	 * List all builds registered in the database.
	 * 
	 * @return list with all builds
	 */
	@SuppressWarnings("unchecked")
	public List<BuildInformation> listAll() {
		EntityManager em = EMFService.get().createEntityManager();
		/* Read the existing entries */
		Query q = em.createQuery("select b from BuildInformation b");
		List<BuildInformation> builds = q.getResultList();
		
		Collections.sort(builds, new Comparator<BuildInformation>() {

			@Override
			public int compare(BuildInformation o1, BuildInformation o2) {
				Date date1 = o1.getBuildDate();
		        Date date2 = o2.getBuildDate();

		        return (date1.getTime() > date2.getTime() ? -1 : 1);     //descending
			}
			
		});
		
		return builds;
	}

	/**
	 * Create and add new build information
	 * 
	 * @param buildInformation
	 * 					BuildInformation class
	 */
	public void save(BuildInformation buildInformation) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(buildInformation);
			em.close();
		}
	}
	
	/**
	 * Remove a build information
	 * 
	 * @param id
	 * 			build identification
	 */
	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			BuildInformation buildInformation = em.find(BuildInformation.class, id);
			em.remove(buildInformation);
		} finally {
			em.close();
		}
	}
}

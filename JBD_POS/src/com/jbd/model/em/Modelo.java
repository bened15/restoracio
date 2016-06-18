package com.jbd.model.em;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//ya no se utilizo la clase, porque se efectua con spring la inyeccion del EM  y tood se maneja en el persistence

public class Modelo {

//	private EntityManagerFactory entityManagerFactory;
//	private EntityManager entityManager;
//
//	public Modelo() {
//
//	}
//
//	public EntityManager createEntityManager() {
//		// RequestContext context = RequestContext.getCurrentInstance();
//		// FacesContext faceContext = FacesContext.getCurrentInstance();
//		// Login usBeanLogin =
//		// faceContext.getApplication().evaluateExpressionGet(
//		// faceContext, "#{loginB}", Login.class);
//
//		// System.out.println("Cree el entitymanager ");
//		Properties props = new Properties();
//		/**************************
//		 * PARA DESARROLLO
//		 **********************************/
//		/***************************************************************************/
//		//
//		// props.setProperty("javax.persistence.jdbc.driver","com.mysql.jdbc.Driver");
//		props.setProperty("javax.persistence.jdbc.url", "jdbc:mysql://localhost:8080/restoracio");
//		props.setProperty("javax.persistence.jdbc.user", "root");
//		props.setProperty("javax.persistence.jdbc.password", "root");
//
//		/***************************************************************************/
//		/***************************************************************************/
//
//		/**************************
//		 * PARA PRODUCCION
//		 **********************************/
//		/***************************************************************************/
//
//		// props.setProperty("javax.persistence.jdbc.url",
//		// "jdbc:oracle:thin:@mhsafi4.mh.gob.sv:1521:aficen");
//		// props.setProperty("javax.persistence.jdbc.user", us);
//		// props.setProperty("javax.persistence.jdbc.password", pass);
//
//		/***************************************************************************/
//		/***************************************************************************/
//		try {
//			entityManagerFactory = Persistence.createEntityManagerFactory("Modelos", props);
//			this.setEntityManager(entityManagerFactory.createEntityManager());
//
//			return entityManager;
//		} catch (Exception e) {
//			e.printStackTrace();
//			// if (e.getMessage().contains(
//			// "Unable to build entity manager factory")) {
//			// //Cuando no se puede conectar con la URL primaria, se prueba con
//			// la secundaria!
//			// props.remove("javax.persistence.jdbc.url");
//			// props.setProperty("javax.persistence.jdbc.url",
//			// "jdbc:oracle:thin:@mhsafi6.mh.gob.sv:1521:aficen");
//			// entityManagerFactory = Persistence.createEntityManagerFactory(
//			// "Modelos", props);
//			// this.setEntityManager(entityManagerFactory
//			// .createEntityManager());
//			//
//			// return entityManager;
//			//
//			// } else {
//			//
//			 return null;
//			// }
//
//		}
//
//	}
//
//	public EntityManager getEntityManager() {
//		return entityManager;
//	}
//
//	public void setEntityManager(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}
//
//	//
//	// //
//	public void destroyEntityManagerFactory() {
//		entityManagerFactory.close();
//	}
//	//
//	//
//	// public void consultarAlumnosEnCurso() {
//	//
//	// EntityManager em = entityManagerFactory.createEntityManager();
//	// em.getTransaction().begin();
//	//
//	// String alumnosEnCursoQueryString =
//	// "SELECT a FROM Curso c JOIN c.alumnos a ";
//	// alumnosEnCursoQueryString +=
//	// "WHERE c.nombre = 'Programación Orientada a Objetos - 2013'";
//	// Query alumnosEnCursoQuery = em.createQuery(alumnosEnCursoQueryString,
//	// Alumno.class);
//	// alumnosEnCursoQuery.getResultList();
//	//
//	// em.getTransaction().commit();
//	// }

}

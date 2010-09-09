package qcs.base.constantes;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import qcs.persistence.hibernate.exception.InfrastructureException;



public class HibernateUtil {

	private static Configuration configuration;
	private static SessionFactory sessionFactory;

	// Create the initial SessionFactory from the default configuration files
	static {
		try {
			configuration = new AnnotationConfiguration();
			sessionFactory = configuration.configure().buildSessionFactory();
		} catch (Exception ex) {
			// We have to catch Throwable, otherwise we will miss
			// NoClassDefFoundError and other subclasses of Error
			//			log.error("Building SessionFactory failed.", ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession()throws InfrastructureException {
		Session s = (Session) sessionFactory.openSession();
		try {
			return s;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			throw new InfrastructureException(ex);
		}
	}
}

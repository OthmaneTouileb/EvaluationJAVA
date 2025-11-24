package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            // Alternative: configure from properties file
            if (configuration.getProperty("hibernate.connection.url") == null) {
                configuration.setProperty("hibernate.connection.driver_class", 
                    "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", 
                    "jdbc:mysql://localhost:3306/etat_civil?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "");
                configuration.setProperty("hibernate.dialect", 
                    "org.hibernate.dialect.MySQL8Dialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.format_sql", "true");
            }
            
            // Add entity classes
            configuration.addAnnotatedClass(ma.projet.beans.Personne.class);
            configuration.addAnnotatedClass(ma.projet.beans.Homme.class);
            configuration.addAnnotatedClass(ma.projet.beans.Femme.class);
            configuration.addAnnotatedClass(ma.projet.beans.Mariage.class);
            
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}


package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                
                // Charger les propriétés depuis application.properties
                Properties settings = new Properties();
                InputStream inputStream = HibernateUtil.class.getClassLoader()
                        .getResourceAsStream("application.properties");
                
                if (inputStream != null) {
                    settings.load(inputStream);
                    
                    // Mapper les propriétés du fichier vers les propriétés Hibernate
                    Properties hibernateProperties = new Properties();
                    hibernateProperties.put(Environment.DRIVER, 
                            settings.getProperty("hibernate.connection.driver_class"));
                    hibernateProperties.put(Environment.URL, 
                            settings.getProperty("hibernate.connection.url"));
                    hibernateProperties.put(Environment.USER, 
                            settings.getProperty("hibernate.connection.username"));
                    hibernateProperties.put(Environment.PASS, 
                            settings.getProperty("hibernate.connection.password"));
                    hibernateProperties.put(Environment.DIALECT, 
                            settings.getProperty("hibernate.dialect"));
                    hibernateProperties.put(Environment.SHOW_SQL, 
                            settings.getProperty("hibernate.show_sql", "true"));
                    hibernateProperties.put(Environment.FORMAT_SQL, 
                            settings.getProperty("hibernate.format_sql", "true"));
                    hibernateProperties.put(Environment.HBM2DDL_AUTO, 
                            settings.getProperty("hibernate.hbm2ddl.auto", "update"));
                    
                    configuration.setProperties(hibernateProperties);
                    inputStream.close();
                } else {
                    // Fallback si le fichier n'est pas trouvé
                    System.err.println("Fichier application.properties non trouvé, utilisation des valeurs par défaut");
                    settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    settings.put(Environment.URL, "jdbc:mysql://localhost:3306/gestion_projets?useSSL=false&serverTimezone=UTC");
                    settings.put(Environment.USER, "root");
                    settings.put(Environment.PASS, "toor");
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                    settings.put(Environment.SHOW_SQL, "true");
                    settings.put(Environment.FORMAT_SQL, "true");
                    settings.put(Environment.HBM2DDL_AUTO, "update");
                    configuration.setProperties(settings);
                }
                
                // Ajouter les classes annotées
                configuration.addAnnotatedClass(ma.projet.classes.Employe.class);
                configuration.addAnnotatedClass(ma.projet.classes.Projet.class);
                configuration.addAnnotatedClass(ma.projet.classes.Tache.class);
                configuration.addAnnotatedClass(ma.projet.classes.EmployeTache.class);
                
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}


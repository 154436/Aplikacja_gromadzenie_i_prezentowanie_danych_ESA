package esa.Project;

import esa.Project.Entities.Pomiar;
import esa.Project.Entities.PunktPomiarowy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionConfig {
    public static Session session;
    static{
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Pomiar.class);
        configuration.addAnnotatedClass(PunktPomiarowy.class);
        // Create Session Factory
        SessionFactory sessionFactory
                = configuration.buildSessionFactory();
        // Initialize Session Object
        session = sessionFactory.openSession();
        System.out.println("Session config start");
    }
}

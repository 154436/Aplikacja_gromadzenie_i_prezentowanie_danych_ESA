package esa.Project.Transactions;

import esa.Project.Entities.PunktPomiarowy;
import esa.Project.SessionConfig;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class PunktPomiarowyTransactions {
    public static PunktPomiarowy getPunktPomiarowyById(int id){
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PunktPomiarowy> cq = cb.createQuery(PunktPomiarowy.class);
        Root<PunktPomiarowy> rootEntry = cq.from(PunktPomiarowy.class);
        CriteriaQuery<PunktPomiarowy> punktPomiarowyById = cq.select(rootEntry).where(cb.equal(rootEntry.get("id"), id));
        Query<PunktPomiarowy> query = session.createQuery(punktPomiarowyById);
        PunktPomiarowy punktPomiarowy = query.getSingleResult();
        return punktPomiarowy;
    }
    public static List<PunktPomiarowy> getPunktPomiarowyByCity(String city){
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PunktPomiarowy> cq = cb.createQuery(PunktPomiarowy.class);
        Root<PunktPomiarowy> rootEntry = cq.from(PunktPomiarowy.class);
        CriteriaQuery<PunktPomiarowy> punktPomiarowyByCity = cq.select(rootEntry).where(cb.equal(rootEntry.get("city"), city));
        Query<PunktPomiarowy> query = session.createQuery(punktPomiarowyByCity);
        return query.getResultList();
    }


    public static List<PunktPomiarowy> getAllPunktPomiarowy(){
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PunktPomiarowy> cq = cb.createQuery(PunktPomiarowy.class);
        Root<PunktPomiarowy> rootEntry = cq.from(PunktPomiarowy.class);
        CriteriaQuery<PunktPomiarowy> punktPomiarowyById = cq.select(rootEntry);
        Query<PunktPomiarowy> query = session.createQuery(punktPomiarowyById);
        return query.getResultList();
    }

    public static void addPunktPomiarowy(String city, String street, String zipCode, String schoolName, float latitude, float longitude){
        Session session = SessionConfig.session;
        session.beginTransaction();
        PunktPomiarowy PunktPomiarowy = new PunktPomiarowy();
        PunktPomiarowy.setCity(city);
        PunktPomiarowy.setStreet(street);
        PunktPomiarowy.setZipCode(zipCode);
        PunktPomiarowy.setSchoolName(schoolName);
        PunktPomiarowy.setLatitude(latitude);
        PunktPomiarowy.setLongitude(longitude);
        session.save(PunktPomiarowy);
        session.getTransaction().commit();
    }

    public static void addPunktIfNotExists(PunktPomiarowy station){
        //TODO uzupelnic
    }
}

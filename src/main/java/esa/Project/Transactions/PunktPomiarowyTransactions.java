package esa.Project.Transactions;

import esa.Project.Entities.PunktPomiarowy;
import esa.Project.SessionConfig;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
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
    private static int getIdIfExisted(PunktPomiarowy station){
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PunktPomiarowy> cq = cb.createQuery(PunktPomiarowy.class);
        Root<PunktPomiarowy> rootEntry = cq.from(PunktPomiarowy.class);
        Predicate[] predicates = new Predicate[4];
        predicates[0] = cb.equal(rootEntry.get("city"), station.getCity());
        predicates[1] = cb.equal(rootEntry.get("street"), station.getStreet());
        predicates[2] = cb.equal(rootEntry.get("zipCode"), station.getZipCode());
        predicates[3] = cb.equal(rootEntry.get("schoolName"), station.getSchoolName());
        //predicates[4] = cb.equal(rootEntry.get("latitude"), station.getLatitude());
        //predicates[5] = cb.equal(rootEntry.get("longitude"), station.getLongitude());

        CriteriaQuery<PunktPomiarowy> punkt = cq.select(rootEntry).where(predicates);
        Query<PunktPomiarowy> query = session.createQuery(punkt);
        try {
            List<PunktPomiarowy> punktPomiarowy = query.getResultList();
            System.out.println("JEEEEST");
            return punktPomiarowy.get(0).getId();
        }
        catch(jakarta.persistence.NoResultException | IndexOutOfBoundsException exc){
            System.out.println("Nie znaleziono");
            addPunktPomiarowy(station.getCity(), station.getStreet(), station.getZipCode(), station.getSchoolName(), station.getLatitude(), station.getLongitude());
            punkt = cq.select(rootEntry).where(predicates);
            query = session.createQuery(punkt);
            return query.getSingleResult().getId();
        }


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
        try{
        session.beginTransaction();
        }
        catch(IllegalStateException exc){

        }
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

    public static PunktPomiarowy addPunktIfNotExists(PunktPomiarowy station){
        int id = getIdIfExisted(station);
        if( id != -1) {
            station.setId(id);
        }
        return station;
    }
}

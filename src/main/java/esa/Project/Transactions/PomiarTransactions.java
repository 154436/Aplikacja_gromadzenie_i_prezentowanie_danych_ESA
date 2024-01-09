package esa.Project.Transactions;
import esa.Project.Entities.Pomiar;
import esa.Project.Entities.PunktPomiarowy;
import esa.Project.SessionConfig;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class PomiarTransactions {
    public static Pomiar getPomiarById(Session session, int id){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pomiar> cq = cb.createQuery(Pomiar.class);
        Root<Pomiar> rootEntry = cq.from(Pomiar.class);
        CriteriaQuery<Pomiar> pomiarById = cq.select(rootEntry).where(cb.equal(rootEntry.get("id"), id));
        Query<Pomiar> query = session.createQuery(pomiarById);
        Pomiar pomiar = query.getSingleResult();
        return pomiar;
    }

    public static List<Pomiar> getAllPomiar(){
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pomiar> cq = cb.createQuery(Pomiar.class);
        Root<Pomiar> rootEntry = cq.from(Pomiar.class);
        CriteriaQuery<Pomiar> pomiarById = cq.select(rootEntry);
        Query<Pomiar> query = session.createQuery(pomiarById);
        return query.getResultList();
    }

    public static List<Pomiar> getAllPomiarCity(String city){
        List<Pomiar> all = new ArrayList<>();
        List<PunktPomiarowy> stations = PunktPomiarowyTransactions.getPunktPomiarowyByCity(city);
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pomiar> cq = cb.createQuery(Pomiar.class);
        Root<Pomiar> rootEntry = cq.from(Pomiar.class);
        for(PunktPomiarowy station : stations){
            CriteriaQuery<Pomiar> pomiarById = cq.select(rootEntry).where(cb.equal(rootEntry.get("punktPomiarowy"), station));;
            Query<Pomiar> query = session.createQuery(pomiarById);
            all.addAll(query.getResultList());
        }
        return all;
    }

    public static List<Pomiar> getAllPomiarPeriod(Date start, Date end){
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pomiar> cq = cb.createQuery(Pomiar.class);
        Root<Pomiar> rootEntry = cq.from(Pomiar.class);
        CriteriaQuery<Pomiar> pomiarById = cq.select(rootEntry).where(cb.between(rootEntry.get("date"), start, end));
        Query<Pomiar> query = session.createQuery(pomiarById);
        return query.getResultList();
    }

    public static List<Pomiar> getAllPomiarCityPeriod(String city, Date start, Date end){
        List<Pomiar> all = new ArrayList<>();
        List<PunktPomiarowy> stations = PunktPomiarowyTransactions.getPunktPomiarowyByCity(city);
        Session session = SessionConfig.session;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pomiar> cq = cb.createQuery(Pomiar.class);
        Root<Pomiar> rootEntry = cq.from(Pomiar.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.between(rootEntry.get("date"), start, end);
        for(PunktPomiarowy station : stations){
            predicates[1] = cb.equal(rootEntry.get("punktPomiarowy"), station);
            CriteriaQuery<Pomiar> pomiarById = cq.select(rootEntry).where(predicates);
            Query<Pomiar> query = session.createQuery(pomiarById);
            all.addAll(query.getResultList());
        }
        return all;
    }

    public static void addPomiar(Date date, Time time, float temperature, float humidity, float pressure, float pm25, float pm10, PunktPomiarowy punktPomiarowy){
        Session session = SessionConfig.session;
        session.beginTransaction();
        Pomiar pomiar = new Pomiar();
        pomiar.setDate(date);
        pomiar.setTime(time);
        pomiar.setTemperature(temperature);
        pomiar.setHumidity(humidity);
        pomiar.setPressure(pressure);
        pomiar.setPm10(pm10);
        pomiar.setPm25(pm25);
        pomiar.setPunktPomiarowy(punktPomiarowy);
        PunktPomiarowyTransactions.addPunktIfNotExists(punktPomiarowy);
        //TODO nie ma takiej lokalizacji
        System.out.println(pomiar.getPunktPomiarowy());
        System.out.println(pomiar.getPunktPomiarowy().getId());
        session.save(pomiar);
        session.getTransaction().commit();
    }

    public static void addPomiar(Pomiar pomiar){
        Session session = SessionConfig.session;
        pomiar.setPunktPomiarowy(PunktPomiarowyTransactions.addPunktIfNotExists(pomiar.getPunktPomiarowy()));
        session.beginTransaction();
        session.save(pomiar);
        session.getTransaction().commit();
    }


    public static void addManyPomiar(Pomiar[] measData){
        for(Pomiar meas : measData){
            addPomiar(meas);
        }
    }

    public static List<Pomiar> getAllPomiarCityById(int cityId) {
        Session session = SessionConfig.session;
        PunktPomiarowy station = PunktPomiarowyTransactions.getPunktPomiarowyById(cityId);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pomiar> cq = cb.createQuery(Pomiar.class);
        Root<Pomiar> rootEntry = cq.from(Pomiar.class);
        CriteriaQuery<Pomiar> pomiarById = cq.select(rootEntry).where(cb.equal(rootEntry.get("punktPomiarowy"), station));;
        Query<Pomiar> query = session.createQuery(pomiarById);
        return query.getResultList();
    }

    public static List<Pomiar> getAllPomiarCityPeriodById(int cityId, Date start, Date end) {


        Session session = SessionConfig.session;
        PunktPomiarowy station = PunktPomiarowyTransactions.getPunktPomiarowyById(cityId);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pomiar> cq = cb.createQuery(Pomiar.class);
        Root<Pomiar> rootEntry = cq.from(Pomiar.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.between(rootEntry.get("date"), start, end);
        predicates[1] = cb.equal(rootEntry.get("punktPomiarowy"), station);
        CriteriaQuery<Pomiar> pomiarById = cq.select(rootEntry).where(predicates);
        Query<Pomiar> query = session.createQuery(pomiarById);
        return query.getResultList();
    }
}

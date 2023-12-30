package esa.Project;

import esa.Project.Entities.PunktPomiarowy;
import esa.Project.Transactions.PomiarTransactions;
import esa.Project.Transactions.PunktPomiarowyTransactions;
//import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class ProjektApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ProjektApplication.class, args);
//        try {
//            //EsaJSONData.handleJSONObject(EsaJSONData.getJson());
//            //EsaJSONData.getJson();
//
//            // */5    *    *    *    * co 5 minut
//        } catch (java.io.IOException exc) {
//            System.out.println(exc.fillInStackTrace());
//        }

    }
}

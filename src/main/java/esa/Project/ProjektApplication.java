package esa.Project;

import esa.Project.Entities.PunktPomiarowy;
import esa.Project.Transactions.PomiarTransactions;
import esa.Project.Transactions.PunktPomiarowyTransactions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ProjektApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ProjektApplication.class, args);
    }
}

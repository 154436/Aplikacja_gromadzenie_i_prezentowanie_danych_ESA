package esa.Project.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.w3c.dom.html.HTMLImageElement;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "pomiar")
@Getter
@Setter
@NoArgsConstructor
public class Pomiar {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "pomiar_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private int id;
    @Column(name = "data")
    private Date date;
    @Column(name = "godzina")
    private Time time;
    @Column(name = "temperatura")
    private float temperature;
    @Column(name = "wilgotnosc")
    private float humidity;
    @Column(name = "cisnienie")
    private float pressure;
    @Column(name = "pm_25")
    private float pm25;
    @Column(name = "pm_10")
    private float pm10;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="punkt_pomiarowy_id", nullable = true)
    private PunktPomiarowy punktPomiarowy;

    public Pomiar(float temperature, float humidity, float pressure, float pm25, float pm10){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(date).append("").append(punktPomiarowy.getId()).append(" ").append(punktPomiarowy.getCity());
        return sb.toString();
    }
}
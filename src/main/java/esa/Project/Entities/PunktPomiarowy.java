package esa.Project.Entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "punkt_pomiarowy")
@Getter
@Setter
@NoArgsConstructor
public class PunktPomiarowy {
    @Id
    @Column(name = "id", nullable = true)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "punkt_pomiarowy_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private int id;
    @Column(name = "miasto")
    private String city;
    @Column(name = "ulica")
    private String street;
    @Column(name = "kod_pocztowy")
    private String zipCode;
    @Column(name = "nazwa_szkoły")
    private String schoolName;
    @Column(name = "szerokosc_geo")
    private float latitude;
    @Column(name = "dlugosc")
    private float longitude;

    public PunktPomiarowy(String city, String street, String zipCode, String schoolName, float latitude, float longitude){
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.schoolName = schoolName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(city).append(" ").append(street).append(" ").append(zipCode).append(" ").append(schoolName);
        return sb.toString();
    }
}


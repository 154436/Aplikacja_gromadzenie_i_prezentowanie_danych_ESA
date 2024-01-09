package esa.Project.Mappers;

import esa.Project.Entities.PunktPomiarowy;

import java.util.List;

public class JSONMapperPunkt {

    static public String getJSON(List<PunktPomiarowy> data){
        StringBuilder json = new StringBuilder("{punkty: [");
        for(PunktPomiarowy punkt : data){
            json.append(getRow(punkt));
        }
        return json.append("]}").toString();
    }
    public static String getOnePunkt(PunktPomiarowy punktPomiarowy) {
        return getRow(punktPomiarowy).toString();
    }
    static private StringBuilder getRow(PunktPomiarowy punkt){
        StringBuilder row = new StringBuilder("{");
        row.append("id: ").append(punkt.getId()).append(",");
        row.append("city: ").append(punkt.getCity() != " " ? punkt.getCity() : "null").append(",");
        row.append("street: ").append(!punkt.getStreet().equals(" ") ? punkt.getStreet() : "null").append(",");
        row.append("schoolName: ").append(punkt.getSchoolName() != " " ? punkt.getSchoolName().replace('"', '*').replace(',','+').replace(",,", "*").replace('/', 'a') : "null").append(",");
        row.append("zipcode: ").append(punkt.getZipCode() != " " ? punkt.getZipCode() : "null").append(",");
        row.append("latitude: ").append(punkt.getLatitude()).append(",");
        row.append("longitude: ").append(punkt.getLongitude());
        return row.append("}");
    }


}

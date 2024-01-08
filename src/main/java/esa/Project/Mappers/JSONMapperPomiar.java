package esa.Project.Mappers;

import esa.Project.Entities.Pomiar;
import esa.Project.Entities.PunktPomiarowy;

import java.util.List;

public class JSONMapperPomiar {
    static public String getJSON(List<Pomiar> data){
        StringBuilder json = new StringBuilder("{pomiary: [");
        for(Pomiar pomiar : data){
            json.append(getRow(pomiar)).append(",");
        }
        json.deleteCharAt(json.length()-1);
        return json.append("]}").toString();
    }

    static private StringBuilder getRow(Pomiar punkt){
        StringBuilder row = new StringBuilder("{");
        row.append("id: ").append(punkt.getId()).append(",");
        row.append("date: ").append(punkt.getDate()).append(",");
        //TODO : w godzinie sypie json
        row.append("time: ").append(punkt.getTime().toString().replace(':','-')).append(",");
        row.append("temperature: ").append(punkt.getTemperature()).append(",");
        row.append("pressure: ").append(punkt.getPressure()).append(",");
        row.append("humidity: ").append(punkt.getHumidity()).append(",");
        row.append("pm10: ").append(punkt.getPm10()).append(",");
        row.append("pm25: ").append(punkt.getPm25()).append(",");
        row.append("punkt pomiarowy: ").append(JSONMapperPunkt.getOnePunkt(punkt.getPunktPomiarowy()));
        return row.append("}");
    }
}

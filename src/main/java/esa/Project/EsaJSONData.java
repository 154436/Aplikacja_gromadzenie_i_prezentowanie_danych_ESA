package esa.Project;


import esa.Project.Entities.Pomiar;
import esa.Project.Entities.PunktPomiarowy;
import esa.Project.Transactions.PomiarTransactions;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

@Component
public class EsaJSONData {
    private static String uriString = "https://public-esa.ose.gov.pl/api/v1/smog";
    private static URL url;

    static {
        try {
            url = new URI(uriString).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @Scheduled(fixedRate = 300000)
    public static void getJson() throws IOException {
        String json = IOUtils.toString(url, Charset.forName("UTF-8"));
        json = json.substring(14, json.length());
        for (int i = json.length() - 1; i > 0; i--) {
            if (json.charAt(i) == ']') {
                json = json.substring(0, i);
                break;
            }
        }
        readSchoolData(json);
    }

    private static void readSchoolData(String json) {
        String[] schools = json.split("school");
        String[][] schoolsData = new String[schools.length][2];
        int valid = 0;
        for (int i = 0; i < schools.length; i++) {
            try {
                String[] data = schools[i].split("data");
                schoolsData[valid][0] = data[0].replace('{', ' ').replace('}', ' ').substring(3, data[0].length() - 3);
                //TODO już tutaj wywalić timestamp w pomiarze, znajdując pierwsze wystąpienie }
                String outTimestamp = trimTimestamp(data[1]);
                schoolsData[valid][1] = outTimestamp.replace('{', ' ').replace('}', ' ').substring(3);

                //System.out.println(schoolsData[valid][0]);
                //System.out.println(schoolsData[valid][1]);
                valid++;
            } catch (IndexOutOfBoundsException exc) {
                /*System.out.println("Brakuje drugiego: ");
                System.out.println(schools[i]);*/
            }
        }
        parseToDataBase(schoolsData);
    }

    private static String trimTimestamp(String row) {
        int firstClosedIndex = 0;
        char closed = '}';
        for (int i = 0; i < row.length(); i++) {
            if (row.charAt(i) == closed) {
                firstClosedIndex = i;
                break;
            }
        }
        return row.substring(0, firstClosedIndex);
    }

    private static void parseToDataBase(String[][] schoolsData) {
        Pomiar[] measData = new Pomiar[schoolsData.length];
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.valueOf(now.toString().split("T")[0]);
        String timeStr = now.toString().split("T")[1];
        String[] timeElem = timeStr.split(":");
        Time time = new Time(Integer.parseInt(timeElem[0]), Integer.parseInt(timeElem[1]), 0);

        for (int i = 0; i < schoolsData.length; i++) {
            if (schoolsData[i][0] == null || schoolsData[i][1] == null) {
                break;
            }
            PunktPomiarowy school = parseSchool(schoolsData[i][0]);
            Pomiar meas = parseMeasData(schoolsData[i][1]);
            if (meas != null) {
                meas.setPunktPomiarowy(school);
                meas.setTime(time);
                meas.setDate(date);
                //TODO save to database
            }
            measData[i] = meas;
        }
        PomiarTransactions.addManyPomiar(deleteVoid(measData));
    }

    private static Pomiar[] deleteVoid(Pomiar[] meas){
        int correct = 0;
        for(Pomiar record : meas){
            if(record != null){
                correct++;
            }
        }
        Pomiar[] correctMeas = new Pomiar[correct];
        int counter = 0;
        for(Pomiar record : meas){
            if(record != null){
                correctMeas[counter] = record;
                counter++;
            }
        }
        return correctMeas;
    }


    private static PunktPomiarowy parseSchool(String school) {
        school = school.trim();
        JSONObject ob = new JSONObject("{" + school + "}");
        String city = ob.get("city").toString().trim();
        String street = ob.get("street").toString().trim();
        String zipCode = ob.get("post_code").toString().trim();
        String schoolName = ob.get("name").toString().trim();
        float latitude = Float.parseFloat(ob.get("latitude").toString().trim());
        float longitude = Float.parseFloat(ob.get("longitude").toString().trim());
        PunktPomiarowy punktPomiarowy = new PunktPomiarowy(city, street, zipCode, schoolName, latitude, longitude);
        //System.out.println(punktPomiarowy);
        return punktPomiarowy;
    }

    private static Pomiar parseMeasData(String data) {
        //System.out.println(data);
        data = data.trim();
        try {
            JSONObject ob = new JSONObject("{" + data + "}");
            float temperature = Float.parseFloat(ob.get("temperature_avg").toString().trim());
            float humidity = Float.parseFloat(ob.get("humidity_avg").toString().trim());
            float pressure = Float.parseFloat(ob.get("pressure_avg").toString().trim());
            float pm25 = Float.parseFloat(ob.get("pm25_avg").toString().trim());
            float pm10 = Float.parseFloat(ob.get("pm10_avg").toString().trim());
            Pomiar pomiar = new Pomiar(temperature, humidity, pressure, pm25, pm10);
            return pomiar;
        } catch (org.json.JSONException | NumberFormatException exc) {
            System.out.println(exc.getMessage());
            System.out.println(data);
            return null;
        }
    }


}

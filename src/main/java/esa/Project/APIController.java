package esa.Project;

import esa.Project.Mappers.JSONMapperPomiar;
import esa.Project.Mappers.JSONMapperPunkt;
import esa.Project.Transactions.PomiarTransactions;
import esa.Project.Transactions.PunktPomiarowyTransactions;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
public class APIController {
    @GetMapping("/api/hello")
    @ResponseBody
    public String hello() {
        return "Hello from Spring REST API!";
    }

    @PostMapping("/api/greet")
    @ResponseBody
    public String greet(@RequestBody String name) {
        JSONObject result = new JSONObject("{Hello: " + name + "}");
        return result.toString();
    }

    @GetMapping("/api/pomiary")
    @ResponseBody
    public String getAllPomiary() {
        String resultStr = JSONMapperPomiar.getJSON(PomiarTransactions.getAllPomiar());
        System.out.println(resultStr.substring(129380, 129410));
        JSONObject result = new JSONObject(resultStr);
        return result.toString();
    }

    @GetMapping("/api/lokalizacje")
    @ResponseBody
    public String getAllPunktyPomiarowe() {
        JSONObject result = new JSONObject(JSONMapperPunkt.getJSON(PunktPomiarowyTransactions.getAllPunktPomiarowy()));
        return result.toString();
    }

    @PostMapping("/api/miasto")
    @ResponseBody
    public String getPomiaryPunkt(@RequestBody String city) {
        JSONObject ob = new JSONObject(city);
        String cityName = ob.get("city").toString().trim();
        JSONObject result = new JSONObject(JSONMapperPomiar.getJSON(PomiarTransactions.getAllPomiarCity(cityName)));
        return result.toString();
    }

    @PostMapping("/api/punkt-id")
    @ResponseBody
    public String getPomiaryPunktById(@RequestBody String city) {
        JSONObject ob = new JSONObject(city);
        String cityId = ob.get("cityId").toString().trim();
        try{
            int id = Integer.parseInt(cityId);
            JSONObject result = new JSONObject(JSONMapperPomiar.getJSON(PomiarTransactions.getAllPomiarCityById(id)));
            return result.toString();
        }
        catch(NumberFormatException exc){
            JSONObject result = new JSONObject("Nieprawidlowy format danych");
            return result.toString();
        }
    }

    @PostMapping("/api/data")
    @ResponseBody
    public String getPomiaryData(@RequestBody String data) {
        JSONObject ob = new JSONObject(data);
        String startDate = ob.get("startDate").toString().trim();
        String endDate = ob.get("endDate").toString().trim();
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);
        String resultStr = JSONMapperPomiar.getJSON(PomiarTransactions.getAllPomiarPeriod(start, end));
        System.out.println(resultStr);
        JSONObject result = new JSONObject(resultStr);
        return result.toString();
    }

    @PostMapping("/api/miasto-data")
    @ResponseBody
    public String getPomiaryPunktData(@RequestBody String data) {
        JSONObject ob = new JSONObject(data);
        String cityName = ob.get("city").toString().trim();
        String startDate = ob.get("startDate").toString().trim();
        String endDate = ob.get("endDate").toString().trim();
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);
        JSONObject result = new JSONObject(JSONMapperPomiar.getJSON(PomiarTransactions.getAllPomiarCityPeriod(cityName, start, end)));
        return result.toString();
    }

    @PostMapping("/api/punkt-id-data")
    @ResponseBody
    public String getPomiaryPunktByIdData(@RequestBody String data) {
        JSONObject ob = new JSONObject(data);
        try {
            String cityId = ob.get("cityId").toString().trim();
            String startDate = ob.get("startDate").toString().trim();
            String endDate = ob.get("endDate").toString().trim();
            Date start = Date.valueOf(startDate);
            Date end = Date.valueOf(endDate);
            int id = Integer.parseInt(cityId);
            JSONObject result = new JSONObject(JSONMapperPomiar.getJSON(PomiarTransactions.getAllPomiarCityPeriodById(id, start, end)));
            return result.toString();
        }catch(NumberFormatException exc){
            JSONObject result = new JSONObject("Nieprawidlowy format danych");
            return result.toString();
        }
    }

}

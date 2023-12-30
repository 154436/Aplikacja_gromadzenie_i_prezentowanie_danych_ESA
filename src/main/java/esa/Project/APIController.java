package esa.Project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "Hello, " + name + "!";
    }

}

package hello;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Person;

import javax.ws.rs.GET;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GET
    @RequestMapping("/person/{id}")
    public String returnPerson(@PathVariable("id") Integer id)
    {
        Person p1 = new Person("sumit", "deo", "brighton");
        JSONObject obj = new JSONObject(p1.getPersonAsMap());
        return obj.toJSONString();
    }

}

package hello;

import org.springframework.web.bind.annotation.RestController;

import pojo.Person;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/get_person")
    public String returnPerson()
    {
    	Person p1 = new Person("sumit", "deo", "brighton");
    	JSONObject obj = new JSONObject(p1.getPersonAsMap());
    	return obj.toJSONString();
    }
    
}

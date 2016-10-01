package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.RedisConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Person;
import service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.Map;

@Api(value = "Hello Controller")
@RestController
public class HelloController {

    @Autowired
    RedisConfig clusterProperties;

    @Autowired
    UserService userService;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private RedisTemplate<String, Map<String, Object>> redisTemplate;

    @ApiOperation(value = "Sample controller")
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GET
    @RequestMapping("/person/{id}")
    public String returnPerson(@PathVariable("id") Integer id) {
        System.out.print(id);
        Person p1 = new Person("sumit", "deo", "brighton");
        JSONObject obj = new JSONObject(p1.getPersonAsMap());

        RedisTemplate<String, String> redisTemplate = appContext.getBean(StringRedisTemplate.class);
        redisTemplate.opsForValue().set("Ajinkya", "peshave");

        return obj.toJSONString();
    }

    @POST
    @RequestMapping("/person")
    public String addPerson(@RequestBody PersonEntity params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> personMap = (Map<String, Object>) mapper.convertValue(params, Map.class);
        System.out.println(personMap.toString());

        Jackson2JsonRedisSerializer<PersonEntity> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                PersonEntity.class);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.opsForValue().set("new", personMap);

        String result = userService.addPerson(params);
        System.out.println(result);
        return result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PersonEntity {
        @JsonProperty("personID")
        public Long personID;

        @JsonProperty("name")
        public String name;

        @JsonProperty("password")
        public String password;
    }
}

package hello;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonSimpleJsonParser;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import configuration.RedisConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pojo.Person;

@Api(value = "Hello Controller")
@RestController
public class HelloController {

	private static final int RedisTemplate = 0;

	private static final int String = 0;

	private static final int Map = 0;

	private static final int Object = 0;

	@Autowired
	RedisConfig clusterProperties;

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

	// @POST
	// @RequestMapping("/person")
	// public Long addPerson(@RequestParam Long personID, @RequestParam String
	// name, @RequestParam String password)
	// {
	// return personID;
	// }

	@POST
	@RequestMapping("/person")
	public Long addPerson(@RequestBody PersonEntity params) 
	{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> personMap = (Map<String, Object>) mapper.convertValue(params, Map.class);
		System.out.println(personMap.toString());
		
		Jackson2JsonRedisSerializer<PersonEntity> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(PersonEntity.class);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		
		redisTemplate.opsForValue().set("new", personMap);
		return params.personID;
	}

	public JedisConnectionFactory connectionFactory() 
	{
		JedisConnectionFactory connectionFactory = appContext.getBean(JedisConnectionFactory.class);
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		return connectionFactory;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class PersonEntity {
		@JsonProperty("personID")
		private Long personID;

		@JsonProperty("name")
		private String name;

		@JsonProperty("password")
		private String password;
	}

}

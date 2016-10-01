package service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.HelloController;
import org.springframework.stereotype.Service;
import pojo.Person;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class UserService {

    public String addPerson(HelloController.PersonEntity personEntity) throws JsonProcessingException {
        JedisPool pool = getJedisPool();

        try (Jedis jedis = pool.getResource()) {
            jedis.incr("USER_COUNT");

            Person person = new Person();
            person.setFirstName(personEntity.name);
            person.setPassword(personEntity.password);
            String UUID = "PERSON" + "__" + personEntity.name + "__" + jedis.get("USER_COUNT");
            person.setUUID(UUID);

            ObjectMapper mapper = new ObjectMapper();
            String personString = mapper.writeValueAsString(person);
            jedis.set(UUID, personString);
            return UUID;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.destroy();
        }
        return null;
    }

    private JedisPool getJedisPool() {
        return new JedisPool(new JedisPoolConfig(), "localhost");
    }
}

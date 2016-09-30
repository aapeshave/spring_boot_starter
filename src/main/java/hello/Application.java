package hello;

import java.util.Arrays;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties.Redis;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.Jedis;

@SpringBootApplication
@ComponentScan(basePackages = {"configuration", "hello"} )
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
    
    @Bean
    public JedisConnectionFactory jedisConnectionFactory()
    {
    	JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
    	connectionFactory.setUsePool(true);
    	connectionFactory.setPort(6379);
    	connectionFactory.setHostName("localhost");
    	return connectionFactory;
    }
    
    
   @Bean
   public StringRedisSerializer stringRedisSerializer()
   {
	   return new StringRedisSerializer();
   }

    @Bean
    public RedisTemplate<String, Map<String, Object>> redisTemplate()
    {
    	RedisTemplate<String, Map<String, Object>> redisTemplate = new RedisTemplate<>();
    	redisTemplate.setConnectionFactory(jedisConnectionFactory());
    	redisTemplate.setKeySerializer(stringRedisSerializer());
    	redisTemplate.setHashKeySerializer(stringRedisSerializer());
    	return redisTemplate;
    }
}

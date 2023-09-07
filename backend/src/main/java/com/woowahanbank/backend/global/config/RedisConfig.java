package com.woowahanbank.backend.global.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {

	@Value("${redis.host}")
	private String host;

	@Value("${redis.port}")
	private int port;

	// @Value("${redis.password}")
	// private String password;

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.registerModule(new JavaTimeModule());

		return mapper;
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		//redisStandaloneConfiguration.setPassword(password);
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	// @Bean
	// public RedisTemplate<String, ChatMessage> redisTemplate() {
	// 	RedisTemplate<String, ChatMessage> template = new RedisTemplate<>();
	// 	template.setConnectionFactory(redisConnectionFactory());
	//
	// 	// Create and configure ObjectMapper for JSR310 java.time
	// 	ObjectMapper objectMapper = new ObjectMapper();
	// 	objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	// 	objectMapper.registerModule(new JavaTimeModule());
	//
	// 	GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
	//
	// 	template.setKeySerializer(new StringRedisSerializer());
	// 	template.setValueSerializer(serializer);
	// 	template.setHashKeySerializer(new StringRedisSerializer());
	// 	template.setHashValueSerializer(serializer);
	//
	// 	template.afterPropertiesSet();
	//
	// 	return template;
	// }

	@Bean
	public RedisTemplate<String, List<String>> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, List<String>> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);

		// Serializer 설정
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		return redisTemplate;
	}

	// @Bean
	// public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, RedisKeyExpirationListener listenerAdapter) {
	// 	RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	// 	container.setConnectionFactory(connectionFactory);
	// 	container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@*:expired"));
	// 	return container;
	// }

	// @Bean
	// public RedisKeyExpirationListener listenerAdapter(RedisKeyExpirationListener listener) {
	// 	return listener;
	// }

}
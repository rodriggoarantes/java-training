package com.treinamento.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("-- Tables Creating ---");
		jdbcTemplate.execute("DROP TABLE user IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE user(" +
				"id SERIAL, name VARCHAR(255), login VARCHAR(255))");

		jdbcTemplate.execute("DROP TABLE work IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE work(" +
				"id SERIAL, name VARCHAR(255))");
		log.info("-- Tables Created ---");
	}
}

package com.training.cardgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CardGameApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(CardGameApplication.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CardGameApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info(" --- creating tables --- ");
		jdbcTemplate.execute("DROP TABLE game IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE game(" +
				"id BIGINT PRIMARY KEY AUTO_INCREMENT, nome VARCHAR(255), " +
				"tipo VARCHAR(255), data TIMESTAMP, saved TIMESTAMP )");

		jdbcTemplate.execute("DROP TABLE player IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE player(id BIGINT PRIMARY KEY AUTO_INCREMENT, nome VARCHAR(255), " +
				"login VARCHAR(255), saved TIMESTAMP )");

		jdbcTemplate.execute("DROP TABLE game_players IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE game_players(game BIGINT NOT NULL, player BIGINT NOT NULL)");
		log.info(" --- finish tables --- ");
	}
}

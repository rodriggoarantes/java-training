package com.connectiondb;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class Application implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("-------- Oracle JDBC Connection TESTE ------");
		log.info("-- Working ---");

		final DataSource dataSource = jdbcTemplate.getDataSource();
		if (dataSource != null) {
			log.info("Conexao realizada com SUCESSO!");

			final HashSet<Connection> conexoes = new HashSet<>(100);
			for (int i=0; i < 101; i++) {
				PreparedStatement prepare = null;
				ResultSet result = null;
				try {
					final Connection connection = dataSource.getConnection();
					conexoes.add(connection);

					final String sysdate = " SELECT SYSDATE AS NOW FROM DUAL ";
					prepare = connection.prepareStatement(sysdate);
					result = prepare.executeQuery();
					if (result.next()) {
						log.info("time : {}", result.getTimestamp("NOW"));
					}
				} catch (Exception e) {
					log.error("ERRO", e);
				} finally {
					if (prepare != null) {
						prepare.close();
					}
					if (result != null) {
						result.close();
					};
				}
			}

			log.info("FECHANDO CONEXOES: {}", conexoes.size());
			final AtomicInteger count = new AtomicInteger(0);
			conexoes.forEach(conn -> {
				try {
					if (count.incrementAndGet() != 100) {
						conn.close();
					}
				} catch (Exception e) {
					log.error("Falha ao fechar conexao", e);
				}
			});
		}

		log.info("-- Worked ---");
		log.info("-------- Oracle JDBC Connection FINALIZADO ------");
	}
}

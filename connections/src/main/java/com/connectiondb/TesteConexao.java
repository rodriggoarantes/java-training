package com.connectiondb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Random;

@Component
public class TesteConexao {
    private static final Logger log = LoggerFactory.getLogger(TesteConexao.class);

    private static final HashSet<Connection> CONEXOES = new HashSet<>(200);

    @Value( "${spring.datasource.username}" )
    private String usernameDb;
    @Value( "${spring.datasource.hikari.maximumPoolSize}" )
    private Integer maxConnection;
    @Value( "${spring.datasource.hikari.connectionTimeout}" )
    private Long timeout;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testeParalelo() throws Exception {
        log.info("-- Working Threads ---");
        CONEXOES.clear();

        final DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource != null) {
            log.info("Conexao realizada com SUCESSO!");

            for (int i=0; i < (maxConnection + 1); i++) {
                final Thread connectionThread = new Thread(() ->
                        runConnection(dataSource, usernameDb));
                connectionThread.start();
            }
        }

        Thread.sleep((long) (timeout * 1.2));
        closeAll();
        log.info("-- Worked Threads ---");
    }

    private static void runConnection(final DataSource dataSource, final String usernameDb) {
        PreparedStatement prepare = null;
        ResultSet result = null;
        try {
            Thread.sleep(random());
            final Connection connection = dataSource.getConnection();
            CONEXOES.add(connection);

            final String sysdate = " select  count(sid) as CONN, SYSDATE as NOW " +
                    "from v$session WHERE UPPER(username)='" + usernameDb.toUpperCase() + "' ";
            prepare = connection.prepareStatement(sysdate);
            result = prepare.executeQuery();
            if (result.next()) {
                log.info("user: {} | time : {} | connections: {}",
                        usernameDb.toUpperCase(),
                        result.getTimestamp("NOW"),
                        result.getInt("CONN"));
            }
        } catch (Exception e) {
            log.error("ERRO ao executar consulta: " + e.getMessage(), e);
        } finally {
            if (prepare != null) {
                try { prepare.close(); }
                catch (Exception ignored) {}
            }
            if (result != null) {
                try { result.close(); }
                catch (Exception ignored) {}
            }
        }
    }

    private static void closeAll() {
        log.info("FECHANDO CONEXOES: {}", CONEXOES.size());
        CONEXOES.forEach(conn -> {
            try {
                    conn.close();
            } catch (Exception e) {
                log.error("Falha ao fechar conexao", e);
            }
        });
    }

    private static long random() {
        final Random rand = new Random();
        return rand.nextInt(5000);
    }
}

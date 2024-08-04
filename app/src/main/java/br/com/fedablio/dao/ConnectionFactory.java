package br.com.fedablio.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public Connection getConexao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://URL:PORTA/BASE", "USER", "PASS");
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }
}
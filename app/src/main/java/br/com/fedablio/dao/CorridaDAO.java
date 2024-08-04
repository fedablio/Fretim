package br.com.fedablio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import br.com.fedablio.model.Corrida;

public class CorridaDAO {

    private static Connection conn;
    private PreparedStatement stmt;
    private Statement st;
    private ResultSet rs;

    public CorridaDAO() {
        conn = new ConnectionFactory().getConexao();
    }

    public String protocolo() {
        return String.valueOf(new Date().getTime());
    }

    public void inserir(Corrida cor) {
        String sql = "INSERT INTO corrida (id_corrida, id_motociclista, origem_corrida, destino_corrida, distancia_corrida, data_corrida, hora_corrida, valor_corrida, lat_corrida, lon_corrida) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cor.getId_corrida());
            stmt.setString(2, cor.getId_motociclista());
            stmt.setString(3, cor.getOrigem_corrida());
            stmt.setString(4, cor.getDestino_corrida());
            stmt.setDouble(5, cor.getDistancia_corrida());
            stmt.setDate(6, cor.getData_corrida());
            stmt.setTime(7, cor.getHora_corrida());
            stmt.setDouble(8, cor.getValor_corrida());
            stmt.setString(9, cor.getLat_corrida());
            stmt.setString(10, cor.getLon_corrida());
            stmt.execute();
            stmt.close();
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public ArrayList<Corrida> listarTodasId(String id_motociclista, String id_corrida) {
        ArrayList<Corrida> lista = new ArrayList<>();
        String sql = "SELECT * FROM corrida WHERE id_motociclista = '" + id_motociclista + "' AND id_corrida = '" + id_corrida + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Corrida cor = new Corrida();
                cor.setId_corrida(rs.getString("id_corrida"));
                cor.setOrigem_corrida(rs.getString("origem_corrida"));
                cor.setDestino_corrida(rs.getString("destino_corrida"));
                cor.setDistancia_corrida(rs.getDouble("distancia_corrida"));
                cor.setData_corrida(rs.getDate("data_corrida"));
                cor.setHora_corrida(rs.getTime("hora_corrida"));
                cor.setValor_corrida(rs.getDouble("valor_corrida"));
                lista.add(cor);
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return lista;
    }

    public ArrayList<Corrida> listarTodasOrigem(String id_motociclista, String origem_corrida) {
        ArrayList<Corrida> lista = new ArrayList<>();
        String sql = "SELECT * FROM corrida WHERE id_motociclista = '" + id_motociclista + "' AND origem_corrida LIKE '%" + origem_corrida + "%' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Corrida cor = new Corrida();
                cor.setId_corrida(rs.getString("id_corrida"));
                cor.setOrigem_corrida(rs.getString("origem_corrida"));
                cor.setDestino_corrida(rs.getString("destino_corrida"));
                cor.setDistancia_corrida(rs.getDouble("distancia_corrida"));
                cor.setData_corrida(rs.getDate("data_corrida"));
                cor.setHora_corrida(rs.getTime("hora_corrida"));
                cor.setValor_corrida(rs.getDouble("valor_corrida"));
                lista.add(cor);
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return lista;
    }

    public ArrayList<Corrida> listarTodasDestino(String id_motociclista, String destino_corrida) {
        ArrayList<Corrida> lista = new ArrayList<>();
        String sql = "SELECT * FROM corrida WHERE id_motociclista = '" + id_motociclista + "' AND destino_corrida LIKE '%" + destino_corrida + "%' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Corrida cor = new Corrida();
                cor.setId_corrida(rs.getString("id_corrida"));
                cor.setOrigem_corrida(rs.getString("origem_corrida"));
                cor.setDestino_corrida(rs.getString("destino_corrida"));
                cor.setDistancia_corrida(rs.getDouble("distancia_corrida"));
                cor.setData_corrida(rs.getDate("data_corrida"));
                cor.setHora_corrida(rs.getTime("hora_corrida"));
                cor.setValor_corrida(rs.getDouble("valor_corrida"));
                lista.add(cor);
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return lista;
    }

    public ArrayList<Corrida> listarTodasData(String id_motociclista, String data_corrida1, String data_corrida2) {
        ArrayList<Corrida> lista = new ArrayList<>();
        String sql = "SELECT * FROM corrida WHERE id_motociclista = '" + id_motociclista + "' AND data_corrida BETWEEN ('" + data_corrida1 + "') AND ('" + data_corrida2 + "') ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Corrida cor = new Corrida();
                cor.setId_corrida(rs.getString("id_corrida"));
                cor.setOrigem_corrida(rs.getString("origem_corrida"));
                cor.setDestino_corrida(rs.getString("destino_corrida"));
                cor.setDistancia_corrida(rs.getDouble("distancia_corrida"));
                cor.setData_corrida(rs.getDate("data_corrida"));
                cor.setHora_corrida(rs.getTime("hora_corrida"));
                cor.setValor_corrida(rs.getDouble("valor_corrida"));
                lista.add(cor);
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return lista;
    }

    public String buscarOrigem(String id) {
        String resultado = "";
        String sql = "SELECT origem_corrida FROM corrida WHERE id_corrida = '" + id + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("origem_corrida");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscarDestino(String id) {
        String resultado = "";
        String sql = "SELECT destino_corrida FROM corrida WHERE id_corrida = '" + id + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("destino_corrida");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public double buscarDistancia(String id) {
        double resultado = 0;
        String sql = "SELECT distancia_corrida FROM corrida WHERE id_corrida = '" + id + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getDouble("distancia_corrida");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public double buscarValor(String id) {
        double resultado = 0;
        String sql = "SELECT valor_corrida FROM corrida WHERE id_corrida = '" + id + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getDouble("valor_corrida");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

}
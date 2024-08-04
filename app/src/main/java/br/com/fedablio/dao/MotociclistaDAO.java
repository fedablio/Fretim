package br.com.fedablio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fedablio.model.Motociclista;
import br.com.fedablio.utility.Porta;

public class MotociclistaDAO {

    private static Connection conn;
    private PreparedStatement stmt;
    private Statement st;
    private ResultSet rs;

    public MotociclistaDAO() {
        conn = new ConnectionFactory().getConexao();
    }

    public void alterar(Motociclista mot) {
        String sql = "UPDATE motociclista SET pass_motociclista = ?, email_motociclista = ?, telefone_motociclista = ?, logradouro_motociclista = ?, numero_motociclista = ?, bairro_motociclista = ?, cidade_motociclista = ?, uf_motociclista = ?, precoquilo_motociclista = ?, precomini_motociclista = ? WHERE id_motociclista = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, new Porta().fecha(mot.getPass_motociclista()));
            stmt.setString(2, mot.getEmail_motociclista());
            stmt.setString(3, mot.getTelefone_motociclista());
            stmt.setString(4, mot.getLogradouro_motociclista());
            stmt.setString(5, mot.getNumero_motociclista());
            stmt.setString(6, mot.getBairro_motociclista());
            stmt.setString(7, mot.getCidade_motociclista());
            stmt.setString(8, mot.getUf_motociclista().toUpperCase());
            stmt.setDouble(9, mot.getPrecoquilo_motociclista());
            stmt.setDouble(10, mot.getPrecimini_motociclista());
            stmt.setString(11, mot.getId_motociclista());
            stmt.execute();
            stmt.close();
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean acessar(String id_motociclista, String situacao_motociclista) {
        boolean resultado = false;
        String sql = "SELECT id_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' AND situacao_motociclista = '" + situacao_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = true;
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public boolean autenticar(Motociclista mot) {
        boolean resultado = false;
        String sql = "SELECT situacao_motociclista FROM motociclista WHERE id_motociclista = '" + mot.getId_motociclista() + "' AND user_motociclista = '" + new Porta().fecha(mot.getUser_motociclista()) + "' AND pass_motociclista = '" + new Porta().fecha(mot.getPass_motociclista()) + "' ";
        System.out.println(sql);
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = true;
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaPass(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT pass_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = new Porta().abre(rs.getString("pass_motociclista"));
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaEmail(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT email_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("email_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaTelefone(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT telefone_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("telefone_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaLogradouro(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT logradouro_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("logradouro_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaNumero(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT numero_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("numero_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaBairro(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT bairro_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("bairro_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaCidade(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT cidade_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("cidade_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public String buscaUf(String id_motociclista) {
        String resultado = "";
        String sql = "SELECT uf_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getString("uf_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public double buscaPrecoquilo(String id_motociclista) {
        double resultado = 0.0;
        String sql = "SELECT precoquilo_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getDouble("precoquilo_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

    public double buscaPrecomini(String id_motociclista) {
        double resultado = 0.0;
        String sql = "SELECT precomini_motociclista FROM motociclista WHERE id_motociclista = '" + id_motociclista + "' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getDouble("precomini_motociclista");
            }
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
        return resultado;
    }

}
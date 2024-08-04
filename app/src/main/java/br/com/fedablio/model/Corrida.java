package br.com.fedablio.model;

import java.sql.Date;
import java.sql.Time;

public class Corrida {

    private String id_corrida;
    private String id_motociclista;
    private String origem_corrida;
    private String destino_corrida;
    private double distancia_corrida;
    private Date data_corrida;
    private Time hora_corrida;
    private double valor_corrida;
    private String lat_corrida;
    private String lon_corrida;

    public String getId_corrida() {
        return id_corrida;
    }

    public void setId_corrida(String id_corrida) {
        this.id_corrida = id_corrida;
    }

    public String getId_motociclista() {
        return id_motociclista;
    }

    public void setId_motociclista(String id_motociclista) {
        this.id_motociclista = id_motociclista;
    }

    public String getOrigem_corrida() {
        return origem_corrida;
    }

    public void setOrigem_corrida(String origem_corrida) {
        this.origem_corrida = origem_corrida;
    }

    public String getDestino_corrida() {
        return destino_corrida;
    }

    public void setDestino_corrida(String destino_corrida) {
        this.destino_corrida = destino_corrida;
    }

    public double getDistancia_corrida() {
        return distancia_corrida;
    }

    public void setDistancia_corrida(double distancia_corrida) {
        this.distancia_corrida = distancia_corrida;
    }

    public Date getData_corrida() {
        return data_corrida;
    }

    public void setData_corrida(Date data_corrida) {
        this.data_corrida = data_corrida;
    }

    public Time getHora_corrida() {
        return hora_corrida;
    }

    public void setHora_corrida(Time hora_corrida) {
        this.hora_corrida = hora_corrida;
    }

    public double getValor_corrida() {
        return valor_corrida;
    }

    public void setValor_corrida(double valor_corrida) {
        this.valor_corrida = valor_corrida;
    }

    public String getLat_corrida() {
        return lat_corrida;
    }

    public void setLat_corrida(String lat_corrida) {
        this.lat_corrida = lat_corrida;
    }

    public String getLon_corrida() {
        return lon_corrida;
    }

    public void setLon_corrida(String lon_corrida) {
        this.lon_corrida = lon_corrida;
    }
}

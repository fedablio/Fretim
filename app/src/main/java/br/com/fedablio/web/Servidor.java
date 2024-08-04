package br.com.fedablio.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Servidor {

    public String informacao(String origins, String destinations) {
        String language = "pt-BR";
        String mode = "driving";
        String key = ""; // Google Cloud
        BufferedReader br;
        try {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?destinations=" + destinations + "&language=" + language + "&mode=" + mode + "&origins=" + origins + "&key=" + key;
            System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }
}
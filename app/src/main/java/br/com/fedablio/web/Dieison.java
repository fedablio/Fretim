package br.com.fedablio.web;

import org.json.JSONObject;
import br.com.fedablio.model.Google;

public class Dieison {

    public Google pegaTrajeto(String o, String d) {
        try {
            String origem = o.replace(" ", "%20");
            String destino = d.replace(" ", "%20");
            String jsonTexto = new Servidor().informacao(origem, destino);
            String origem_ok1 = new JSONObject(jsonTexto).get("origin_addresses").toString();
            String destino_ok1 = new JSONObject(jsonTexto).get("destination_addresses").toString();
            String origem_ok2 = origem_ok1.replace("[\"", "");
            String origem_ok3 = origem_ok2.replace("\"]", "");
            String destino_ok2 = destino_ok1.replace("[\"", "");
            String destino_ok3 = destino_ok2.replace("\"]", "");
            JSONObject distance = new JSONObject(jsonTexto).getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance");
            int distancia_ok = Integer.parseInt(distance.get("value").toString());
            JSONObject duration = new JSONObject(jsonTexto).getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration");
            int duracao_ok = Integer.parseInt(duration.get("value").toString());
            Google goo = new Google();
            goo.setOrigem(origem_ok3);
            goo.setDestino(destino_ok3);
            goo.setDuracao(duracao_ok);
            goo.setDistancia(distancia_ok);
            return goo;
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }
}
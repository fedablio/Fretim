package br.com.fedablio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import br.com.fedablio.ftm.R;
import br.com.fedablio.model.Corrida;

public class CorridaAdapter extends ArrayAdapter<Corrida> {

    private final Context context;
    private final ArrayList<Corrida> elementos;

    public CorridaAdapter(Context context, ArrayList<Corrida> elementos) {
        super(context, R.layout.linha, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);
        TextView tvId = (TextView) rowView.findViewById(R.id.tvIdLinha);
        TextView tvOrigem = (TextView) rowView.findViewById(R.id.tvOrigemLinha);
        TextView tvDestino = (TextView) rowView.findViewById(R.id.tvDestinoLinha);
        TextView tvDistancia = (TextView) rowView.findViewById(R.id.tvDistanciaLinha);
        TextView tvData = (TextView) rowView.findViewById(R.id.tvDataLinha);
        TextView tvHora = (TextView) rowView.findViewById(R.id.tvHoraLinha);
        TextView tvValor = (TextView) rowView.findViewById(R.id.tvValorLinha);
        tvId.setText(elementos.get(position).getId_corrida());
        tvOrigem.setText(elementos.get(position).getOrigem_corrida());
        tvDestino.setText(elementos.get(position).getDestino_corrida());
        tvDistancia.setText(String.valueOf(elementos.get(position).getDistancia_corrida()) + " Km");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tvData.setText(String.valueOf(sdf.format(elementos.get(position).getData_corrida())));
        tvHora.setText(String.valueOf(elementos.get(position).getHora_corrida()));
        tvValor.setText("R$ " + String.valueOf(elementos.get(position).getValor_corrida()));
        return rowView;
    }

}
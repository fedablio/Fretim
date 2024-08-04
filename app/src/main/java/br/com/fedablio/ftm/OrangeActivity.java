package br.com.fedablio.ftm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrangeActivity extends Activity {

    private String _PRO_ = "";
    private String _ORI_ = "";
    private String _DES_ = "";
    private String _DIS_ = "";
    private String _VAL_ = "";
    private TextView tvProtocolo;
    private TextView tvOrigem;
    private TextView tvDestino;
    private TextView tvDistancia;
    private TextView tvValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orange);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            _PRO_ = bundle.getString("_PRO_");
            _ORI_ = bundle.getString("_ORI_");
            _DES_ = bundle.getString("_DES_");
            _DIS_ = bundle.getString("_DIS_");
            _VAL_ = bundle.getString("_VAL_");
        }
        tvProtocolo = (TextView) findViewById(R.id.tvProtocoloOrange);
        tvOrigem = (TextView) findViewById(R.id.tvOrigemOrange);
        tvDestino = (TextView) findViewById(R.id.tvDestinoOrange);
        tvDistancia = (TextView) findViewById(R.id.tvDistanciaOrange);
        tvValor = (TextView) findViewById(R.id.tvValorOrange);
        tvProtocolo.setText(_PRO_);
        tvOrigem.setText(_ORI_);
        tvDestino.setText(_DES_);
        tvDistancia.setText(_DIS_);
        tvValor.setText(_VAL_);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
    }

    public void compartilhar(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        String cp1 = "**SIMULAÇÃO DO FRETE**\n";
        String cp2 = "Protocolo:\n" + tvProtocolo.getText().toString() + "\n";
        String cp3 = "Origem:\n" + tvOrigem.getText().toString() + "\n";
        String cp4 = "Destino:\n" + tvDestino.getText().toString() + "\n";
        String cp5 = "Distância (Km):\n" + tvDistancia.getText().toString() + "\n";
        String cp6 = "Valor (R$):\n" + tvValor.getText().toString();
        intent.putExtra(Intent.EXTRA_TEXT, cp1 + cp2 + cp3 + cp4 + cp5 + cp6);
        intent.setType("text/plain");
        Intent.createChooser(intent, "Compartilhar via");
        startActivity(intent);
    }

    public void nova(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fretim");
        builder.setMessage(Html.fromHtml("<font color='#FFFF00'>Tem certeza?</font>"));
        builder.setCancelable(false);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(OrangeActivity.this, AppleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        try {
            Resources resources = dialog.getContext().getResources();
            int alertTitleId = resources.getIdentifier("alertTitle", "id", "android");
            TextView alertTitle = (TextView) dialog.getWindow().getDecorView().findViewById(alertTitleId);
            alertTitle.setTextColor(Color.parseColor("#FDF5E6"));
            int titleDividerId = resources.getIdentifier("titleDivider", "id", "android");
            View titleDivider = dialog.getWindow().getDecorView().findViewById(titleDividerId);
            titleDivider.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Button pos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pos.setBackgroundColor(Color.parseColor("#C0C0C0"));
        pos.setTextColor(Color.parseColor("#FFFFFF"));
        Button neg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        neg.setBackgroundColor(Color.parseColor("#C0C0C0"));
        neg.setTextColor(Color.parseColor("#FFFFFF"));
    }

}
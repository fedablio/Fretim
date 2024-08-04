package br.com.fedablio.ftm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import br.com.fedablio.dao.CorridaDAO;
import br.com.fedablio.dao.MotociclistaDAO;
import br.com.fedablio.locale.GpsTracker;
import br.com.fedablio.model.Corrida;
import br.com.fedablio.web.Dieison;

public class AppleActivity extends Activity {

    private static String id_global;
    private static String id_sessao;
    private static String complementa_protocolo;
    private static String origem_retorno;
    private static String destino_retorno;
    private static double distancia_retorno;
    private static double valor_retorno;
    private static String ERRO_MENSAGEM = "";
    private static String logradouro_motociclista;
    private static String numero_motociclista;
    private static String bairro_motociclista;
    private static String cidade_motociclista;
    private static String uf_motociclista;
    private static int valor = 0;
    private static String latitude;
    private static String longitude;
    private EditText etOrigem;
    private EditText etDestino;
    private CheckBox cbOrigemCompleta;
    private GpsTracker gpsTracker;
    private ProgressDialog progressDialog1;
    private ProgressDialog progressDialog2;

    public static boolean temConexao(Context contexto) {
        boolean resultado = false;
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())) {
            resultado = true;
        }
        return resultado;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences sp = getSharedPreferences("pado", Context.MODE_PRIVATE);
        id_sessao = sp.getString("_ID_MOTOCICLISTA_SESSAO_", "");
        etOrigem = (EditText) findViewById(R.id.etOrigemApple);
        etDestino = (EditText) findViewById(R.id.etDestinoApple);
        cbOrigemCompleta = (CheckBox) findViewById(R.id.cbOrigemApple);
        gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            latitude = String.valueOf(gpsTracker.getLocation().getLatitude());
            longitude = String.valueOf(gpsTracker.getLocation().getLongitude());
        } else {
            latitude = "N/A";
            longitude = "N/A";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1 = ProgressDialog.show(AppleActivity.this, "", "");
                    }
                });
                logradouro_motociclista = new MotociclistaDAO().buscaLogradouro(id_sessao);
                numero_motociclista = new MotociclistaDAO().buscaNumero(id_sessao);
                bairro_motociclista = new MotociclistaDAO().buscaBairro(id_sessao);
                cidade_motociclista = new MotociclistaDAO().buscaCidade(id_sessao);
                uf_motociclista = new MotociclistaDAO().buscaUf(id_sessao);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                        cbOrigemCompleta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (cbOrigemCompleta.isChecked()) {
                                    etOrigem.setText(logradouro_motociclista + ", " + numero_motociclista + ", " + bairro_motociclista + ", " + cidade_motociclista + ", " + uf_motociclista);
                                    etOrigem.setEnabled(false);
                                } else {
                                    etOrigem.setText("");
                                    etOrigem.setEnabled(true);
                                }
                            }
                        });
                    }
                });
            }
        }).start();
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
    }

    public void inserir_apple(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog2 = ProgressDialog.show(AppleActivity.this, "", "");
                    }
                });
                if (temConexao(AppleActivity.this)) {
                    String nome1 = etOrigem.getText().toString();
                    String nome2 = etDestino.getText().toString();
                    if (!nome1.equals("") && !nome2.equals("")) {
                        try {
                            double distancia_servidor = new Dieison().pegaTrajeto(nome1, nome2).getDistancia();
                            double divisor = 1000;
                            double quebrado = distancia_servidor / divisor;
                            BigDecimal bd = new BigDecimal(quebrado).setScale(1, RoundingMode.HALF_EVEN);
                            double dist_dou = (bd.doubleValue());
                            id_global = new CorridaDAO().protocolo();
                            Corrida cor = new Corrida();
                            complementa_protocolo = id_sessao.toUpperCase();
                            cor.setId_corrida(complementa_protocolo + id_global);
                            cor.setId_motociclista(id_sessao);
                            String origem_3 = new Dieison().pegaTrajeto(nome1, nome2).getOrigem();
                            cor.setOrigem_corrida(origem_3);
                            String destino_3 = new Dieison().pegaTrajeto(nome1, nome2).getDestino();
                            cor.setDestino_corrida(destino_3);
                            cor.setDistancia_corrida(dist_dou);
                            cor.setData_corrida(new Date(new java.util.Date().getTime()));
                            cor.setHora_corrida(new Time(new java.util.Date().getTime()));
                            double preco_km = new MotociclistaDAO().buscaPrecoquilo(id_sessao);
                            double prec_min = new MotociclistaDAO().buscaPrecomini(id_sessao);
                            double resultado = dist_dou * preco_km;
                            if (resultado < prec_min) {
                                resultado = prec_min;
                            }
                            cor.setValor_corrida(resultado);
                            cor.setLat_corrida(latitude);
                            cor.setLon_corrida(longitude);
                            new CorridaDAO().inserir(cor);
                            origem_retorno = new CorridaDAO().buscarOrigem(complementa_protocolo + id_global);
                            destino_retorno = new CorridaDAO().buscarDestino(complementa_protocolo + id_global);
                            distancia_retorno = new CorridaDAO().buscarDistancia(complementa_protocolo + id_global);
                            valor_retorno = new CorridaDAO().buscarValor(complementa_protocolo + id_global);
                            valor = 1;
                        } catch (Exception erro) {
                            ERRO_MENSAGEM = "Endereço(s) de origem e/ou de destino não localizado(s).";
                        }
                    } else {
                        ERRO_MENSAGEM = "Há campo(s) em branco.";
                    }
                } else {
                    ERRO_MENSAGEM = "Sem conexão com a internet.";
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (valor == 1) {
                            progressDialog2.dismiss();
                            Intent intent = new Intent(AppleActivity.this, OrangeActivity.class);
                            intent.putExtra("_PRO_", complementa_protocolo + id_global);
                            intent.putExtra("_ORI_", origem_retorno);
                            intent.putExtra("_DES_", destino_retorno);
                            intent.putExtra("_DIS_", String.valueOf(distancia_retorno));
                            intent.putExtra("_VAL_", String.valueOf(valor_retorno));
                            startActivity(intent);
                            finish();
                            valor = 0;
                        } else {
                            if (!ERRO_MENSAGEM.equals("")) {
                                progressDialog2.dismiss();
                                Toast.makeText(AppleActivity.this, ERRO_MENSAGEM, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void limpar_apple(View view) {
        etOrigem.setText(null);
        if (cbOrigemCompleta.isChecked()) {
            cbOrigemCompleta.toggle();
        }
        etOrigem.setEnabled(true);
        etDestino.setText(null);
        etOrigem.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_apple, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnAdministracaoApple) {
            Intent intent = new Intent(AppleActivity.this, PearActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.mnSairApple) {
            finishAffinity();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }
}
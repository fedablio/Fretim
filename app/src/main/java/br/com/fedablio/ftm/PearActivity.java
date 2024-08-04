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
import android.widget.EditText;
import android.widget.Toast;
import br.com.fedablio.dao.MotociclistaDAO;
import br.com.fedablio.model.Motociclista;

public class PearActivity extends Activity {

    private static String ERRO_MENSAGEM = "";
    private static int valor = 0;
    private static String id_sessao;
    private EditText etUsuario;
    private EditText etSenha;
    private ProgressDialog progressDialog1;

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
        setContentView(R.layout.activity_pear);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences sp = getSharedPreferences("pado", Context.MODE_PRIVATE);
        id_sessao = sp.getString("_ID_MOTOCICLISTA_SESSAO_", "");
        etUsuario = (EditText) findViewById(R.id.etUserPear);
        etSenha = (EditText) findViewById(R.id.etPassPear);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
    }

    public void autenticar_pear(View view) {
        MotociclistaDAO mod = new MotociclistaDAO();
        Motociclista mot = new Motociclista();
        String usuario = etUsuario.getText().toString();
        String senha = etSenha.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1 = ProgressDialog.show(PearActivity.this, "", "");
                    }
                });
                if (temConexao(PearActivity.this)) {
                    if (!usuario.equals("") && !senha.equals("")) {
                        mot.setId_motociclista(id_sessao);
                        mot.setUser_motociclista(usuario);
                        mot.setPass_motociclista(senha);
                        if (mod.autenticar(mot)) {
                            valor = 1;
                        } else {
                            ERRO_MENSAGEM = "Usuário e/ou senha inválido(s).";
                        }
                    } else {
                        ERRO_MENSAGEM = "Há campos em branco.";
                    }
                } else {
                    ERRO_MENSAGEM = "Sem conexão com a internet.";
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (valor == 1) {
                            progressDialog1.dismiss();
                            Intent intent = new Intent(PearActivity.this, CocoaActivity.class);
                            startActivity(intent);
                            finish();
                            valor = 0;
                        } else {
                            if (!ERRO_MENSAGEM.equals("")) {
                                progressDialog1.dismiss();
                                Toast.makeText(PearActivity.this, ERRO_MENSAGEM, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void limpar_pear(View view) {
        etUsuario.setText(null);
        etSenha.setText(null);
        etUsuario.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnVoltarPear) {
            Intent intent = new Intent(PearActivity.this, AppleActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
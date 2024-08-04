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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.fedablio.dao.MotociclistaDAO;
import br.com.fedablio.model.Motociclista;

public class GuavaActivity extends Activity {

    private static String id_sessao;
    private static String senha_motociclista;
    private static String email_motociclista;
    private static String telefone_motociclista;
    private static String logradouro_motociclista;
    private static String numero_motociclista;
    private static String bairro_motociclista;
    private static String cidade_motociclista;
    private static String uf_motociclista;
    private static double km_motociclista;
    private static double mini_motociclista;
    private static String ERRO_MENSAGEM = "";
    private static int valor = 0;
    private EditText etId;
    private EditText etSenha;
    private EditText etEmail;
    private EditText etTelefone;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etBairro;
    private EditText etCidade;
    private EditText etUf;
    private EditText etKm;
    private EditText etMinimo;
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
        setContentView(R.layout.activity_guava);
        SharedPreferences sp = getSharedPreferences("pado", Context.MODE_PRIVATE);
        id_sessao = sp.getString("_ID_MOTOCICLISTA_SESSAO_", "");
        etId = (EditText) findViewById(R.id.etIdGuava);
        etSenha = (EditText) findViewById(R.id.etSenhaMotociclistaGuava);
        etEmail = (EditText) findViewById(R.id.etEmailGuava);
        etTelefone = (EditText) findViewById(R.id.etTelefoneGuava);
        etLogradouro = (EditText) findViewById(R.id.etLogradouroGuava);
        etNumero = (EditText) findViewById(R.id.etNumeroGuava);
        etBairro = (EditText) findViewById(R.id.etBairroGuava);
        etCidade = (EditText) findViewById(R.id.etCidadeGuava);
        etUf = (EditText) findViewById(R.id.etUfGuava);
        etKm = (EditText) findViewById(R.id.etPrecoKiloGuava);
        etMinimo = (EditText) findViewById(R.id.etPrecoMiniGuava);
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1 = ProgressDialog.show(GuavaActivity.this, "", "");
                    }
                });
                senha_motociclista = new MotociclistaDAO().buscaPass(id_sessao);
                email_motociclista = new MotociclistaDAO().buscaEmail(id_sessao);
                telefone_motociclista = new MotociclistaDAO().buscaTelefone(id_sessao);
                logradouro_motociclista = new MotociclistaDAO().buscaLogradouro(id_sessao);
                numero_motociclista = new MotociclistaDAO().buscaNumero(id_sessao);
                bairro_motociclista = new MotociclistaDAO().buscaBairro(id_sessao);
                cidade_motociclista = new MotociclistaDAO().buscaCidade(id_sessao);
                uf_motociclista = new MotociclistaDAO().buscaUf(id_sessao);
                km_motociclista = new MotociclistaDAO().buscaPrecoquilo(id_sessao);
                mini_motociclista = new MotociclistaDAO().buscaPrecomini(id_sessao);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                        etId.setText(id_sessao);
                        etSenha.setText(senha_motociclista);
                        etEmail.setText(email_motociclista);
                        etTelefone.setText(telefone_motociclista);
                        etLogradouro.setText(logradouro_motociclista);
                        etNumero.setText(numero_motociclista);
                        etBairro.setText(bairro_motociclista);
                        etCidade.setText(cidade_motociclista);
                        etUf.setText(uf_motociclista);
                        etKm.setText(String.valueOf(km_motociclista));
                        etMinimo.setText(String.valueOf(mini_motociclista));
                    }
                });
            }
        }).start();
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
    }

    public void alterar_guava(View view) {
        String senha = etSenha.getText().toString();
        String email = etEmail.getText().toString();
        String telefone = etTelefone.getText().toString();
        String logradouro = etLogradouro.getText().toString();
        String numero = etNumero.getText().toString();
        String bairro = etBairro.getText().toString();
        String cidade = etCidade.getText().toString();
        String uf = etUf.getText().toString();
        String km = etKm.getText().toString();
        String mini = etMinimo.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog2 = ProgressDialog.show(GuavaActivity.this, "", "");
                    }
                });
                if (temConexao(GuavaActivity.this)) {
                    if (!senha.equals("") && !email.equals("") && !telefone.equals("") && !km.equals("") && !mini.equals("")) {
                        if (Double.parseDouble(km) > 0) {
                            if (senha.length() > 3) {
                                Motociclista mot = new Motociclista();
                                MotociclistaDAO mod = new MotociclistaDAO();
                                mot.setPass_motociclista(senha);
                                mot.setEmail_motociclista(email);
                                mot.setTelefone_motociclista(telefone);
                                mot.setLogradouro_motociclista(logradouro);
                                mot.setNumero_motociclista(numero);
                                mot.setBairro_motociclista(bairro);
                                mot.setCidade_motociclista(cidade);
                                mot.setUf_motociclista(uf.toUpperCase());
                                mot.setPrecoquilo_motociclista(Double.parseDouble(km));
                                mot.setPrecimini_motociclista(Double.parseDouble(mini));
                                mot.setId_motociclista(id_sessao);
                                mod.alterar(mot);
                                valor = 1;
                            } else {
                                ERRO_MENSAGEM = "A senha deve ter pelo menos 4 caracteres.";
                            }
                        } else {
                            ERRO_MENSAGEM = "O Preço por Km deve ser maior que 0 (zero).";
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
                            progressDialog2.dismiss();
                            Intent intent = new Intent(GuavaActivity.this, CocoaActivity.class);
                            startActivity(intent);
                            finish();
                            valor = 0;
                        } else {
                            if (!ERRO_MENSAGEM.equals("")) {
                                progressDialog2.dismiss();
                                Toast.makeText(GuavaActivity.this, ERRO_MENSAGEM, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void limpar_guava(View view) {
        etEmail.setText(null);
        etTelefone.setText(null);
        etLogradouro.setText(null);
        etNumero.setText(null);
        etBairro.setText(null);
        etCidade.setText(null);
        etUf.setText(null);
        etKm.setText(null);
        etMinimo.setText(null);
        etLogradouro.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_guava, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnVoltarGuava) {
            Intent intent = new Intent(GuavaActivity.this, CocoaActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
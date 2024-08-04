package br.com.fedablio.ftm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import br.com.fedablio.dao.MotociclistaDAO;

public class MainActivity extends Activity {

    private static String ERRO_MENSAGEM = "";
    private static int valor = 0;
    private EditText etIdMotociclista;
    private static final String SITUACAO = "A";
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
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        etIdMotociclista = (EditText) findViewById(R.id.etIdMotociclistaMain);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
    }

    public void executar_main(View view) {
        String entrada = etIdMotociclista.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1 = ProgressDialog.show(MainActivity.this, "", "");
                    }
                });
                if (temConexao(MainActivity.this)) {
                    if (!entrada.equals("")) {
                        if (new MotociclistaDAO().acessar(entrada, SITUACAO)) {
                            SharedPreferences sp = getSharedPreferences("pado", Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString("_ID_MOTOCICLISTA_SESSAO_", entrada);
                            ed.commit();
                            valor = 1;
                        } else {
                            ERRO_MENSAGEM = "ID não encontrado.";
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
                            Intent intent = new Intent(MainActivity.this, AppleActivity.class);
                            startActivity(intent);
                            finish();
                            valor = 0;
                        } else {
                            if (!ERRO_MENSAGEM.equals("")) {
                                Toast.makeText(MainActivity.this, ERRO_MENSAGEM, Toast.LENGTH_LONG).show();
                                progressDialog1.dismiss();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnSobreMain) {
            Intent intent = new Intent(MainActivity.this, CherryActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
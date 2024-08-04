package br.com.fedablio.ftm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import br.com.fedablio.adapter.CorridaAdapter;
import br.com.fedablio.dao.CorridaDAO;
import br.com.fedablio.model.Corrida;

public class PlumActivity extends Activity {

    private static String id_sessao;
    private static String ERRO_MENSAGEM = "";
    private static int valor = 0;
    Calendar myCalendar = Calendar.getInstance();
    private EditText etProtocolo;
    private EditText etOrigem;
    private EditText etDestino;
    private EditText etData1;
    private EditText etData2;
    private RadioButton rbPesquisaUm;
    private RadioButton rbPesquisaDois;
    private RadioButton rbPesquisaTres;
    private ArrayAdapter adapter;
    private ListView lista;
    private ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plum);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista = (ListView) findViewById(R.id.lvCorridaPlum);
        SharedPreferences sp = getSharedPreferences("pado", Context.MODE_PRIVATE);
        id_sessao = sp.getString("_ID_MOTOCICLISTA_SESSAO_", "");
        rbPesquisaUm = (RadioButton) findViewById(R.id.rbPesquisaUm);
        rbPesquisaDois = (RadioButton) findViewById(R.id.rbPesquisaDois);
        rbPesquisaTres = (RadioButton) findViewById(R.id.rbPesquisaTres);
        etProtocolo = (EditText) findViewById(R.id.etProtocoloPlum);
        etOrigem = (EditText) findViewById(R.id.etOrigemPlum);
        etDestino = (EditText) findViewById(R.id.etDestinoPlum);
        etData1 = (EditText) findViewById(R.id.etData1Plum);
        etData2 = (EditText) findViewById(R.id.etData2Plum);
        etData1.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
                    etData1.setText(sdf.format(myCalendar.getTime()));
                }
            };

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlumActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etData2.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
                    etData2.setText(sdf.format(myCalendar.getTime()));
                }
            };

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlumActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
    }

    public void executar_plum(View view) {
        String proto = etProtocolo.getText().toString();
        String ori = etOrigem.getText().toString();
        String des = etDestino.getText().toString();
        String dt1 = etData1.getText().toString();
        String dt2 = etData2.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1 = ProgressDialog.show(PlumActivity.this, "", "");
                    }
                });
                if (rbPesquisaUm.isChecked()) {
                    ArrayList<Corrida> listaProto = new CorridaDAO().listarTodasId(id_sessao, proto);
                    adapter = new CorridaAdapter(PlumActivity.this, listaProto);
                    valor = 1;
                } else if (rbPesquisaDois.isChecked()) {
                    ArrayList<Corrida> listaOrig = new CorridaDAO().listarTodasOrigem(id_sessao, ori);
                    adapter = new CorridaAdapter(PlumActivity.this, listaOrig);
                    valor = 1;
                } else if (rbPesquisaTres.isChecked()) {
                    ArrayList<Corrida> listaDest = new CorridaDAO().listarTodasDestino(id_sessao, des);
                    adapter = new CorridaAdapter(PlumActivity.this, listaDest);
                    valor = 1;
                } else {
                    if (!dt1.equals("") && !dt2.equals("")) {
                        ArrayList<Corrida> listaData = new CorridaDAO().listarTodasData(id_sessao, dt1, dt2);
                        adapter = new CorridaAdapter(PlumActivity.this, listaData);
                        valor = 1;
                    } else {
                        ERRO_MENSAGEM = "As datas devem ser preenchidas.";
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (valor == 1) {
                            progressDialog1.dismiss();
                            ListView lista = (ListView) findViewById(R.id.lvCorridaPlum);
                            lista.setAdapter(adapter);
                            valor = 0;
                        } else {
                            if (!ERRO_MENSAGEM.equals("")) {
                                progressDialog1.dismiss();
                                Toast.makeText(PlumActivity.this, ERRO_MENSAGEM, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void limpar_plum(View view) {
        etProtocolo.setText(null);
        etOrigem.setText(null);
        etDestino.setText(null);
        etData1.setText(null);
        etData2.setText(null);
        rbPesquisaUm.setChecked(true);
        lista.setAdapter(null);
        etProtocolo.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_plum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnVoltarPlum) {
            Intent intent = new Intent(PlumActivity.this, CocoaActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
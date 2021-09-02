package com.example.prova1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class ReservarMesaActivy extends AppCompatActivity {
    private RecyclerView rvmesas;
    private MesaAdapter adapter;
    SharedPreferences preferences;

    private Button btnLiberar;
    private EditText editLiberar;
    private  Button btnSalvar;
    private Button btnReservarTodas;
    private Button btnConfig;

    private ArrayList<Mesa> listaMesas = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_mesas);

        rvmesas = findViewById(R.id.rvMesas);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        instanciarMesas();

        rvmesas.setHasFixedSize(true);
        rvmesas.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new MesaAdapter(listaMesas, preferences);
        rvmesas.setAdapter(adapter);

        btnLiberar = findViewById(R.id.btnLiberar);
        editLiberar = findViewById(R.id.editLiberar);

        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> salvarMesas());

        btnReservarTodas = findViewById(R.id.btnReservarTodas);

        btnConfig = findViewById(R.id.btnConfig);

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservarMesaActivy.this, PreferenciasActivity.class));
            }
        });

        btnReservarTodas.setOnClickListener(v -> reservarTodas());

        btnLiberar.setOnClickListener(v -> {
            int posicao = Integer.valueOf(editLiberar.getText().toString()) - 1;
            if(!listaMesas.get(posicao).getIsReserved()){
                Toast.makeText(ReservarMesaActivy.this, "Mesa não reservada a mesa " + (posicao + 1 ) + " está livre para reserva", Toast.LENGTH_SHORT).show();
                return;
            }
            listaMesas.get(posicao).setReserved(false);
            adapter.notifyItemChanged(posicao);
        });

    }

    public void instanciarMesas() {
        for (int i = 0; i < 9; i++) {
            Mesa mesa = new Mesa();
            mesa.setReserved(preferences.getBoolean("mesa" + i, false));
            listaMesas.add(mesa);
        }
    }

    public void salvarMesas() {
        SharedPreferences.Editor editor = preferences.edit();

        for(int i = 0; i < listaMesas.size(); i++) {
            editor.putBoolean("mesa" + i, listaMesas.get(i).getIsReserved());
        }
        editor.apply();
    }

    public void reservarTodas() {
        boolean isReserved = true;

        for (Mesa mesa : listaMesas) {
            if(!mesa.getIsReserved()) {
                isReserved = false;
            }
        }

        if (isReserved) {
            Toast.makeText(this, "Operação inválida. Todas as mesas já possuem reserva", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < listaMesas.size(); i++) {
            listaMesas.get(i).setReserved(true);
        }
        adapter.notifyDataSetChanged();
    }
}

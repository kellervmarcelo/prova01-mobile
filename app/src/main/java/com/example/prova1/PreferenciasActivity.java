package com.example.prova1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PreferenciasActivity extends AppCompatActivity {
    EditText corRes;
    EditText corLivre;
    Button btnSalvar;
    Button btnDefault;
    SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        corRes = findViewById(R.id.corRes);
        corLivre = findViewById(R.id.corLivre);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnDefault = findViewById(R.id.btnDefault);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCores();
                finish();
            }
        });

        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coresPadrao();
                finish();
            }
        });
    }

    public void salvarCores() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("corRes", corRes.getText().toString());
        editor.putString("corLivre", corLivre.getText().toString());
        editor.apply();
    }

    public void coresPadrao() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("corRes", "");
        editor.putString("corLivre", "");
        editor.apply();
    }
}

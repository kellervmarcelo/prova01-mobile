package com.example.prova1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList mesas = new ArrayList<Mesa>();
    private EditText editEmail, editSenha;
    private Button btnContinuar;
    private Mesa mesa1 = new Mesa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnContinuar = findViewById(R.id.btnSignIn);

        btnContinuar.setOnClickListener(v -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("NOME_USUARIO", editEmail.getText().toString());
            editor.apply();

            Intent intent = new Intent(MainActivity.this, ReservarMesaActivy.class);

            MainActivity.this.startActivity(intent);

            final String usernameString = editEmail.getText().toString();
            final String passwordString = editSenha.getText().toString();

            if (usernameString.isEmpty()) {
                Toast.makeText(MainActivity.this, "Nome de usuário não pode ser vazio", Toast.LENGTH_LONG).show();
                return;
            }

            if (passwordString.isEmpty()) {
                Toast.makeText(MainActivity.this, "Senha não pode ser vazia", Toast.LENGTH_LONG).show();
                return;
            }

            if (
                    (usernameString.equals("Administrador") && passwordString.equals("Administrador")) ||
                    (usernameString.equals("Adm") && passwordString.equals("Adm123")) ||
                    (usernameString.equals("Administrator") && passwordString.equals("Que3B1eng4ElT0r0")) ||
                    (usernameString.equals("Root") && passwordString.equals("pr0m1uscu0"))
            ) {
                startActivity(new Intent(MainActivity.this, ReservarMesaActivy.class));
                return;
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editEmail.setText(preferences.getString("NOME_USUARIO", ""));
    }
}
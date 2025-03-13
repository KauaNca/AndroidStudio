package com.example.senac;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CadastroSimples extends AppCompatActivity {
    Button btCadastrar;
    EditText nome, senha;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_simples);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btCadastrar = findViewById(R.id.btCadastrar);
        nome = findViewById(R.id.nome);
        senha = findViewById(R.id.senha);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection con = null;
                PreparedStatement stmt = null;
                try {
                    con = Conexao.conectar();
                    stmt = con.prepareStatement("INSERT INTO login(usuario,senha) VALUES(?,?);");
                    stmt.setString(1, nome.getText().toString());
                    stmt.setString(2, senha.getText().toString());
                    stmt.execute();
                    Intent t2 = new Intent(CadastroSimples.this, MainActivity.class);
                    startActivity(t2);
                } catch (Exception e) {
                    System.out.println("ERRO NO BANCO DE DADOS: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        if (stmt != null) stmt.close();
                        if (con != null) con.close();
                    } catch (Exception e) {
                        System.out.println("ERRO AO FECHAR RECURSOS: " + e.getMessage());
                    }
                }
            }
        });
    }
}

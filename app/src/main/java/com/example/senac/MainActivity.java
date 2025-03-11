package com.example.senac;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Button mostrar;
    ArrayList<String> dados;
    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mostrar = findViewById(R.id.btLista);
        lista = findViewById(R.id.lista);
        lista.setVisibility(View.GONE);
        dados = new ArrayList<>();

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection con = Conexao.conectar();
                    PreparedStatement stmt = con.prepareStatement("SELECT descricao,preco FROM produto");
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()){
                        dados.add("Produto: " + rs.getString("descricao") + " Preço: " + rs.getString("preco"));
                    }
                    adaptador = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, dados);
                    lista.setAdapter(adaptador);
                    lista.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
        });
    }
}

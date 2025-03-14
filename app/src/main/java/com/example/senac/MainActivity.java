package com.example.senac;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    String id;
    Button mostrar,atualizar;
    EditText textoLinha;
    ArrayList<String> dados;
    ArrayAdapter<String> adaptador;

    public void atualizarTabela(View v){
        try {
            Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement("UPDATE login SET usuario = ? WHERE id = ?");
            stmt.setString(1,textoLinha.getText().toString());
            stmt.setString(1,id);
            stmt.executeUpdate();
            lista.setVisibility(View.GONE);
            textoLinha.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    textoLinha = findViewById(R.id.editar);
        mostrar = findViewById(R.id.btLista);
        lista = findViewById(R.id.lista);
        atualizar = findViewById(R.id.btAtualizar);
        lista.setVisibility(View.GONE);
        dados = new ArrayList<>();

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection con = Conexao.conectar();
                    PreparedStatement stmt = con.prepareStatement("SELECT id,usuario FROM login");
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()){
                        dados.add("ID: " + rs.getString("id") + " Usuário: " + rs.getString("usuario"));
                    }
                    adaptador = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, dados);
                    lista.setAdapter(adaptador);
                    lista.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String texto = (String) adaptador.getItem(position);
                String[] partes = texto.split(" ");
                textoLinha.setText(partes[3]);
                
            }
        });
    }

}


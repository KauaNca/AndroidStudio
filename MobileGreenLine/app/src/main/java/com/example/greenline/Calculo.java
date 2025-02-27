package com.example.greenline;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calculo extends AppCompatActivity {
    EditText gasolina,etanol;
    Button calcular;
    TextView textoArea;

    public class NumberInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isDigit(source.charAt(i)) && source.charAt(i) != '.') {
                    return "";
                }
            }
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gasolina = findViewById(R.id.gasolina);
        etanol = findViewById(R.id.usuario);
        calcular = findViewById(R.id.calcular);
        textoArea = findViewById(R.id.resultado);
        gasolina.setFilters(new InputFilter[]{
                new NumberInputFilter()
        });
        etanol.setFilters(new InputFilter[]{
                new NumberInputFilter()
        });

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double calculo = Double.parseDouble(etanol.getText().toString())/Double.parseDouble(gasolina.getText().toString());
                if(calculo <=  0.7 ){
                    textoArea.setText(" Abasteça com Etanol");
                }
                else{
                    textoArea.setText(" Abasteça com gasolina");
                }
            }
        });
    }
}
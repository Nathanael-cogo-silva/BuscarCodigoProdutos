package com.example.buscarcodigoprodutos;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText editBuscar;
    Button botaoBuscar;
    TextView textoNome;
    TextView textoCodigo;
    TextView textoTara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editBuscar = findViewById(R.id.editBuscar);
        botaoBuscar = findViewById(R.id.botaoBuscar);
        textoNome = findViewById(R.id.textoNome);
        textoCodigo = findViewById(R.id.textoCodigo);
        textoTara = findViewById(R.id.textoTara);

        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buscaProdutos(editBuscar.getText().toString());


            }
        });




    }

    private void buscaProdutos(String produtoDigitado) {
        db.collection("produtos" )
                .whereEqualTo("nome" , produtoDigitado)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                editBuscar.setText("");
                                textoNome.setText(document.get("nome").toString());
                                textoCodigo.setText(document.get("codigo").toString());
                                textoTara.setText(document.get("tara").toString());

                            }
                        } else {

                            editBuscar.setText("");
                            Toast.makeText(getApplicationContext(), "produto não cadastrado.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
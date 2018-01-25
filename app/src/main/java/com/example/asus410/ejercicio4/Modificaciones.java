package com.example.asus410.ejercicio4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Modificaciones extends AppCompatActivity {

    EditText nombre,telefono;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificaciones);
        bundle = getIntent().getExtras();
        nombre=(EditText)findViewById(R.id.etNombreModificacion);
        telefono=(EditText)findViewById(R.id.etTfnModificacion);
        nombre.setText(bundle.getString("NOMBRE"));
        telefono.setText(bundle.getString("TELEFONO"));

    }

    public void clicEditarContacto(View v){
        AdaptadorBD abd = new AdaptadorBD(this);

        abd.open();
        Cursor c = abd.getContacto(bundle.getLong("ID"));
        Long id = c.getLong(0);
        String nom = nombre.getText().toString();
        String tipo = c.getString(2);
        String tlf = telefono.getText().toString();

        Contactos con = new Contactos(id,nom,tipo,tlf);

        if(abd.actualizarContacto(con)){
            Toast.makeText(this,"Contacto actualizado correctamente",Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this,"No se ha podido modificar",Toast.LENGTH_SHORT).show();
        abd.close();
        finish();
    }
}

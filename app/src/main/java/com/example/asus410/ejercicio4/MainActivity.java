package com.example.asus410.ejercicio4;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private Bundle bundle;
    private Intent IntentConsulta, IntentEliminarContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button botonAdd = (Button) findViewById(R.id.btnAdd);
        final Button botonConsulta = (Button) findViewById(R.id.btnConsulta);
        final Button botonConsultaNombre = (Button) findViewById(R.id.btnConsultaNombre);

        bundle = new Bundle();

        IntentConsulta = new Intent(this, Consulta.class);
        final Intent IntentAdd = new Intent(this, AddContactos.class);
        IntentEliminarContacto = new Intent(this,ListaContactos.class);



        //evento onClick botón 'Añadir contactos'
        botonAdd.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {

                startActivity(IntentAdd);
            }
        });

        //evento onClick botón 'Consultar contactos'
        botonConsulta.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                bundle.putInt("CLAVE",0);
                IntentConsulta.putExtras(bundle);
                startActivity(IntentConsulta);
            }
        });

        botonConsultaNombre.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                bundle.putInt("CLAVE",1);
                IntentConsulta.putExtras(bundle);
                startActivity(IntentConsulta);
            }
        });
    }

    private void clickBorrar(View v) {

    }

    protected void clickConsultaNombre(View v){

        bundle.putInt("CLAVE",1);
        IntentConsulta.putExtras(bundle);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Consulta Contactos");

        builder.setMessage("Introduce el nombre")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: handle the OK
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/
    }

    public int recorre(String name){
        AdaptadorBD db = new AdaptadorBD(this);

        // abre la base de datos
        db.open();

        //obtiene todos los contactos de la BD
        Cursor cursor = db.getTodosContactos();

        while (cursor.moveToNext()){
            String nombre = cursor.getString(1);
            if (nombre.equalsIgnoreCase(name)){
                return cursor.getInt(0);
            }
        }
        return 0;
    }

    public void clikModificaciones(View v){
        bundle.putInt("CODIGO",0);
        IntentEliminarContacto.putExtras(bundle);
        startActivity(IntentEliminarContacto);
    }

    public void clikEliminarContacto(View v){
        bundle.putInt("CODIGO",1);
        IntentEliminarContacto.putExtras(bundle);
        startActivity(IntentEliminarContacto);
    }
}
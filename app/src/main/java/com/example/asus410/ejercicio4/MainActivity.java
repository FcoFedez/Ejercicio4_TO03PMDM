package com.example.asus410.ejercicio4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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

    public void clickBorrar(View v) {

        final Context context = v.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("BORRADO BASE DE DATOS");

        builder.setMessage("¿Esta seguro que desea borrar la base de datos?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       context.deleteDatabase ("dbagenda");
                        Toast.makeText(context,"Se ha borrado la base de datos dbagenda",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context,"Se ha cancelado el borrado",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    protected void clickConsultaNombre(View v){

        bundle.putInt("CLAVE",1);
        IntentConsulta.putExtras(bundle);

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
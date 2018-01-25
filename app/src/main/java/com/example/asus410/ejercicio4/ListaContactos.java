package com.example.asus410.ejercicio4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListaContactos extends Activity {

    private List lista;
    private Bundle bundle;
    private Contactos[] array;

    private AdaptadorBD db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        bundle = getIntent().getExtras();
        db = new AdaptadorBD(this);
        db.open();
        //lista = db.getNombresLista();



        lista = new ArrayList(db.getAllContactos());
        array = new Contactos[lista.size()];
        String[] aux = new String[lista.size()];
        lista.toArray(array);

        for(int i = 0; i< array.length;i++){

            aux[i]=array[i].getNombre();

        }
        db.close();


        final ListAdapter adaptador = new ArrayAdapter<String>(
                this,android.R.layout.simple_expandable_list_item_1,aux
        );
        ListView lv = (ListView)findViewById(R.id.list);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adaptador);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String t = adapterView.toString();

                String t= array[i].getNombre();

                if (bundle.getInt("CODIGO") == 1){
                    eliminaContacto(i,view,t);
                    finish();
                }else {
                    modificaContacto(view,i);
                }
            }
        });


    }

    public void eliminaContacto(int i, View view,String texto){
        db.open();

        Long num = new Long(array[i].getNumero());
        if(db.borrarContacto(num)) {

            Toast.makeText(view.getContext(), "Contacto "+texto+" borrado correctamente", Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    public void modificaContacto(View view, int pos){
        Intent i = new Intent(view.getContext(),Modificaciones.class);
        bundle.putLong("ID",array[pos].getNumero());
        bundle.putString("NOMBRE",array[pos].getNombre());
        bundle.putString("TELEFONO",array[pos].getTelefono());
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }


}

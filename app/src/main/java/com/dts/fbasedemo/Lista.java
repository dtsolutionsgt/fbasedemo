package com.dts.fbasedemo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.dts.base.clsClasses;
import com.dts.firebase.fbPBus;
import com.dts.ladapt.LA_View;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Lista extends PBase {

    private ListView listView;

    private LA_View adapter;

    private ArrayList<clsClasses.clsView> vitems= new ArrayList<clsClasses.clsView>();
    private clsClasses.clsView vitem;
    private clsClasses.clsBus item;

    private fbPBus fbb;
    private Runnable rnListItems;

    private int lastid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lista);

            super.InitBase();

            listView = findViewById(R.id.listView1);

            rnListItems = new Runnable() {
                public void run() {
                    processList();
                }
            };

            fbb=new fbPBus("PBUS/");

            fbb.fdt.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //String value = dataSnapshot.getValue(String.class);
                    toast("Registros actualizados");

                    Handler mtimer = new Handler();
                    Runnable mrunner=new Runnable() {
                        @Override
                        public void run() {
                            fbb.listItems(rnListItems);
                        }
                    };
                    mtimer.postDelayed(mrunner,200);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //toast("Actualizacion registros error : "+error.getMessage());
                }
            });

            fbb.listItems(rnListItems);
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }

    }

    //region Events

    public void doAdd(View view) {
        try {

            item=clsCls.new clsBus();

            lastid++;

            item.id=lastid;
            item.placa="Placa "+item.id;
            item.nombre="Bus "+item.id;
            item.barra="Placa "+item.id;
            item.activo=1;

            fbb.setItem(item.id+"",item);

            fbb.listItems(rnListItems);

        } catch (Exception e) {
            String ss=e.getMessage();
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    //endregion

    //region Main

    private void listItems() {
        try {
            vitems.clear();

            for (int i = 0; i <fbb.items.size(); i++) {

                if (fbb.items.get(i).activo==1) {
                    vitem = clsCls.new clsView();

                    vitem.pk = fbb.items.get(i).id;
                    vitem.f1 = fbb.items.get(i).nombre;

                    vitems.add(vitem);
                }
            }

            adapter=new LA_View(this,this,vitems);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    private void processList() {
        int lsize;

        try {

            if (fbb.items.size()==0) {
                lastid=0;
            } else {
                fbb.orderById();
                lsize=fbb.items.size()-1;
                lastid=fbb.items.get(lsize).id;

                for (int i = 0; i <fbb.items.size(); i++) {
                    if (fbb.items.get(i).id==gl.iditem) {
                        item=fbb.items.get(i);break;
                    }
                }
            }

            listItems();
        } catch (Exception e) {
            msgbox(new Object(){}.getClass().getEnclosingMethod().getName()+" . "+e.getMessage());
        }
    }

    //endregion

}
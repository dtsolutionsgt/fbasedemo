package com.dts.firebase;

import androidx.annotation.NonNull;

import com.dts.base.clsClasses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class fbPBus extends fbBase {

    public clsClasses.clsBus item,litem;
    public ArrayList<clsClasses.clsBus> items= new ArrayList<clsClasses.clsBus>();

    private clsClasses clsCls=new clsClasses();

    public fbPBus(String troot) {
        super(troot);
    }

    //region Public

    public void setItem(String node, clsClasses.clsBus item) {
        fdt=fdb.getReference(root+node);
        fdt.setValue(item);
    }

    public void getItem(String node,Runnable rnCallback) {

        fdb.getReference(root+node).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot res=task.getResult();

                    item=clsCls.new clsBus();

                    item.id=res.child("id").getValue(Integer.class);
                    item.nombre=res.child("nombre").getValue(String.class);
                    item.barra=res.child("barra").getValue(String.class);
                    item.placa=res.child("placa").getValue(String.class);
                    item.activo=res.child("activo").getValue(Integer.class);

                    callBack=rnCallback;
                    runCallBack();
                } else {
                    try {
                        throw new Exception(task.getException().getMessage());
                    } catch (Exception e) {}
                }
            }
        });
    }

    public void updateItem(String node, clsClasses.clsBus val) {
        fdb.getReference(root+node).child("id").setValue(val.id);
        fdb.getReference(root+node).child("nombre").setValue(val.nombre);
        fdb.getReference(root+node).child("activo").setValue(val.activo);
        fdb.getReference(root+node).child("barra").setValue(val.barra);
        fdb.getReference(root+node).child("placa").setValue(val.placa);
    }

    public void updateValue(String node, String value) {
        fdb.getReference(root+node).setValue(value);
    }

    public void removeValue(String node) {
        fdb.getReference(root+node).removeValue();
    }

    public void listItems(Runnable rnCallback) {
        try {
            items.clear();

            fdb.getReference(root).
            //fdb.getReference(root+node).orderByChild(field).equalTo(filter).
                    get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {

                        DataSnapshot res=task.getResult();

                        if (res.exists()) {

                            for (DataSnapshot snap : res.getChildren()) {
                                litem=clsCls.new clsBus();

                                litem.id=snap.child("id").getValue(Integer.class);
                                litem.nombre=snap.child("nombre").getValue(String.class);
                                litem.barra=snap.child("barra").getValue(String.class);
                                litem.placa=snap.child("placa").getValue(String.class);
                                litem.activo=snap.child("activo").getValue(Integer.class);

                                items.add(litem);
                            }
                        }

                        callBack=rnCallback;
                        runCallBack();
                    } else {
                        try {
                            throw new Exception(task.getException().getMessage());
                        } catch (Exception e) {}
                    }
                }
            });
        } catch (Exception e) {
            String ss=e.getMessage();
        }
    }

    public void orderById() {
        Collections.sort(items, new ItemComparatorId());
    }

    public void orderByNombre() {
        Collections.sort(items, new ItemComparatorNombre());
    }

    public void orderByBarra() {
        Collections.sort(items, new ItemComparatorBarra());
    }

    public void orderByPlaca() {
        Collections.sort(items, new ItemComparatorPlaca());
    }

    //endregion

    //region Private

    private  class ItemComparatorId implements Comparator<clsClasses.clsBus> {
        public int compare(clsClasses.clsBus left, clsClasses.clsBus right) {
             return Integer.compare(left.id, right.id);
        }
    }

    private  class ItemComparatorNombre implements Comparator<clsClasses.clsBus> {
        public int compare(clsClasses.clsBus left, clsClasses.clsBus right) {
            return left.nombre.compareTo(right.nombre);
        }
    }

    private  class ItemComparatorBarra implements Comparator<clsClasses.clsBus> {
        public int compare(clsClasses.clsBus left, clsClasses.clsBus right) {
            return left.barra.compareTo(right.barra);
        }
    }

    private  class ItemComparatorPlaca implements Comparator<clsClasses.clsBus> {
        public int compare(clsClasses.clsBus left, clsClasses.clsBus right) {
            return left.placa.compareTo(right.placa);
        }
    }

    //endregion

}

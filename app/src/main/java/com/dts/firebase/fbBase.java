package com.dts.firebase;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class fbBase {

    public FirebaseDatabase fdb;
    public DatabaseReference fdt;

    public Runnable callBack;

    public String value,root;

    public fbBase(String troot) {
        fdb = FirebaseDatabase.getInstance();
        root=troot;
        fdt=fdb.getReference(root);
        callBack=null;
    }

    public void runCallBack() {
        if (callBack==null) return;

        final Handler cbhandler = new Handler();
        cbhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.run();
            }
        }, 50);
    }

}

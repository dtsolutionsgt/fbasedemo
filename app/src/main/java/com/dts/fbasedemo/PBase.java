package com.dts.fbasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

import com.dts.base.MiscUtils;
import com.dts.base.appGlobals;
import com.dts.base.clsClasses;

import java.io.File;

public class PBase extends AppCompatActivity {

    public appGlobals gl;
    public MiscUtils mu;
    public clsClasses clsCls = new clsClasses();

    public int callback =0,mode;
    public int selid,selidx;
    public long fecha,stamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbase);
    }

    public void InitBase() {

        gl=((appGlobals) this.getApplication());

        mu=new MiscUtils(this,gl);

        callback =0;

    }

    //region Messages

    public void toast(String msg) {

        if (mu.emptystr(msg)) return;

        Toast toast= Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void toastlong(String msg) {

        if (mu.emptystr(msg)) return;

        Toast toast= Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void toast(double val) {
        this.toast(""+val);
    }

    public void msgbox(String msg){
        mu.msgbox(msg);
    }

    public void msgbox(int val){
        mu.msgbox(""+val);
    }

    public void msgbox(double val){
        mu.msgbox(""+val);
    }

    //endregion




}
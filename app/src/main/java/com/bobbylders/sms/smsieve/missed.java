package com.bobbylders.sms.smsieve;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class missed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LinearLayout tap = (LinearLayout)findViewById(R.id.num);
        createLists(tap, returnPhoneNumList(GetMessages()));
    }

    public ArrayList<String> returnPhoneNumList(List<String> allMsgsList) {

        int index1, index2, index3, index_body_end, index_body_start;
        String body;

        ArrayList<String> phoneNumList = new ArrayList<String>();

        for (String msg: allMsgsList) {

            index_body_start = msg.indexOf("body:") + 5;
            index_body_end = msg.indexOf("service_center");
            body = msg.substring(index_body_start, index_body_end);

            index1 = body.indexOf("missed call(s)");
            index2 = body.indexOf("+");
            index3 = body.indexOf(".", index2);

            if(index1 != -1 && index2 != -1) {

                String phone_num = body.substring(index2, index3);

                phoneNumList.add(phone_num);

            } //if

        } //for

        return phoneNumList;
    }

    public void createLists(LinearLayout container,List<String> toadd)
    {
        for(int i=0;i<toadd.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);
            temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            temp.setBackgroundColor(Color.RED);
            Button tempstr = new Button(this);
            tempstr.setText(toadd.get(i));
            Log.i("p",toadd.get(i));
            temp.addView(tempstr);
            container.addView(temp);
        }
    }
    protected List<String> GetMessages(){
        List Mes = new ArrayList();
        Uri message = Uri.parse("content://sms/");
        ContentResolver crd = getContentResolver();
        Cursor cr = crd.query(message,null,null,null,null);
        if (cr.moveToFirst()) {

            // must check the result to prevent exception
            do {
                String msgData = "";
                String cont ="";
                for(int idx=0;idx<cr.getColumnCount();idx++)
                {
                    msgData += " " + cr.getColumnName(idx) + ":" + cr.getString(idx);
                }
                cont = cont + msgData;
                Mes.add(msgData);
                // use msgData
            } while (cr.moveToNext());
            for (int i = 0; i < Mes.size(); i++) {
                Log.i("p",Mes.get(i).toString());
                Log.i("p","end");
            }
        } else {
            // empty box, no SMS
        }
        return Mes;
    }
}

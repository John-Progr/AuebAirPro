package com.example.myapp.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.widget.Button;
import android.view.View;
import android.content.Intent;


import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private ImageButton button4;
    private ImageButton button5;

    protected boolean power=false;
    public static final String strPower = "power";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (ImageButton) findViewById(R.id.settings_button);
        button5 = (ImageButton) findViewById(R.id.help);

        ImageView powerOff=(ImageView) findViewById(R.id.powerOff);
        ImageView  powerOn=(ImageView) findViewById(R.id.powerOn);

        powerOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                powerOff.setVisibility(View.GONE);
                powerOn.setVisibility(View.VISIBLE);
                power=true;
            }
        });

        powerOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                powerOn.setVisibility(View.GONE);
                powerOff.setVisibility(View.VISIBLE);
                power=false;
            }
        });



        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAC_Activity();
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDH_Activity();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAP_Activity();
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
                openSettings_Activity();
            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Βοήθεια");
                builder.setMessage(getString(R.string.help_text));
                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void openSettings_Activity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }



    public void openAC_Activity(){
        Intent intent=new Intent(getBaseContext(),AC_Activity.class);
        intent.putExtra(strPower,power);
        startActivity(intent);
    }

    public void openDH_Activity(){
        Intent intent=new Intent(getBaseContext(),DH_Activity.class);
        intent.putExtra(strPower,power);

        startActivity(intent);
    }

    public void openAP_Activity(){
        Intent intent=new Intent(this,AP_Activity.class);
        startActivity(intent);
    }

}
package com.example.myapp.app;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import java.util.Random;

public class AC_Activity extends MainActivity {
    int tempCounter = 20;
    int volumeCounter = 1;
    boolean checkHeat = false;
    boolean checkCold = false;
    String[] listItems = {"Αυτόματο", "Turbo", "Αθόρυβο","Ανεμιστήρας"};
    String[] timeItems = {"1 λεπτό", "2 λεπτά", "5 λεπτά", "10 λεπτά", "15 λεπτά",
            "30 λεπτά", "1 ώρα", "2 ώρες", "3 ώρες", "6 ώρες"};
    protected static int temp=18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //////////////////////////////////////////
        //Buttons cold/heat
        //////////////////////////////////////////

        Button heatButton = (Button) findViewById(R.id.heat);
        Button coldButton = (Button) findViewById(R.id.cold);

        heatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!checkHeat) {
                    heatButton.setBackgroundColor(Color.GREEN);
                    coldButton.setBackgroundColor(Color.WHITE);
                    checkHeat = true;
                    checkCold = false;
                } else {
                    heatButton.setBackgroundColor(Color.WHITE);
                    checkCold = true;
                    checkHeat = false;
                }
            }
        });

        coldButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!checkCold) {
                    coldButton.setBackgroundColor(Color.GREEN);
                    heatButton.setBackgroundColor(Color.WHITE);
                    checkCold = true;
                    checkHeat = false;
                } else {
                    coldButton.setBackgroundColor(Color.WHITE);
                    checkHeat = true;
                    checkCold = false;
                }
            }
        });

        //////////////////////////////////////////
        //onClickListener for weather info
        //////////////////////////////////////////

        ImageView link = (ImageView) findViewById(R.id.imageArrow);
        link.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.meteo.gr/cf.cfm?city_id=12"));
                startActivity(viewIntent);
            }
        });

        //////////////////////////////////////////
        //onClick Alert Dialog for modes
        //////////////////////////////////////////

        Button modeButton = (Button) findViewById(R.id.button12);
        TextView mode = (TextView) findViewById((R.id.textMode));
        int pos = 0;
        modeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AC_Activity.this);
                builder.setTitle("Επιλέξτε λειτουργία");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mode.setText(listItems[which]);
                    }
                });
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
        //////////////////////////////////////////
        //onClick Alert Dialog for timer
        //////////////////////////////////////////
        Button timerButton = (Button) findViewById(R.id.timer);
        ImageView timerImage = (ImageView) findViewById(R.id.timerImage);
        timerImage.setVisibility(View.GONE);
        timerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AC_Activity.this);
                builder.setTitle("Ορίστε Χρονόμετρο");
                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(timeItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        timerImage.setVisibility(View.VISIBLE);
                    }
                });
                builder.setNegativeButton(R.string.reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timerImage.setVisibility(View.GONE);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //////////////////////////////////////////
        //onClick for power on/off
        //////////////////////////////////////////

        ImageView powerOff1 = (ImageView) findViewById(R.id.powerOff1);
        ImageView powerOn1 = (ImageView) findViewById(R.id.powerOn1);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        boolean p1 = b.getBoolean(strPower);

        if (p1) {
            powerOff1.setVisibility(View.GONE);
            powerOn1.setVisibility(View.VISIBLE);

        } else {
            powerOn1.setVisibility(View.GONE);
            powerOff1.setVisibility(View.VISIBLE);
        }
        powerOn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                powerOn1.setVisibility(View.GONE);
                powerOff1.setVisibility(View.VISIBLE);
            }
        });

        powerOff1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                powerOn1.setVisibility(View.VISIBLE);
                powerOff1.setVisibility(View.GONE);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if(id== R.id.update){
            boolean somt=fetchWeatherTemp();
            displayTemperature(temp);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //////////////////////////////////////////
    //Buttons increase/decrease
    //////////////////////////////////////////

    public void increaseTemp(View view) {
        if (tempCounter < 30) {
            tempCounter++;
            displayHumidCounter(tempCounter);
        }
    }

    public void increaseVolume(View view) {
        if (volumeCounter < 5) {
            volumeCounter++;
            displayVolumeCounter(volumeCounter);
        }
    }

    public void decreaseTemp(View view) {
        if (tempCounter > 18) {
            tempCounter--;
            displayHumidCounter(tempCounter);
        }
    }

    public void decreaseVolume(View view) {
        if (volumeCounter > 1) {
            volumeCounter--;
            displayVolumeCounter(volumeCounter);
        }
    }

    private void displayHumidCounter(int humidCounter) {
        TextView displayInteger = (TextView) findViewById(
                R.id.textView10);
        displayInteger.setText("" + tempCounter + "°C");
    }

    private void displayVolumeCounter(int volumeCounter) {
        TextView displayInteger = (TextView) findViewById(
                R.id.volume_number);
        displayInteger.setText("" + volumeCounter);
    }
    protected  void displayTemperature(int temp) {
        TextView displayTemp = (TextView) findViewById(
                R.id.temp_out);
        displayTemp.setText("" + temp+"°C");
    }

    public boolean fetchWeatherTemp(){
        WeatherAPI weatherApi=new WeatherAPI();
        weatherApi.execute("000");
        return true;

    }

}
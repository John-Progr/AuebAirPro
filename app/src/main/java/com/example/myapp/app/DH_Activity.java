package com.example.myapp.app;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.app.AlertDialog.Builder;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import java.util.Random;

public class DH_Activity extends MainActivity{

    int humidCounter=50;
    int volumeCounter=1;
    String[] listItems = {"Αυτόματο", "Turbo", "Αθόρυβο"};
    String[] timeItems={"1 λεπτό","2 λεπτά","5 λεπτά","10 λεπτά","15 λεπτά",
            "30 λεπτά","1 ώρα","2 ώρες","3 ώρες","6 ώρες"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dh);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //////////////////////////////////////////
        //Random value for room's temperature
        //////////////////////////////////////////

        Random r = new Random();
        int i1 = r.nextInt(23 - 16) + 16;
        TextView temp=(TextView) findViewById(R.id.temp);
        temp.setText(Integer.toString(i1)+"°C");

        //////////////////////////////////////////
        //onClickListener for weather info
        //////////////////////////////////////////

        ImageView link=(ImageView) findViewById(R.id.imageArrow);
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

        Button modeButton= (Button) findViewById(R.id.button12);
        TextView mode=(TextView) findViewById((R.id.textMode));
        int pos=0;
        modeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DH_Activity.this);
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
        Button timerButton= (Button) findViewById(R.id.timer);
        ImageView timerImage=(ImageView) findViewById(R.id.timerImage);
        timerImage.setVisibility(View.GONE);
        timerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DH_Activity.this);
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
                builder.setNegativeButton(R.string.reset,new DialogInterface.OnClickListener() {
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


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dh, menu);
        return true;
    }

    //////////////////////////////////////////
    //Buttons increase/decrease
    //////////////////////////////////////////

    public void increaseHumid(View view) {
        if(humidCounter<90) {
            humidCounter+=5;
            displayHumidCounter(humidCounter);
        }
    }
    public void increaseVolume(View view) {
        if(volumeCounter<5) {
            volumeCounter++;
            displayVolumeCounter(volumeCounter);
        }
    }
    public void decreaseHumid(View view) {
        if(humidCounter>20) {
            humidCounter-=5;
            displayHumidCounter(humidCounter);
        }
    }
    public void decreaseVolume(View view) {
        if(volumeCounter>1) {
            volumeCounter--;
            displayVolumeCounter(volumeCounter);
        }
    }
    private void displayHumidCounter(int humidCounter) {
        TextView displayInteger = (TextView) findViewById(
                R.id.humid_number);
        displayInteger.setText("" + humidCounter+"%");
    }
    private void displayVolumeCounter(int volumeCounter) {
        TextView displayInteger = (TextView) findViewById(
                R.id.volume_number);
        displayInteger.setText("" + volumeCounter);
    }




}
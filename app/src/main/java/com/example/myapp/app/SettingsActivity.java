package com.example.myapp.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity{
    private SettingsFragment settingsFragment=new SettingsFragment();;
    String[] listItems = {"Αυτόματο", "Turbo", "Αθόρυβο"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,settingsFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Preference preference = settingsFragment.findPreference("policy");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Πολιτική απορρήτου & συμμόρφωση GDPR");
                builder.setMessage(getString(R.string.policy_text));
                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
}
package com.project.jsproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.project.jsproject.R;
import com.project.jsproject.api.BaseUrl;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private List<String>time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time=new ArrayList<>();


        time.add("25");
        time.add("10");
        time.add("30");
        time.add("40");

        setContentView(R.layout.settings_activity);
        mSpinner=(Spinner) findViewById(R.id.spnner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, time);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BaseUrl.SetttingTime=time.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        switch (BaseUrl.SetttingTime){
            case "25":
                mSpinner.setSelection(0);
                break;
            case "10":
                mSpinner.setSelection(1);
                break;
            case "30":
                mSpinner.setSelection(2);
                break;
            case "40":
                mSpinner.setSelection(3);
                break;
        }

    }

}
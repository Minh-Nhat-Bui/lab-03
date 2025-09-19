package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener{

    private ArrayList<City> dataList;
    private ListView cityList;
    private ArrayAdapter cityAdapter;

    private Integer cityPos = -1;
    public void addCity(City city){
        if (cityPos > -1){
            dataList.set(cityPos, city);
            cityPos = -1;
        }
        else {
            cityAdapter.add(city);
        }
        cityAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Toronto"
        };
        String []provinces = {
                "AB", "BC", "ON"
        };

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++){
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cityPos = i;
                City selectedCity = dataList.get(cityPos);

                AddCityFragment cityFragment = new AddCityFragment(selectedCity.getName(), selectedCity.getProvince());
                cityFragment.show(getSupportFragmentManager(), "Edit City");
            }
        });
    
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            cityPos = -1;
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });




    }
}
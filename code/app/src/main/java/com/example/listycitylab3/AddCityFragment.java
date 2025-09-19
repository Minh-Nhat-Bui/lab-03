package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.Objects;

public class AddCityFragment extends DialogFragment {

    private String cityName;
    private String provinceName;

    public AddCityFragment(){
    }
    public AddCityFragment( String cityName, String provinceName){
        this.cityName = cityName;
        this.provinceName = provinceName;
    }

    interface AddCityDialogListener{
        void addCity(City city);
    }
    private AddCityDialogListener listener;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String tag = getTag();

        boolean isEdit = (Objects.equals(tag, "Edit City"));

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if(isEdit){
            editCityName.setHint(cityName);
            editProvinceName.setHint(provinceName);
        }
        else{
            editCityName.setHint("City");
            editProvinceName.setHint("Province");
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit a city": "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    City newCity = new City(cityName, provinceName);
                    listener.addCity(newCity);

                })
                .create();
    }


     static AddCityFragment newInstance(City city){
        Bundle args = new Bundle();
        args.putSerializable("city", (Serializable) city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        }
        else{
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }
}

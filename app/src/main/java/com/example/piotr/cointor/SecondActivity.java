package com.example.piotr.cointor;

import android.content.Intent;      //Biblioteka podstawowych aktywności
import android.os.Bundle;       // mapowanie z danych String na inne typy
import android.support.v7.app.AppCompatActivity; // biblioteka wsparcia v7 AppCompatActivity
import android.view.View; //biblioteka widoku
import android.widget.Button; // bilbioteka widoku i funckji przycisku
import android.widget.EditText; // biblioteka widoku i funkcji wpisywania tekstu
import android.widget.TextView; // biblioteka tekstu

import java.text.DecimalFormat; // biblioteka numeryczna odpowiedzialna z numery dziesiętne

/**
 * Created by Piotr on 17.12.2018.
 */


public class SecondActivity extends AppCompatActivity { // Clasa drugiej aktywności rozszerzona o Podstawowe aktywności AppCompatActivity

    private TextView tvTitle, tvSubTitle, tvOutputName, tvOutputRate; //deklaracja zmiennych
    private EditText etInput;  // zmienna wpisywana  wartość waluty w PLN
    private Button btnCalculate; // przycisk oblicz

    private String currencyName; // zmienna z nazwą waluty
    private double currencyRate; // zmienna z kursem waluty
    private static DecimalFormat df3 = new DecimalFormat("0.###"); // deklaracja obiektu aby ograniczyć ilość miejsc po przecinku
    @Override
    protected void onCreate(Bundle savedInstanceState) { // przy utworzeniu
        super.onCreate(savedInstanceState); // odwłoanie od klasy bazowej
        setContentView(R.layout.activity_second); // ustawienie widoku na plik xml

        tvTitle = (TextView) findViewById(R.id.tvTitle); // Odwołanie zmiennej do obiektu widoku
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle); // Odwołanie zmiennej do obiektu widoku
        tvOutputName = (TextView) findViewById(R.id.tvOutputName); // Odwołanie zmiennej do obiektu widoku
        tvOutputRate = (TextView) findViewById(R.id.tvOutputRate); // Odwołanie zmiennej do obiektu widoku

        etInput = (EditText) findViewById(R.id.etInput); // Odwołanie zmiennej do obiektu widoku
        btnCalculate = (Button) findViewById(R.id.btnCalculate); // Odwołanie zmiennej do obiektu widoku

        Intent intent = getIntent();
        currencyName = intent.getStringExtra("currency_name"); //przypisanie wartosci do zmiennych
        currencyRate = intent.getDoubleExtra("currency_rate", 0);
        double bRate = (1/currencyRate); // kurs odwrócony
        tvTitle.setText("PLN na " + currencyName.toUpperCase()); // opis kursu w dużych literach
        tvSubTitle.setText("Kurs do PLN : " + currencyRate + // obliczanie kursu bazowego w dużych literach
                "\nKurs " + currencyName.toUpperCase()+" na PLN : " + df3.format(bRate)); // kurs odwrócony w dużych literach
        tvOutputName.setText(currencyName.toUpperCase() + ": "); // ustawienie tekstu  waluty - duzy tekst

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // ustawienie aktywności na przycisk oblicz
                if (etInput.getText().toString().length() == 0){ // pobieranie danych wpisanych jeżeli 0 nic nie zwracamy
                    return;
                }

                double input; // deklaracja typu wpisanego
                try{
                    input = Double.parseDouble(etInput.getText().toString()); // przypisanie wartosci wpisanych do obiektu input
                }
                catch (NumberFormatException e){
                    etInput.setText("");
                    return;
                }

                double output = input * currencyRate; // obliczanie wartosci (mnozenie liczby wpisanej przez kurs)
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##"); // deklaracja formatu liczbowego
                tvOutputRate.setText(decimalFormat.format(output)); //wyswietlanie obliczonej wartosci wymiany
            }
        });
    }
}

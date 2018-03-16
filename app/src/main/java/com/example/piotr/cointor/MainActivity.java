package com.example.piotr.cointor;


import android.content.Intent;              //Biblioteka podstawowych aktywności
import android.content.SharedPreferences;   //Biblioteka danych SP służąca do zapisu kursów
import android.os.Bundle; //A mapping from String keys to various Parcelable values
import android.preference.PreferenceManager; //Biblioteka używana do tworzenai hierarchii XML
import android.support.v7.app.AppCompatActivity;    //Podstawowa klasa funkcji paska działań
import android.widget.ListView;     //Biblioteka do widoku listy


import com.google.gson.Gson; // Biblioteka do zapisywania obietków typu Gson
import com.google.gson.reflect.TypeToken; // Biblioteka do rozpoznawania typu danych


import java.lang.reflect.Type;
import java.util.ArrayList;



import retrofit2.Call; // Bibliotek retrofit służąca do komunikacji http
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory; // Konwersja typu Gson

public class MainActivity extends AppCompatActivity implements Callback<CurrencyExchange>, CurrencyItemClickListener { // Główna klasa rozszerzona o funkcje AppCompatActivity i Callback biblioteki retrofit2 oraz CurrencyItemClickListener

    private ListView lvCurrency; //widok listy
    @Override
    protected void onCreate(Bundle savedInstanceState) { //podczas otwierania aplikacji tworzy się instacja widoku głównego z widokiem listy
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = (ListView) findViewById(R.id.lvCurrency);
    }

    @Override
    protected void onStart() { //przy otwieraniu aplikacji
        super.onStart(); // wołamy konstruktora klasy nadrzędnej
        loadCurrencyExchangeData(); // wołamy funkcje odpowiedzialną za załadowanie i wyświetlenie kursów

    }

    private void loadCurrencyExchangeData(){ // funkcja odpowiedzialna za wyświetlenie i załadowanie kursów
        Retrofit retrofit = new Retrofit.Builder() // tworzymy nowy obiekt typu retrofit poprzez jego builder
                .baseUrl("http://api.fixer.io/") // bazowy adres do komunikacji
                .addConverterFactory(GsonConverterFactory.create()) // konwersja danych z typu Gson
                .build(); //przywołanie konstruktora

        CurrencyExchangeService service = retrofit.create(CurrencyExchangeService.class); // tworzenie obiektu service
        Call<CurrencyExchange> call = service.loadCurrencyExchange(); //Utworzenie obiektu, będzie on zawierał danae kursów walut
        call.enqueue(this); // wysyła zapytanie i odczytuje callback albo jego brak czyli błąd
    }

    @Override
    public void onResponse(Call<CurrencyExchange> call, Response<CurrencyExchange> response) { // przy odpowiedzi na zapytanie
        CurrencyExchange currencyExchange = response.body(); // obiekt odpowiadajacy za waluty i ich kursy skonstruowany na wzór odpowiedzi
        ArrayList<Currency> listOffline = new ArrayList<Currency>(currencyExchange.getCurrencyList()); // ustworzenie listy służącej do zapisu
        saveArrayList(listOffline,"listOfRates"); // zapis danych do pliku SharedPreferences
        lvCurrency.setAdapter(new CurrencyAdapter(this, listOffline, this)); //Odwołanie widoku do listy z kursami




    }

    @Override
    public void onFailure(Call<CurrencyExchange> call, Throwable t) { // W przypadku braku odpowiedzi od serwera(np. brak internetu)
        ArrayList<Currency> listOffline = new ArrayList<>(getArrayList("listOfRates")); // tworzona jest lista w oparciu o plik zapisany w SP
        lvCurrency.setAdapter(new CurrencyAdapter(this, listOffline, this)); // Odwołanie widoku do listy z kursami
    }

    @Override
    public void onCurrencyItemClick(Currency c) { // Przy kliknieciu myszy na dany obiekt list przenosi nas do widoku drugiej aktywności z parametrem waluty
        Intent intent = new Intent(this, SecondActivity.class); // przenosi aktywności
        intent.putExtra("currency_name", c.getName()); // w zależnosci od waluty pobiera nazwe
        intent.putExtra("currency_rate", c.getRate()); // i kurs
        startActivity(intent);  // rozpoczęcie aktywności
    }

    public void saveArrayList(ArrayList<Currency> list, String key){  // zapis listy z walutami i kursami
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); // utworzenie domyslnego obiektu SP
        SharedPreferences.Editor editor = prefs.edit(); // dodane edytora pliku SP
        Gson gson = new Gson(); //utworzenie obiektu Gson
        String json = gson.toJson(list); // zapis do obiektu json listy
        editor.putString(key, json); //zapis klucza SP
        editor.apply();
    }

    public ArrayList<Currency> getArrayList(String key){ // Odczyt listy z pliku Shared Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); // utworzenie domyślnego obiektu SP
        Gson gson = new Gson(); // utworzenie obiektu Gson
        String json = prefs.getString(key, null); // przywołanie wartości z SP do obiektu json
        Type type = new TypeToken<ArrayList<Currency>>() {}.getType(); // rozpoznanie typu danych
        return gson.fromJson(json, type); // zwrót obiektu json z kursami w odpowiednim typie danych
    }






}

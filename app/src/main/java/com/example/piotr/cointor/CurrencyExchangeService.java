package com.example.piotr.cointor;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Piotr on 21.12.2017.
 */

public interface CurrencyExchangeService {

    @GET("latest?base=PLN")
    Call<CurrencyExchange> loadCurrencyExchange();
}

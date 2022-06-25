package com.mahmud.covidinfotracker.api;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface apiinterface {

    static final String BASE_URL = "https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<List<CountryData>> getCountryData();


}

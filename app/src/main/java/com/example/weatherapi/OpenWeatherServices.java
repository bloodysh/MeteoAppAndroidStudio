package com.example.weatherapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenWeatherServices {
    @GET("forecast?q=London&appid=f93a6bbe8570dd012d393e3105a54078&units=metric")
    Call<ForecastData> getForcast();
}


package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    EditText locationInput;
    TextView textViewRTemp;
    TextView textViewMin;
    TextView textViewMax;
    TextView textDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationInput = findViewById(R.id.editTextVille);
        textViewRTemp = findViewById(R.id.textViewRTemp); // Initialize textViewRTemp
        textViewMin = findViewById(R.id.textViewMin);
        textViewMax = findViewById(R.id.textViewRMax);
        textDateTime = findViewById(R.id.textViewDateTime);

        MyWeatherService service = MyWeatherService.retrofit.create(MyWeatherService.class);
        String apiKey = "f93a6bbe8570dd012d393e3105a54078";
        String unit = "metric";

        locationInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Capture the user's input for the location
                String location = locationInput.getText().toString().trim();

                if (!location.isEmpty()) {
                    Call<ForecastData> call = service.getForecast(location, apiKey, unit);

                    call.enqueue(new Callback<ForecastData>() {
                        // Inside your onResponse method
                        // Inside your onResponse method
                        @Override
                        public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ForecastData forecastData = response.body();

                                // Update the temperature TextView
                                long seconds = forecastData.getPrevisions().get(0).getDatetime();
                                Date date = new Date(seconds * 1000); // Convert seconds to milliseconds

                                int index = forecastData.getMidnightIndex();
                                Toast.makeText(MainActivity.this, ""+index, Toast.LENGTH_SHORT).show();
                                double maxi = -100;
                                double minim = 100;

                                for (int i = 0; i < index + 8; i++) {
                                    double max = forecastData.getPrevisions().get(i).getWeather().getTempMax();
                                    if (max > maxi) {
                                        maxi = max;
                                    }

                                    double min = forecastData.getPrevisions().get(i).getWeather().getTempMin();
                                    if (min < minim) {
                                        minim = min;
                                    }
                                }


                                double temperature = forecastData.getPrevisions().get(0).getWeather().getTemp();

                                // Format the date without time
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = dateFormat.format(date);

                                // Display the information in TextViews
                                String temperatureMessage = "Temperature in " + location + ": " + temperature + "°C";
                                textViewRTemp.setText(temperature + " °C");
                                textViewMin.setText(minim + " °C");
                                textViewMax.setText(maxi + " °C");
                                textDateTime.setText("" + formattedDate);

                            }
                        }


                        @Override
                        public void onFailure(Call<ForecastData> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a location.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }
}

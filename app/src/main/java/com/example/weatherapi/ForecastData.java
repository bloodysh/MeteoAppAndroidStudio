package com.example.weatherapi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForecastData implements Serializable {
    @SerializedName("list")
    ArrayList<Forecast> previsions;


    public ArrayList<Forecast> getPrevisions() {
        return previsions;
    }

    public void setPrevisions(ArrayList<Forecast> previsions) {
        this.previsions = previsions;
    }

    public int getMidnightIndex() {
        // Expression régulière pour correspondre à la date se terminant par 00:00:00
        String pattern = "\\d{4}-\\d{2}-\\d{2} 00:00:00";
        Pattern regex = Pattern.compile(pattern);

        for (int i = 0; i < previsions.size(); i++) {
            String dateTime = previsions.get(i).getDateText();

            Matcher matcher = regex.matcher(dateTime);

            if (matcher.matches()) {
                // Correspondance trouvée, retournez l'index
                return i;
            }
        }

        // Aucune correspondance trouvée, retournez une valeur par défaut (par exemple, -1)
        return -1;
    }


}

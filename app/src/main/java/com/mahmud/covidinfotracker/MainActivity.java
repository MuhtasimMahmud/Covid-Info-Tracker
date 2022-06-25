package com.mahmud.covidinfotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mahmud.covidinfotracker.api.CountryData;
import com.mahmud.covidinfotracker.api.apiUtilities;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView totalConfirm, totalActive, totalRecovered, totalDeath, totalTests;
    private TextView todayConfirm, todayRecovered, todayDeath, date;
    private PieChart piechart;

    private List< CountryData > list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        initializs();

        apiUtilities.getapiApiinterface().getCountryData().enqueue(new Callback< List< CountryData > >() {
            @Override
            public void onResponse(Call< List< CountryData > > call, Response< List< CountryData > > response) {
                list.addAll(response.body());

                for(int i=0; i<list.size(); i++){
                    if(list.get(i).getCountry().equals("Bangladesh")){
                        int confirm = Integer.parseInt(list.get(i).getCases());
                        int active = Integer.parseInt(list.get(i).getActive());
                        int recovered = Integer.parseInt(list.get(i).getRecovered());
                        int death = Integer.parseInt(list.get(i).getDeaths());
                        int tests = Integer.parseInt(list.get(i).getTests());


                        totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                        totalActive.setText(NumberFormat.getInstance().format(active));
                        totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                        totalDeath.setText(NumberFormat.getInstance().format(death));
                        totalTests.setText(NumberFormat.getInstance().format(tests));

                        todayConfirm.setText("(Today : "+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases()))+")");
                        todayRecovered.setText("(Today : "+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered()))+")");
                        todayDeath.setText("(Today : "+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths()))+")");



                        piechart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.yellow)));
                        piechart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue_pie)));
                        piechart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green_pie)));
                        piechart.addPieSlice(new PieModel("Death", death, getResources().getColor(R.color.red_pie)));
                        piechart.startAnimation();


                        setText(list.get(i).getUpdated());

                    }
                }
            }

            @Override
            public void onFailure(Call< List< CountryData > > call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setText(String updated) {

        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        long milliseconds = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        date.setText("Updated at "+ format.format(calendar.getTime()));
    }

    private void initializs(){
        totalConfirm = findViewById(R.id.totalConfirm);
        totalActive = findViewById(R.id.totalActive);
        totalRecovered = findViewById(R.id.totalRecovered);
        totalDeath = findViewById(R.id.totalDeath);
        totalTests = findViewById(R.id.totalTests);
        todayConfirm = findViewById(R.id.todayConfirm);
        todayRecovered = findViewById(R.id.todayRecovered);
        todayDeath = findViewById(R.id.todayDeath);
        totalTests = findViewById(R.id.totalTests);

        piechart = findViewById(R.id.piechart);
        date = findViewById(R.id.date);
    }
}
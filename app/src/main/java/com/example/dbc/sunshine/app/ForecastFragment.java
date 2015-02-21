package com.example.dbc.sunshine.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Forecast fragment containing a Forecast List view.
 */
public class ForecastFragment extends Fragment {
    private ArrayAdapter<String> strawhatPiratesAdapter;

    public ForecastFragment() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute("94043");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
                "Luffy - Sunny 25%",
                "Zorro - cloudy 45%",
                "Nami - cloudy 35%",
                "Sanji - sunny 50%",
                "Chopper - rainy 50%",
                "Franky - cloudy 50%",
                "Brook - cloudy 35%",
                "Luffy - Sunny 25%",
                "Zorro - cloudy 45%",
                "Nami - cloudy 35%",
                "Sanji - sunny 50%",
                "Chopper - rainy 50%",
                "Franky - cloudy 50%",
                "Brook - cloudy 35%"
        };
        List<String> strawHatPirates = new ArrayList<String>(Arrays.asList(data));

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        strawhatPiratesAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                strawHatPirates
        );

        FrameLayout mainFrame = (FrameLayout) rootView.findViewById(R.id.list_fragment);
        ListView piratesListView = (ListView)mainFrame.findViewById(R.id.listView);
        piratesListView.setAdapter(strawhatPiratesAdapter);

        return rootView;
    }

public class FetchWeatherTask extends AsyncTask<String, Void, Void>{
    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

    @Override
    protected Void doInBackground(String... params) {

        if(params == null){
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr;
        String unit = "metric";
        String format = "json";
        int days = 7;
        try {
            final String FORECAST_BASE_URL ="http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String QUERY_PARAM = "q";
            final String UNIT_PARAM = "units";
            final String FORMAT_PARAM = "mode";
            final String DAYS_PARAM = "cnt";
            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,params[0])
                    .appendQueryParameter(UNIT_PARAM,unit)
                    .appendQueryParameter(FORMAT_PARAM,format)
                    .appendQueryParameter(DAYS_PARAM,Integer.toString(days))
                    .build();
            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "The built url is :" + url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            if(inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                stringBuffer.append(line+"\n");
            }
            if(stringBuffer.length() == 0){
                return null;
            }
            forecastJsonStr = stringBuffer.toString();
            Log.i(LOG_TAG,"Our JSON is:"+ forecastJsonStr);
        }catch(IOException e){
            Log.e(LOG_TAG, "ERROR", e);
        }finally {
            try{
                reader.close();
            }catch(final IOException e){
                Log.e(LOG_TAG,"Error Closing Stream",e);
            }
            urlConnection.disconnect();
        }
        return null;
    }
}
}

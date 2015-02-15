package com.example.dbc.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ArrayAdapter<String> strawhatPiratesAdapter;

        public PlaceholderFragment() {
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
    }
}

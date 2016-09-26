package com.gamaliel.surya.merame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchTrip extends AppCompatActivity {

    String description = "";
    String tPlace = "";
    String tBudget, tMonth, tYear, tAge, tGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //AutoComplete Place
        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.searchTrip_autocomplete_destination);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.part_auto_complete_list_item));
        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                description = (String) parent.getItemAtPosition(position);
                autocompleteView.setText(description);
                tPlace = description;
            }
        });
        autocompleteView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // on focus off
                    autocompleteView.setText("");
                }
            }
        });

        //Spinner
        Spinner sp_budget = (Spinner) findViewById(R.id.spinner_budget);
        Spinner sp_month = (Spinner) findViewById(R.id.spinner_month);
        Spinner sp_year = (Spinner) findViewById(R.id.spinner_year);
        Spinner sp_age = (Spinner) findViewById(R.id.spinner_age);
        Spinner sp_gender = (Spinner) findViewById(R.id.spinner_gender);

        List<String> budgets = new ArrayList<>();
        budgets.add("Budget Ransel"); budgets.add("Budget Koper");

        List<String> months = new ArrayList<>();
        months.add("Januari"); months.add("Februari"); months.add("Maret");
        months.add("April"); months.add("Mei"); months.add("Juni");
        months.add("Juli"); months.add("Agustus"); months.add("September");
        months.add("Oktober"); months.add("November"); months.add("Desember");

        List<String> years = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int list_year = calendar.get(Calendar.YEAR);
        years.add(String.valueOf(list_year)); years.add(String.valueOf(list_year+1));

        List<String> ages = new ArrayList<>();
        ages.add("Dibawah 25 TH"); ages.add("25 TH Keatas");

        List<String> genders = new ArrayList<>();
        genders.add("Mix"); genders.add("Cowok Saja"); genders.add("Cewek Saja");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<>(this, R.layout.part_spinner_month, months);
        ArrayAdapter<String> dataAdapterBudget = new ArrayAdapter<>(this, R.layout.part_spinner_budget, budgets);
        ArrayAdapter<String> dataAdapterYear = new ArrayAdapter<>(this, R.layout.part_spinner_year, years);
        ArrayAdapter<String> dataAdapterAge = new ArrayAdapter<>(this, R.layout.part_spinner_age, ages);
        ArrayAdapter<String> dataAdapterGender = new ArrayAdapter<>(this, R.layout.part_spinner_gender, genders);

        // Drop down layout style - list view with radio button
        dataAdapterBudget.setDropDownViewResource(R.layout.part_spinner);
        dataAdapterMonth.setDropDownViewResource(R.layout.part_spinner);
        dataAdapterYear.setDropDownViewResource(R.layout.part_spinner);
        dataAdapterAge.setDropDownViewResource(R.layout.part_spinner);
        dataAdapterGender.setDropDownViewResource(R.layout.part_spinner);

        // attaching data adapter to spinner
        sp_month.setAdapter(
                new NothingSelectedSpinnerAdapter(dataAdapterMonth, R.layout.part_spinner_month, this));
        sp_year.setAdapter(
                new NothingSelectedSpinnerAdapter(dataAdapterYear, R.layout.part_spinner_year, this));
        sp_budget.setAdapter(
                new NothingSelectedSpinnerAdapter(dataAdapterBudget, R.layout.part_spinner_budget, this));
        sp_age.setAdapter(
                new NothingSelectedSpinnerAdapter(dataAdapterAge, R.layout.part_spinner_age, this));
        sp_gender.setAdapter(
                new NothingSelectedSpinnerAdapter(dataAdapterGender, R.layout.part_spinner_gender, this));

        // Spinner click listener
        sp_budget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0)
                    tBudget = parent.getItemAtPosition(position).toString();
                try {
                    if (!tBudget.equals("Budget")) {
                        TextView tv = (TextView) findViewById(R.id.hint_spinner_budget);
                        tv.setTextColor(Color.BLACK);
                    }

                } catch (NullPointerException npe) {
                    Log.e("sp_budget_error", npe.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    tMonth = parent.getItemAtPosition(position).toString();
                try {
                    if (!tMonth.equals("Bulan")) {
                        TextView tv = (TextView) findViewById(R.id.hint_spinner_month);
                        tv.setTextColor(Color.BLACK);
                    }
                } catch (NullPointerException npe) {
                    Log.e("sp_month_error", npe.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    tYear = parent.getItemAtPosition(position).toString();
                try {
                    if (!tYear.equals("Tahun")) {
                        TextView tv = (TextView) findViewById(R.id.hint_spinner_year);
                        tv.setTextColor(Color.BLACK);
                    }
                } catch (NullPointerException npe) {
                    Log.e("sp_year_error", npe.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sp_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    tAge = parent.getItemAtPosition(position).toString();
                try {
                    if (!tAge.equals("Usia")) {
                        TextView tv = (TextView) findViewById(R.id.hint_spinner_age);
                        tv.setTextColor(Color.BLACK);
                    }
                } catch (NullPointerException npe) {
                    Log.e("sp_age_error", npe.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    tGender = parent.getItemAtPosition(position).toString();
                try {
                    if (!tGender.equals("Gender")) {
                        TextView tv = (TextView) findViewById(R.id.hint_spinner_gender);
                        tv.setTextColor(Color.BLACK);
                    }
                } catch (NullPointerException npe) {
                    Log.e("sp_gender_error", npe.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button nextButton = (Button) findViewById(R.id.button_search);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchTrip.this, ResultTrip.class);
                intent.putExtra("KEY_PLACE", tPlace);
                intent.putExtra("KEY_MONTH", tMonth);
                intent.putExtra("KEY_YEAR", tYear);
                intent.putExtra("KEY_BUDGET", tBudget);
                intent.putExtra("KEY_AGE", tAge);
                intent.putExtra("KEY_GENDER", tGender);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_trip, menu);
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
}

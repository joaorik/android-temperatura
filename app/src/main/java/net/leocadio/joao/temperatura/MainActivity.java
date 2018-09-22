package net.leocadio.joao.temperatura;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private Temperature selectedEditTxt;
    CustomProgressBar customProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText celsiusValue = (EditText) findViewById(R.id.celciusText);
        final EditText far = (EditText) findViewById(R.id.farText);

        customProgressBar = (CustomProgressBar) findViewById(R.id.custom_pb);

        celsiusValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasFocus) {
                selectedEditTxt = Temperature.CELCIUS;
                celsiusValue.setFilters(new InputFilter[]{new InputFilterMinMax(-273.15, Double.MAX_VALUE, selectedEditTxt, MainActivity.this), new InputFilter.LengthFilter(10)});
            }

        });

        far.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                selectedEditTxt = Temperature.FAHRENHEIT;
                far.setFilters(new InputFilter[]{new InputFilterMinMax(-459.67, Double.MAX_VALUE, selectedEditTxt, MainActivity.this), new InputFilter.LengthFilter(10)});
            }
        });

        celsiusValue.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String tempValue = celsiusValue.getText().toString();
                if (!tempValue.equals("") && !tempValue.equals(".") && !tempValue.equals("-") && selectedEditTxt == Temperature.CELCIUS) {
                    double temp = Double.parseDouble(tempValue);
                    // fahrenheit
                    double outputFar = (temp * 1.8) + 32;
                    far.setText(String.valueOf(stripDecimal(outputFar)));

                    customProgressBar.setProgress(10);

                } else if (selectedEditTxt == Temperature.CELCIUS) {
                    far.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        far.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String tempValue = far.getText().toString();

                if (!tempValue.equals("") && !tempValue.equals(".") && !tempValue.equals("-") && selectedEditTxt == Temperature.FAHRENHEIT) {

                    double temp = Double.parseDouble(tempValue);

                    //Celcius
                    double outputCelcius = (temp - 32) / 1.8;
                    celsiusValue.setText(String.valueOf((stripDecimal(outputCelcius))));
                } else if (selectedEditTxt == Temperature.FAHRENHEIT) {
                    celsiusValue.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    public Double stripDecimal(Double temp) {

        String valor = String.format("%.2f", temp);
        return Double.parseDouble(valor);
    }

}

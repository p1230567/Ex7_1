package com.cyo.ex7_1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by USER on 2015/4/24.
 */
public class OptionActivity extends Activity {
    private Button btn_submit, btn_default;
    private Spinner spn_color, spn_size;
    private String intent_color, intent_size;
    private final static String DEFAULT_color = "黑色";
    private final static String DEFAULT_size = "16sp";
    private final static String PREF_NAME = "color_size";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        findViews();

    }

    private void findViews() {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_default = (Button) findViewById(R.id.btn_default);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("color", intent_color);
                bundle.putString("size", intent_size);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        btn_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPref();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("color", intent_color);
                bundle.putString("size", intent_size);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

//        ---------------------------spinner內容------------------------------------
        spn_color = (Spinner) findViewById(R.id.spn_color);
        spn_size = (Spinner) findViewById(R.id.spn_size);
        String[] color = {"紅色", "黑色", "藍色", "黃色", "綠色"};
        String[] size = {"12", "16", "20", "24", "28"};
        // ArrayAdapter用來管理整個選項的內容與樣式，android.R.layout.simple_spinner_item為內建預設樣式
        ArrayAdapter<String> adapter_color = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, color);
        ArrayAdapter<String> adapter_size = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, size);
        spn_color.setAdapter(adapter_color);
        spn_size.setAdapter(adapter_size);
        spn_color.setOnItemSelectedListener(listener_color);
        spn_size.setOnItemSelectedListener(listener_size);
//        ---------------------------spinner內容------------------------------------

    }


    //    下拉式選單監聽器
    Spinner.OnItemSelectedListener listener_color = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            intent_color = parent.getItemAtPosition(position).toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    Spinner.OnItemSelectedListener listener_size = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            intent_size = parent.getItemAtPosition(position).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };



    private void loadPref() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String color = preferences.getString("color", DEFAULT_color);
        intent_color = color;
        String size = preferences.getString("size", DEFAULT_size);
        intent_size = size;


    }

}

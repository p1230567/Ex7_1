package com.cyo.ex7_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    private final int LOGIN_REQUEST = 0;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        read_Text();
    }

    private void findViews() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        registerForContextMenu(tv_content);
    }

    //    點擊螢幕跳出的選單

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                option_activity();
                break;
            case R.id.over:
                finish();
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    private void option_activity() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, OptionActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != LOGIN_REQUEST) {
            return;
        }

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            tv_content.setTextSize(Integer.parseInt(bundle.getString("size")));
            switch (bundle.getString("color")) {
                case "紅色":
                    tv_content.setTextColor(Color.RED);
                    break;
                case "黑色":
                    tv_content.setTextColor(Color.BLACK);
                    break;
                case "藍色":
                    tv_content.setTextColor(Color.BLUE);
                    break;
                case "黃色":
                    tv_content.setTextColor(Color.YELLOW);
                    break;
                case "綠色":
                    tv_content.setTextColor(Color.GREEN);
                    break;


            }
        }
    }

    //  讀取檔案方法
    private void read_Text() {
        BufferedReader bf = null;
        try {
            InputStream is = getAssets().open("dearJohn.txt");
            bf = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String reader;
            while ((reader = bf.readLine()) != null) {
                sb.append(reader);
                sb.append("\n");
            }
            tv_content.setText(sb);

        } catch (IOException e) {
            Log.e("MainActivity", e.toString());
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException ie) {
                    Log.e("MainActivity", ie.toString());
                }
            }
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
}

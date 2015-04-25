package com.cyo.ex7_1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
    //    INTENT的回傳碼
    private final int LOGIN_REQUEST = 0;
    private TextView tv_content;
    //    儲存設定、intent回來的顏色大小，使用static和程式一起啟動
    private static String intent_color, intent_size;

    //    原本預設的顏色和大小
    private final static String DEFAULT_color = "黑色";
    private final static String DEFAULT_size = "16";
    //    設定的名稱
    private final static String PREF_NAME = "color_size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
//        onCreate時載入文件
//        讀取設定
//       設定字型
        read_Text();
        loadPref();
        setTEXT();
    }

    private void findViews() {
        tv_content = (TextView) findViewById(R.id.tv_content);
//        註冊按著時啟動選單->textview
        registerForContextMenu(tv_content);
    }

    //-----------------------------點擊螢幕跳出的選單--------------------------------------
//  將選單inflater上去
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    //  選單個選項被點擊時的動作及分辨user點擊的選項
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
//                intent到option_activity
                option_activity();
                break;
            case R.id.over:
//                關閉APP
                finish();
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    //-----------------------------傳送到點擊設定的那一個頁面---------------------------
    private void option_activity() {
        Intent intent = new Intent();
//        設定本頁MainActivity.this，傳送到OptionActivity.class
        intent.setClass(MainActivity.this, OptionActivity.class);
//        傳送過去並且附加LOGIN_REQUEST
        startActivityForResult(intent, LOGIN_REQUEST);

    }

    //-----------------------------------當頁面回傳回來資料時啟動-----------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        如果回復碼不對則不動作
        if (requestCode != LOGIN_REQUEST) {
            return;
        }
//        回復碼正確則進行
        if (resultCode == RESULT_OK) {
//            取得回傳回來的Intent data資料
            Bundle bundle = data.getExtras();
            intent_color = bundle.getString("color");
            intent_size = bundle.getString("size");
//收到回傳的資料之後設定字型
            setTEXT();
//            儲存設定
            savePref();
        }
    }


    private void setTEXT() {
//            將字體大小Integer.parseInt將回傳的字串轉為int並設定textsize
        tv_content.setTextSize(Integer.parseInt(intent_size));
//            進行回傳的顏色篩選，並且設定字型顏色
        switch (intent_color) {
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


    //-----------------------------------讀取檔案方法----------------------------------------
    private void read_Text() {

//        先建立雙向的管子
        BufferedReader bf = null;
        try {
//            透過getassets()取得該資料夾並open檔案
            InputStream is = getAssets().open("dearJohn.txt");
            bf = new BufferedReader(new InputStreamReader(is));
//            StringBuilder讓字串append
            StringBuilder sb = new StringBuilder();
            String reader;
            while ((reader = bf.readLine()) != null) {
                sb.append(reader);
                sb.append("\n");
            }
//          當讀取完畢之後將StringBuilder內容settext到textview上
            tv_content.setText(sb);

//          進行IO動作所以要IOException
        } catch (IOException e) {
            Log.e("MainActivity", e.toString());
        } finally {
            if (bf != null) {
                try {
//                    使用完畢後關閉
                    bf.close();
                } catch (IOException ie) {
                    Log.e("MainActivity", ie.toString());
                }
            }
        }

    }

//      儲存設定
    private void savePref() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        preferences.edit()
                .putString("color", intent_color)
                .putString("size", intent_size)
                .apply();


    }
//  讀取設定
    private void loadPref() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        intent_color = preferences.getString("color", DEFAULT_color);
        intent_size = preferences.getString("size", DEFAULT_size);
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

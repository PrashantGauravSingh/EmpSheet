package com.andtechno.singh.empsheet;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity {

  //  private static final String MY_URL = "https://api.myjson.com/bins/1en2ca";
    private static final String MY_URL="https://api.myjson.com/bins/1f2aya";
 // private static final String MY_URL="https://api.myjson.com/bins/1af4fm";
    static ArrayList<String> nameList = new ArrayList<>();
    static ArrayList<String> agelist = new ArrayList<>();
    static ArrayList<String> genderList = new ArrayList<>();
    static ArrayList<String> Citylist = new ArrayList<>();
    static ArrayList<String> desglist = new ArrayList<>();
    static ArrayList<String> imglist = new ArrayList<>();
    private ProgressBar progBar;
    private Handler handler = new Handler();
    ;
    private int ProgressStatus = 0;
    String toReturn = "",str1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static boolean permissionGranted = false;
    public TextView txtView;

    public static JSONArray ja_data;
    Date currentTime,doneTime;
    Date httpcurrentTime,httpDoneTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progBar = findViewById(R.id.progressBar1);
        txtView = findViewById(R.id.textView1);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        requestPermission();
      //  getEmpDetails();
        new downloadFile().execute();

    }

    // Using AsyncHttp Library(Did Time Complexcity check)
//    public void getEmpDetails() {
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(MY_URL, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
    //             currentTime = Calendar.getInstance().getTime();
//                long millis = System.currentTimeMillis() % 1000;
//                Log.e("Async Start time",": "+millis);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                try {
//                    str1 = new String(response, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                try {
//
//                    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//                    File myFile = new File(folder, "newAsyncDetails.json");// Folder Name
//
//                    FileOutputStream fileOutputStream = null;
//                    try {
//                        fileOutputStream = new FileOutputStream(myFile);
//                        fileOutputStream.write(response);
//                        Toast.makeText(MainActivity.this, "Download Completed" , Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (fileOutputStream != null) {
//                            try {
//                                fileOutputStream.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    long millis = System.currentTimeMillis() % 1000;
//                    Log.e("Async done time",": "+millis);

//                       doneTime = Calendar.getInstance().getTime();
//                    JSONObject jsonObj = new JSONObject(str1);
//                    String data = jsonObj.getString("data");
//                    ja_data = jsonObj.getJSONArray("data");
//                    int length = ja_data.length();
//
//
//                    for (int i = 0; i < ja_data.length(); i++) {
//
//                        JSONArray first = ja_data.getJSONArray(i);
//                        for (int j = 0; j < 6; j++) {
//
//                            if (j == 0)
//                                nameList.add(first.getString(j));
//                            else if (j == 1)
//                                agelist.add(first.getString(j));
//                            else if (j == 2)
//                                Citylist.add(first.getString(j));
//                            else if (j == 3)
//                                desglist.add(first.getString(j));
//                        }
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
// //               new downloadFile().execute();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });
//    }


    private class downloadFile extends AsyncTask<Void, Void, Void> {
        int count = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            doSomeWork();
            long millis = System.currentTimeMillis() % 1000;
            Log.e("Http Start time",": "+millis);
        //    httpcurrentTime = Calendar.getInstance().getTime();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(MY_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedInputStream is = new BufferedInputStream(
                            conn.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    String line;

                    while ((line = br.readLine()) != null) {
                        toReturn += line;
                    }

                    br.close();
                    is.close();
                    conn.disconnect();

                } else {
                    throw new IllegalStateException("HTTP response: " + responseCode);
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {

            long millis = System.currentTimeMillis() % 1000;
            Log.e("Http Done time",": "+millis);
     // httpDoneTime = Calendar.getInstance().getTime();
            try {
                    JSONObject jsonObj = new JSONObject(toReturn);
                    String data = jsonObj.getString("data");
                    ja_data = jsonObj.getJSONArray("data");
                    int length = ja_data.length();
                    for (int i = 0; i < ja_data.length(); i++) {

                     //   Log.e("json Array", "" + ja_data.getString(i));
                        JSONArray first = ja_data.getJSONArray(i);
                        for (int j = 0; j < 6; j++) {

                            if (j == 0)
                                nameList.add(first.getString(j));
                            else if (j == 1)
                                agelist.add(first.getString(j));
                            else if (j == 2)
                                genderList.add(first.getString(j));
                            else if (j == 3)
                                Citylist.add(first.getString(j));
                            else if (j == 4)
                                desglist.add(first.getString(j));
                            else if (j == 5)
                                imglist.add(first.getString(j));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            Intent intent = new Intent(MainActivity.this, EmployeeDetails.class);
            startActivity(intent);
            writedata(toReturn);


        }


    }


    void writedata(String st1) {
        //  try {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myFile = new File(folder, "empDetails.json");// Folder Name

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(myFile);
            fileOutputStream.write(st1.getBytes());
            Toast.makeText(this, "Downloaded" , Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doSomeWork();

    }

    public void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, INTERNET, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;

                } else {
                    permissionGranted = false;

                }
                return;
            }

        }
    }

    public void doSomeWork() {
        new Thread(new Runnable() {
            public void run() {
                while (ProgressStatus < 100) {
                    ProgressStatus += 5;
                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            progBar.setProgress
                                    (ProgressStatus);
                            txtView.setText(ProgressStatus + "/" + progBar.getMax());
                            if (ProgressStatus == 100) {
                                progBar.setVisibility(View.GONE);
                            }
                        }
                    });
                    try {
                        //Display progress slowly
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}




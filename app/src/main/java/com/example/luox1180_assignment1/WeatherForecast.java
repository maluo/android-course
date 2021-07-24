package com.example.luox1180_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar bar;
    TextView currentTemp,minTemp,maxTemp,title;
    ImageView weather;
    protected static final String ACTIVITY_NAME = "WeatherForecastActivity";
    protected static String _URL = "http://api.openweathermap.org/data/2.5/weather?q=%s,ca&APPID=e83b3c4c08285bf87b99f9bbc0abe3f0&mode=xml&units=metric";
    protected static String URL = "";
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "On Create");
        setContentView(R.layout.activity_weather_forecast);
        findElements();

        city = getIntent().getExtras().getString("city").toString();
        URL = String.format(_URL,city);

        ForecastQuery factory = new ForecastQuery();
        factory.execute();

        city = city.substring(0, 1).toUpperCase() + city.substring(1);
        title.setText(city+" "+getString(R.string.weather_postfix));
    }

    private void findElements(){
        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        currentTemp = findViewById(R.id.txtCurrent);
        minTemp = findViewById(R.id.txtMin);
        maxTemp = findViewById(R.id.txtMax);
        weather = findViewById(R.id.weather);
        title = findViewById(R.id.weathertitle);
    }

    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {
        String  min,max,current, fileName;
        Bitmap img;

        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            InputStream input;
            try {
                url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                input = conn.getInputStream();
                Log.i(ACTIVITY_NAME, "File Downloaded");

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(
                        XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(input, null);

                Log.i(ACTIVITY_NAME, "Start Parsing");
                int type;
                //While you're not at the end of the document:
                while ((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    //Are you currently at a Start Tag?
                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("temperature")) {
                            current = parser.getAttributeValue(null,
                                    "value");
                            publishProgress(25);
                            min = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            max = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        } else if (parser.getName().equals("weather")) {
                            fileName = parser.getAttributeValue(null, "icon")+".png";
                            if (fileExistance(fileName)) {
                                FileInputStream fis = null;
                                fis = openFileInput(fileName);
                                img = BitmapFactory.decodeStream(fis);
                            }else{
                                String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                img = getImage(new
                                        URL(iconUrl));
                                FileOutputStream outputStream =
                                        openFileOutput( fileName, Context.MODE_PRIVATE);
                                img.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                            }
                            publishProgress(100);
                        }
                    }
                    parser.next();
                }
            }catch (MalformedURLException e) {
                Log.i(ACTIVITY_NAME, e.getMessage());
            } catch (ProtocolException e) {
                Log.i(ACTIVITY_NAME, e.getMessage());
            } catch (IOException e) {
                Log.i(ACTIVITY_NAME, e.getMessage());
            } catch (XmlPullParserException e) {
                Log.i(ACTIVITY_NAME, e.getMessage());
            }
            Log.i(ACTIVITY_NAME, "Finish Parsing");
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            bar.setVisibility(View.INVISIBLE);
            currentTemp.setText(current + " " +getString(R.string.weather_degree));
            minTemp.setText(min+ " " +getString(R.string.weather_degree));
            maxTemp.setText(max+ " " +getString(R.string.weather_degree));
            weather.setImageBitmap(img);
            Log.i(ACTIVITY_NAME, "Setting Temperature");
        }

        @Override
        protected void onProgressUpdate(Integer...    values) {
            bar.setProgress(values[0]);
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection)
                        url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return
                            BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}
package com.uas.fitguru;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {
    public static String KEY_ACTIVITY = "msg_activity";
    TextView txtMessageF;
    private String username;
    WebView webView;
    CalendarView calendarView;

    public static final String DATA_URL = "http://192.168.2.5/FitGuru/search_login.php?";
    public static final String KEY_USERNAME = "username";
    public static final String JSON_ARRAY = "result";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        //Hooks
        txtMessageF = (TextView) view.findViewById(R.id.textCoba);
        calendarView = (CalendarView)view.findViewById(R.id.calendarView);
        webView = (WebView)view.findViewById(R.id.webView1);

        try {
            getData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Menampilkan CalenderView
        calendarView.setFocusedMonthDateColor(Color.RED);
        calendarView.setUnfocusedMonthDateColor(Color.BLUE);
        calendarView.setSelectedWeekBackgroundColor(Color.RED);
        calendarView.setWeekSeparatorLineColor(Color.GREEN);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity(), "your fitness activity is scheduled for " + dayOfMonth + "/" + month + year, Toast.LENGTH_LONG).show();
            }
        });

        //Menampilkan WebView
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.detik.com/tag/fitness/");
        return view;
    }

    //Method untuk mengambil username dari database
    private void getData(){

        String url = DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                username = jo.getString(KEY_USERNAME);

                txtMessageF.setText("Hi, " + username + "!");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

 package com.uas.fitguru;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import java.util.Map;

 public class RatingFragment extends Fragment {
     Button btnSend;
     EditText edtComment;
     RatingBar ratingStars;
     TextView txtRate1, txtRate2, txtRate3;
     String message2;
     ProgressBar progressBar;
     int rating = 0;
     String myRating;

     String url_tambah = "http://192.168.2.5/FitGuru/insert_rating.php";
     public static final String DATA_URL = "http://192.168.2.5/FitGuru/search_login.php?";
     public static final String KEY_USERNAME = "username";
     public static final String JSON_ARRAY = "result";

     @Nullable
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_rating, container, false);

         //Hooks
         txtRate1 = v.findViewById(R.id.txtRate1);
         txtRate2 = v.findViewById(R.id.txtRate2);
         txtRate3 = v.findViewById(R.id.txtRate3);
         btnSend = v.findViewById(R.id.btnSend);
         edtComment = v.findViewById(R.id.edtComment);
         ratingStars = v.findViewById(R.id.ratingBar);
         progressBar = v.findViewById(R.id.progressBar);

         //Mendapatkan Informasi Username Yang Sedang Login Dari Database
         getData();

         //Saat Rating Diklik
         ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                 rating = (int) v;
                 String message = null;
                 switch (rating) {
                     case 1:
                         message = "Disappointed";
                         txtRate1.setText(message);
                         txtRate1.setTextColor(Color.parseColor("#CA2122"));
                         txtRate2.setText("Hi " + message2 + ", Please Kindly Help Us To Understand Your Problem");
                         txtRate3.setText("What Makes You Dissapointed?");
                         edtComment.setVisibility(View.VISIBLE);
                         myRating = "1";
                         break;
                     case 2:
                         message = "Unsatisfied";
                         txtRate1.setText(message);
                         txtRate1.setTextColor(Color.parseColor("#CA2122"));
                         txtRate2.setText("Hi " + message2 + ", Please Kindly Help Us To Understand Your Problem");
                         txtRate3.setText("Please Kindly Tell Us, What Makes You Unsatisfied?");
                         edtComment.setVisibility(View.VISIBLE);
                         myRating = "2";
                         break;
                     case 3:
                         message = "Neutral";
                         txtRate1.setText(message);
                         txtRate1.setTextColor(Color.parseColor("#F9B320"));
                         txtRate2.setText("Hi " + message2 + ", It Looks Like You Don't Like This Application, Can We Know Why?");
                         txtRate3.setText("What Should We Fix?");
                         edtComment.setVisibility(View.VISIBLE);
                         myRating = "3";
                         break;
                     case 4:
                         message = "Satisfied";
                         txtRate1.setText(message);
                         txtRate1.setTextColor(Color.parseColor("#65C20B"));
                         txtRate2.setText("Thank You " + message2 + "!, Let Us Know About Your Story");
                         txtRate3.setText("What Makes You Satisfied?");
                         edtComment.setVisibility(View.VISIBLE);
                         myRating = "4";
                         break;
                     case 5:
                         message = "Very Satisfied";
                         txtRate1.setText(message);
                         txtRate1.setTextColor(Color.parseColor("#65C20B"));
                         txtRate2.setText("Appreciate It " + message2 + "!, Let Us Know About Your Story");
                         txtRate3.setText("What Makes You Very Satisfied?");
                         edtComment.setVisibility(View.VISIBLE);
                         myRating = "5";
                         break;
                 }
             }
         });

         //Saat Button Send Diklik
         btnSend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (rating == 0) {
                     Toast.makeText(getActivity(), "Please Fill In The Rating", Toast.LENGTH_SHORT).show();
                 } else {
                     insertRating();
                     Intent i = new Intent(getActivity(), MainActivity.class);
                     i.putExtra("frgToLoad", 2);
                     startActivity(i);
                 }
             }
         });
         return v;
     }

     //Method Untuk Menginput Rating User Ke Database
     private void insertRating() {
         final String comment = edtComment.getText().toString();
         final String rate = myRating;

         progressBar.setVisibility(View.VISIBLE);
         RequestQueue queue = Volley.newRequestQueue(getActivity());
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url_tambah, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONObject jObj = new JSONObject(response);
                     int sukses = jObj.getInt("success");
                     if (sukses == 1) {
                         Toast.makeText(getActivity(), "Your Response Submitted", Toast.LENGTH_SHORT).show();
                     } else {
                         Toast.makeText(getActivity(), "Failed To Submit Your Response", Toast.LENGTH_SHORT).show();
                     }
                     progressBar.setVisibility(View.GONE);
                 } catch (Exception ex) {
                     Log.e("Error", ex.toString());
                     progressBar.setVisibility(View.GONE);
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.e("Error", error.getMessage());
                 Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
             }
         }) {
             @Override
             protected Map<String, String> getParams() {
                 Map<String, String> params = new HashMap<>();
                 params.put("rate", rate);
                 params.put("comment", comment);
                 return params;
             }

             @Override
             public Map<String, String> getHeaders() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 params.put("Content-Type", "application/x-www-form-urlencoded");
                 return params;
             }
         };
         queue.getCache().clear();
         queue.add(stringRequest);
     }

     //Method untuk mengambil username yang sedang login dari database
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
                 message2 = jo.getString(KEY_USERNAME);
             }


         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
 }
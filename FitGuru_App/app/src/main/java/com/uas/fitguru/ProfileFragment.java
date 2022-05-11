package com.uas.fitguru;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {
    public static String KEY_ACTIVITY = "msg_activity";
    EditText edtUsername, edtFullname, edtPassword, edtEmail, edtPhone, edtAddress, edtWeight;
    Button btnUpdate, btnDelete;
    EditText txtvalue;

    private String username;

    public static final String url_update = "http://192.168.2.5/FitGuru/update.php";
    public static final String url_delete = "http://192.168.2.5/FitGuru/delete.php";
    public static final String DATA_URL_USERNAME = "http://192.168.2.5/FitGuru/search_login.php";
    public static final String DATA_URL = "http://192.168.2.5/FitGuru/search.php?username=";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_PASSWORD =  "password";
    public static final String KEY_EMAIL =  "email";
    public static final String KEY_PHONE =  "phone";
    public static final String KEY_ADDRESS =  "address";
    public static final String KEY_WEIGHT =  "weight";
    public static final String JSON_ARRAY = "result";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        //Hooks
        txtvalue = (EditText)v.findViewById(R.id.editText);
        edtUsername = (EditText)v.findViewById(R.id.edtUsername);
        edtFullname = (EditText)v.findViewById(R.id.edtFullname);
        edtPassword = (EditText)v.findViewById(R.id.edtPassword);
        edtEmail = (EditText)v.findViewById(R.id.edtEmail);
        edtPhone = (EditText)v.findViewById(R.id.edtPhone);
        edtAddress = (EditText)v.findViewById(R.id.edtAddress);
        edtWeight = (EditText)v.findViewById(R.id.edtWeight);
        btnUpdate = (Button)v.findViewById(R.id.btnUpdate);
        btnDelete = (Button)v.findViewById(R.id.btnDelete);

        getDataUsername();

        //Saat Button Update Diklik
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        //Saat Button Delete Diklik
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Confirmation");
                dialog.setMessage("Are You Sure Want To Delete Your Account? (Your Account Will Be Deleted Permanent)");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                        Intent i = new Intent(getActivity(), Login.class);
                        startActivity(i);
                    }

                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
        return v;
    }

    //Method Untuk Mengambil Semua Data User Dari Database
    private void getData(){
        String value = txtvalue.getText().toString().trim();

        if (value.equals("")) {
            Toast.makeText(getActivity(), "Please Enter Data Value", Toast.LENGTH_LONG).show();
            return;
        }

        String url = DATA_URL + value;

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
                String username = jo.getString(KEY_USERNAME);
                String fullname = jo.getString(KEY_FULLNAME);
                String password = jo.getString(KEY_PASSWORD);
                String email = jo.getString(KEY_EMAIL);
                String phone = jo.getString(KEY_PHONE);
                String address = jo.getString(KEY_ADDRESS);
                String weight = jo.getString(KEY_WEIGHT);

                edtUsername.setText(username);
                edtFullname.setText(fullname);
                edtPassword.setText(password);
                edtEmail.setText(email);
                edtPhone.setText(phone);
                edtAddress.setText(address);
                edtWeight.setText(weight);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Method Untuk Update Data User
    private void update(){
        final String username = edtUsername.getText().toString();
        final String fullname = edtFullname.getText().toString();
        final String password = edtPassword.getText().toString();
        final String email = edtEmail.getText().toString();
        final String phone = edtPhone.getText().toString();
        final String address = edtAddress.getText().toString();
        final String weight = edtWeight.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    int sukses = jObj.getInt("success");
                    if (sukses == 1) {
                        Toast.makeText(getActivity(), "Data Berhasil Diupdate!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Data Gagal Diupdate, Silahkan Coba Kembali.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(getActivity(), "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("fullname", fullname);
                params.put("password", password);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address", address);
                params.put("weight", weight);
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

    //Method Untuk Menghapus Akun User
    private void delete(){
        final String username = edtUsername.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    int sukses = jObj.getInt("success");
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(getActivity(), "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
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

    //Method Untuk Mendapatkan Informasi Mengenai User Yang Sedang Login Sekarang
    private void getDataUsername(){

        String url = DATA_URL_USERNAME;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSONUsername(response);
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

    private void showJSONUsername(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                username = jo.getString(KEY_USERNAME);

                txtvalue.setText(username);
                getData();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
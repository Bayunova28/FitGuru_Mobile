package com.uas.fitguru;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductFragment extends Fragment {
    SearchView searchView;
    SimpleAdapter adapter;
    ListView listProduct;
    public static final String DATA_URL = "http://192.168.2.5/FitGuru/search_product.php";
    public static final String KEY_Product_Name = "product_name";
    public static final String KEY_Product_Price = "product_price";
    public static final String KEY_Product_Description =  "product_description";
    public static final String KEY_Product_Picture =  "product_picture";
    public static final String JSON_ARRAY = "result";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product,container,false);

        //Hooks
        searchView = (SearchView)v.findViewById(R.id.searchView);
        listProduct = (ListView)v.findViewById(R.id.listProduct);

        getData();
        return v;
    }

    private void getData(){

        String url = DATA_URL;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String product_name = jo.getString(KEY_Product_Name);
                String product_price = jo.getString(KEY_Product_Price);
                String product_description = jo.getString(KEY_Product_Description);
                String product_picture = jo.getString(KEY_Product_Picture);



                final HashMap<String, String> employees = new HashMap<>();
                employees.put(KEY_Product_Name,  product_name);
                employees.put(KEY_Product_Price, product_price);
                employees.put(KEY_Product_Description, product_description);
                employees.put(KEY_Product_Picture, product_picture);

                list.add(employees);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new SimpleAdapter(
                getActivity(), list, R.layout.activity_mylist,
                new String[]{KEY_Product_Name, KEY_Product_Price, KEY_Product_Description, KEY_Product_Picture},
                new int[]{R.id.product_name, R.id.product_price, R.id.product_description, R.id.product_picture});

        listProduct.setAdapter(adapter);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);

                return false;
            }
        });

    }
}

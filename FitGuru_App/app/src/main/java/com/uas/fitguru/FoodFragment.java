package com.uas.fitguru;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodFragment extends ListFragment implements AdapterView.OnItemLongClickListener {
    private MyDBHandler dbHandler;
    EditText edtCaloriesUse, edtCaloriesBeginning, edtCaloriesRemaining;
    private ArrayList<Food> values;
    private Button btnEdit;
    private Button btnDelete;
    Button btnReset, btnCalculate;

    public static final String url_update = "http://192.168.2.5/FitGuru/update_calories.php";
    public static final String DATA_URL = "http://192.168.2.5/FitGuru/search_calories.php";
    public static final String KEY_CALORIES = "calories";
    public static final String JSON_ARRAY = "result";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);

        //Hooks
        edtCaloriesUse = (EditText) v.findViewById(R.id.edtCaloriesUse);
        edtCaloriesBeginning = (EditText) v.findViewById(R.id.edtCaloriesBeginning);
        edtCaloriesRemaining = (EditText) v.findViewById(R.id.edtCaloriesRemaining);
        btnReset = v.findViewById(R.id.btnReset2);
        btnCalculate = v.findViewById(R.id.btnCalculate);

        //Buat objek untuk Class MyDBHandler
        dbHandler = new MyDBHandler(getActivity());

        //Membuka koneksi database
        try {
            dbHandler.open();
            getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Menjumlahkan Calories User
        int totalcalories = 0;
        totalcalories = dbHandler.getTotal();
        edtCaloriesUse.setText(totalcalories + "");

        //Menampilkan Informasi Food Pada ListView
        values = dbHandler.getAllFood();
        final ArrayAdapter<Food> adapter = new ArrayAdapter<Food>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        ListView list = (ListView) v.findViewById(android.R.id.list);
        list.setOnItemLongClickListener(this);

        final int finalTotalcalories = totalcalories;

        //Saat Button Calculate Diklik
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                if (TextUtils.isEmpty(edtCaloriesBeginning.getText().toString())){
                    Toast.makeText(getActivity(), "Please Fill In Your Daily Calories Goal", Toast.LENGTH_SHORT).show();
                }else{
                    //Menghitung Calories Remaining
                    String temp =   edtCaloriesBeginning.getText().toString();
                    int CaloriesBeginning = 0;
                    CaloriesBeginning = Integer.parseInt(temp);
                    int hasil = CaloriesBeginning - finalTotalcalories;
                    Integer converthasil = new Integer(hasil);
                    String finalangkahasil = converthasil.toString();

                    edtCaloriesRemaining.setText(finalangkahasil);

                }
            }
        });

        //Saat Button Rest Diklik
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Reset Food");
                dialog.setMessage("Are you sure want to reset all food?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Menghapus semua Food
                        dbHandler.deleteAllFood();
                        Toast.makeText(getActivity(), "Food Succesfully Reset", Toast.LENGTH_SHORT).show();
                        //Kembali ke FoodFragment
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        i.putExtra("frgToLoad", 1);
                        startActivity(i);
                        dbHandler.close();
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

        //Saat Floating Button Diklik
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Berpindah ke AddFood
                Intent i = new Intent(getActivity(), AddFood.class);
                startActivity(i);
            }
        });
        return v;
    }

    //Method yang digunakan jika ListView ditekan agak lama
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int
            i, long l) {
        //Menampilkan dialog dan mengambil layout dari dialog.xml
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog);
        dialog.show();

        final Food barang = (Food) getListAdapter().getItem(i);
        final long id = barang.getID();

        btnEdit = dialog.findViewById(R.id.btnEdit);
        btnDelete = dialog.findViewById(R.id.btnHapus);

        //Method yang digunakan jika Button Edit pada dialog.xml ditekan
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food barang = dbHandler.getFood(id);
                Intent i = new Intent(getActivity(), EditFood.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", barang.getID());
                bundle.putString("foodName", barang.getFoodName());
                bundle.putString("foodDescription", barang.getFoodDescription());
                bundle.putLong("foodCalories", barang.getFoodCalories());
                i.putExtras(bundle);
                startActivity(i);
                dialog.dismiss();
            }
        });
        //Method yang digunakan jika Button Delete pada dialog.xml ditekan
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder konfirm = new AlertDialog.Builder(getActivity());
                konfirm.setTitle("Delete Food");
                konfirm.setMessage("Are You Sure Want To Delete This Food?");
                konfirm.setPositiveButton("Yes", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHandler.deleteFood(id);
                                Toast.makeText(getActivity(), "Food Successfully Deleted", Toast.LENGTH_SHORT).show();

                                //Kembali ke FoodFragment
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                i.putExtra("frgToLoad", 1);
                                startActivity(i);
                                dbHandler.close();
                            }
                        });
                konfirm.setNegativeButton("No", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                konfirm.show();
                dialog.dismiss();
            }
        });
        return true;
    }

    //Method untuk mengambil Target Food dari database
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
                String calories = jo.getString(KEY_CALORIES);
                edtCaloriesBeginning.setText(calories);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Method untuk update nilai target calories
    private void update(){
        final String calories = edtCaloriesBeginning.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
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
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("calories", calories);
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
}

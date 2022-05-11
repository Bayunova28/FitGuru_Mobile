package com.uas.fitguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditFood extends AppCompatActivity {
    private long id;
    private String foodName;
    private String foodDescription;
    private long foodCalories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_food);

        //Hooks
        final EditText edtName = (EditText)findViewById(R.id.editName);
        final EditText edtDescription = (EditText)findViewById(R.id.editDescription);
        final EditText edtCalories = (EditText)findViewById(R.id.editCalories);
        Button btnReset = (Button)findViewById(R.id.btnReset);
        Button btnSimpan = (Button)findViewById(R.id.btnSimpan);

        //Buat objek untuk Class MyDBHandler
        final MyDBHandler dbHandler = new MyDBHandler(this);

        //Membuka koneksi database
        try { dbHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Buat objek untuk class Bundle
        Bundle bundle = this.getIntent().getExtras();
        //Mengambil Nilai Untuk Ditampilkan
        id = bundle.getLong("id");
        foodName = bundle.getString("foodName");
        foodDescription = bundle.getString("foodDescription");
        foodCalories = bundle.getLong("foodCalories");
        //Menampilkan Nilai ke editText
        this.setTitle("Edit Food ID: "+id);
        edtName.setText(foodName);
        edtDescription.setText(foodDescription);
        edtCalories.setText(Long.toString(foodCalories));

        //Saat Button Simpan Diklik
        btnSimpan.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View view) {
            //Buat objek untuk class Food
            Food food = new Food();
            //Mengirimkan id ke class Food
            food.setID(id);

            if (edtName.getText().toString().isEmpty() || edtDescription.getText().toString().isEmpty() || edtCalories.getText().toString().isEmpty()){
                Toast.makeText(EditFood.this, "Please Fill In The Input", Toast.LENGTH_SHORT).show();
            }else {
                //Mengambil nilai
                String Bname = edtName.getText().toString();
                String Bdescription = edtDescription.getText().toString();
                long Bcalories = Long.parseLong(edtCalories.getText().toString());
                //Mengirimkan nilai ke class Food
                food.setFoodName(Bname);
                food.setFoodDescription(Bdescription);
                food.setFoodCalories(Bcalories);
                //Menjalankan update data
                dbHandler.updateFood(food);

                Toast.makeText(EditFood.this, "Food Successfully Updated",Toast.LENGTH_LONG).show();

                //Kembali ke FoodFragment
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("frgToLoad", 1);
                startActivity(i);
                EditFood.this.finish();
                dbHandler.close();
            }
        }
        });

        //Saat Button Rest Diklik
        btnReset.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View view) {
            edtName.setText("");
            edtDescription.setText("");
            edtCalories.setText("");
            edtName.requestFocus();
        }
        });
    }
}
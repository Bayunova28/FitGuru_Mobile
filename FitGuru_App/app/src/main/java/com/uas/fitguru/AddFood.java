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

public class AddFood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_food);

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

        //Saat Button Simpan Diklik
        btnSimpan.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View view) {

            if (edtName.getText().toString().isEmpty() || edtDescription.getText().toString().isEmpty() || edtCalories.getText().toString().isEmpty()){
                Toast.makeText(AddFood.this, "Please Fill In The Input", Toast.LENGTH_SHORT).show();
            }else {
                //Buat objek untuk Class Food
                Food food = new Food();
                //Mengambil nilai
                String bName = edtName.getText().toString();
                String bDescription = edtDescription.getText().toString();
                long bCalories = Long.parseLong(edtCalories.getText().toString());

                //Menambahkan Food ke database
                dbHandler.createFood(bName, bDescription, bCalories);
                Toast.makeText(AddFood.this, "Food Successfully Added", Toast.LENGTH_LONG).show();

                //Mengosongkan Field
                edtName.setText("");
                edtDescription.setText("");
                edtCalories.setText("");
                edtName.requestFocus();

                //Kembali ke FoodFragment
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("frgToLoad", 1);
                startActivity(i);
                AddFood.this.finish();
                dbHandler.close();

            }
        }
        });
        //Saat Button Reset Diklik
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
package com.uas.fitguru;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WorkoutFragment extends Fragment {
    ListView listView;
    String mTitle[] = {"Push Up", "Body Building", "Yoga", "Power Lifting"};
    String mDescription[] = {"Push ups are a type of strength exercise that serves to strengthen the biceps and triceps.", "Bodybuilding is a body building activity that involves intensive muscle hypertrophy.", "Yoga is an exercise of body and mind that focuses on strength, flexibility, and breathing.", "Powerlifting is a sport that aims to increase maximum strength and endurance."};
    int images[] = {R.drawable.pushup, R.drawable.bodybuilding, R.drawable.yoga, R.drawable.power};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workout,container,false);

        //Hooks
        listView = v.findViewById(R.id.listView);
        //Buat objek untuk class MyAdapter
        MyAdapter adapter = new MyAdapter(getActivity(), mTitle, mDescription, images);
        listView.setAdapter(adapter);

        //Saat listview Diklik
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    //Mengirimkan position == 0 ke WorkoutDetailActivity
                    Intent intent = new Intent(getActivity(), WorkoutDetailActivity.class);
                    intent.putExtra("title", mTitle[0]);
                    intent.putExtra("position", "0");
                    startActivity(intent);
                }
                if (position ==  1) {
                    //Mengirimkan position == 1 ke WorkoutDetailActivity
                    Intent intent = new Intent(getActivity(), WorkoutDetailActivity.class);
                    intent.putExtra("title", mTitle[1]);
                    intent.putExtra("position", "1");
                    startActivity(intent);
                }
                if (position ==  2) {
                    //Mengirimkan position == 2 ke WorkoutDetailActivity
                    Intent intent = new Intent(getActivity(), WorkoutDetailActivity.class);
                    intent.putExtra("title", mTitle[2]);
                    intent.putExtra("position", "2");
                    startActivity(intent);
                }
                if (position ==  3) {
                    //Mengirimkan position == 3 ke WorkoutDetailActivity
                    Intent intent = new Intent(getActivity(), WorkoutDetailActivity.class);
                    intent.putExtra("title", mTitle[3]);
                    intent.putExtra("position", "3");
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    //Class Custom Adapter
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            return row;
        }
    }
}

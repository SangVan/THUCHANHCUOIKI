package com.example.thuchanhcuoiki;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int Layout;
    private List<Course> courseList;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-ae026-default-rtdb.firebaseio.com");
    DatabaseReference myRef = database.getReference("course");

    public MyAdapter(Context context, int layout, List<Course> courseList) {
        this.context = context;
        Layout = layout;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(Layout, null);

        TextView txtTilte = view.findViewById(R.id.txtName);
        Course item = courseList.get(i);
        txtTilte.setText(item.getName());


        ImageView btnRemove = view.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(courseList.get(i).getId() + "").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Remove Success", "");
                            Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Xoa khong thanh cong!!!", "");
                        }
                    }
                });
            }
        });

        return view;
    }
}

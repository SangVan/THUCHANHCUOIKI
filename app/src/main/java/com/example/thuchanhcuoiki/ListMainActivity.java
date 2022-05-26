package com.example.thuchanhcuoiki;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListMainActivity extends AppCompatActivity {

    ListView lvCourse;
    ArrayList<Course> arrayList;
    MyAdapter adapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-ae026-default-rtdb.firebaseio.com");
    DatabaseReference myRef = database.getReference("course");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        initUI();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edtTodo = findViewById(R.id.txtName);
                int length = arrayList.size();

            Course todo = new Course(edtTodo.getText().toString());
                todo.setId(length);

                String pathOject = String.valueOf(todo.getId());

                myRef.child(pathOject).setValue(todo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ListMainActivity.this, "Add success", Toast.LENGTH_SHORT).show();
                        loadDataFromFirebase();
                    }
                });
            }
        });
    }

    private void initUI() {
        lvCourse = findViewById(R.id.lvMain);
        arrayList = new ArrayList<>();

        loadDataFromFirebase();

        adapter = new MyAdapter(this, R.layout.list_item, arrayList);
        lvCourse.setAdapter(adapter);
    }

    private void loadDataFromFirebase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Course> listTodo = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                   Course todo = data.getValue(Course.class);
                    listTodo.add(todo);
                }
                arrayList.clear();
                arrayList.addAll(listTodo);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
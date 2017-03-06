package com.gyt.adapterview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoodsSpecLayout goodsSpecLayout = (GoodsSpecLayout) findViewById(R.id.goods_spec_layout);
        List<Student> lists = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            Student student = new Student();
            student.setAge(i + 2);
            student.setName("小李" + i);
            lists.add(student);
        }

        SpecAdapter<Student> specAdapter = new SpecAdapter<Student>(lists) {
            @Override
            public View getView(FlowLayout parent, int position, Student student) {

                TextView textView = new TextView(MainActivity.this);
                textView.setText(student.getName() + " :: " + student.getAge());
                textView.setBackgroundColor(Color.GRAY);
                return textView;
            }
        };

        goodsSpecLayout.setAdapter(specAdapter);

    }
}

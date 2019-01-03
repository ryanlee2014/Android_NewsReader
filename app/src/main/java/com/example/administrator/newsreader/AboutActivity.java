package com.example.administrator.newsreader;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.appImage)
    ImageView imageView;
    @BindView(R.id.program_name)
    TextView program_name;
    @BindView(R.id.statement)
    TextView statement;
    @BindView(R.id.author_name)
    TextView author;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        Glide.with(getApplicationContext())
                .load(R.mipmap.ic_launcher)
                .into(imageView);
        program_name.setText("Android Reader");
        statement.setText("A JSON-base NewsReader using TIANAPI");
        author.setText("Designer: 廖鹏城 李昊元");
    }
}

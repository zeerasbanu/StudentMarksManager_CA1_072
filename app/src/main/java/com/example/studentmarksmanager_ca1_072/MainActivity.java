
package com.example.studentmarksmanager_ca1_072;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvHomeSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddStudent =
                findViewById(R.id.btnAddStudent);

        Button btnManageStudents =
                findViewById(R.id.btnManageStudents);

        tvHomeSummary = findViewById(R.id.tvHomeSummary);

        btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddStudentActivity.class);

            startActivity(intent);
        });

        btnManageStudents.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ManageStudentsActivity.class);

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (tvHomeSummary != null) {
            tvHomeSummary.setText(
                    "Total Students: "
                            + StudentData.students.size());
        }
    }
}

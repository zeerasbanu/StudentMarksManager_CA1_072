
package com.example.studentmarksmanager_ca1_072;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etMath;
    private EditText etScience;
    private EditText etEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.etName);
        etMath = findViewById(R.id.etMath);
        etScience = findViewById(R.id.etScience);
        etEnglish = findViewById(R.id.etEnglish);

        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveStudent());
    }

    private void saveStudent() {

        String name = etName.getText()
                .toString().trim();

        String mathText = etMath.getText()
                .toString().trim();

        String scienceText = etScience.getText()
                .toString().trim();

        String englishText = etEnglish.getText()
                .toString().trim();

        if (name.isEmpty() || mathText.isEmpty()
                || scienceText.isEmpty()
                || englishText.isEmpty()) {

            Toast.makeText(this,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double math = Double.parseDouble(mathText);
            double science = Double.parseDouble(scienceText);
            double english = Double.parseDouble(englishText);

            if (!validMark(math)
                    || !validMark(science)
                    || !validMark(english)) {

                Toast.makeText(this,
                        "Marks must be between 0 and 100",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Student student = new Student(
                    name, math, science, english);

            StudentData.students.add(student);

            Toast.makeText(this,
                    "Student added successfully",
                    Toast.LENGTH_SHORT).show();

            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Please enter valid marks",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validMark(double mark) {
        return Double.isFinite(mark)
                && mark >= 0 && mark <= 100;
    }
}

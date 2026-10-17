
package com.example.studentmarksmanager_ca1_072;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ManageStudentsActivity extends AppCompatActivity {

    private LinearLayout studentContainer;
    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        studentContainer =
                findViewById(R.id.studentContainer);

        tvSummary = findViewById(R.id.tvSummary);

        displayStudents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayStudents();
    }

    private void displayStudents() {

        studentContainer.removeAllViews();

        int count = StudentData.students.size();
        double totalAverage = 0;

        for (int i = 0; i < count; i++) {

            Student student = StudentData.students.get(i);
            totalAverage += student.getAverage();

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(20, 16, 20, 16);
            card.setBackgroundColor(0xFFEAF2F8);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 8, 0, 16);
            card.setLayoutParams(params);

            TextView details = new TextView(this);
            details.setTextSize(16);

            details.setText(
                    "Name: " + student.name
                            + "\nMathematics: " + student.math
                            + "\nScience: " + student.science
                            + "\nEnglish: " + student.english
                            + "\nTotal: " + student.getTotal()
                            + "\nAverage: " + String.format(
                            Locale.US, "%.2f",
                            student.getAverage())
                            + "\nGrade: " + student.getGrade());

            card.addView(details);

            final int position = i;

            Button btnEdit = new Button(this);
            btnEdit.setText("Edit Student");
            card.addView(btnEdit);

            btnEdit.setOnClickListener(v ->
                    editStudent(position));

            Button btnDelete = new Button(this);
            btnDelete.setText("Delete Student");
            card.addView(btnDelete);

            btnDelete.setOnClickListener(v ->
                    confirmDelete(position));

            studentContainer.addView(card);
        }

        double classAverage = count == 0
                ? 0 : totalAverage / count;

        tvSummary.setText(
                "Number of Students: " + count
                        + "\nClass Average: "
                        + String.format(Locale.US, "%.2f",
                        classAverage));
    }

    private void editStudent(int position) {

        Student student = StudentData.students.get(position);

        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(24, 8, 24, 8);

        EditText nameInput = new EditText(this);
        nameInput.setSingleLine(true);
        nameInput.setHint("Student Name");
        nameInput.setText(student.name);
        form.addView(nameInput);

        EditText mathInput =
                createMarkInput("Mathematics", student.math);
        form.addView(mathInput);

        EditText scienceInput =
                createMarkInput("Science", student.science);
        form.addView(scienceInput);

        EditText englishInput =
                createMarkInput("English", student.english);
        form.addView(englishInput);

        new AlertDialog.Builder(this)
                .setTitle("Update Student")
                .setView(form)
                .setPositiveButton("Update", (dialog, which) -> {

                    String name = nameInput.getText()
                            .toString().trim();

                    String mathText = mathInput.getText()
                            .toString().trim();

                    String scienceText = scienceInput.getText()
                            .toString().trim();

                    String englishText = englishInput.getText()
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
                        double math =
                                Double.parseDouble(mathText);

                        double science =
                                Double.parseDouble(scienceText);

                        double english =
                                Double.parseDouble(englishText);

                        if (!validMark(math)
                                || !validMark(science)
                                || !validMark(english)) {

                            Toast.makeText(this,
                                    "Marks must be between 0 and 100",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        student.name = name;
                        student.math = math;
                        student.science = science;
                        student.english = english;

                        displayStudents();

                        Toast.makeText(this,
                                "Student updated successfully",
                                Toast.LENGTH_SHORT).show();

                    } catch (NumberFormatException e) {
                        Toast.makeText(this,
                                "Enter valid numeric marks",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private EditText createMarkInput(
            String subject, double mark) {

        EditText input = new EditText(this);
        input.setHint(subject + " Marks");

        input.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_FLAG_DECIMAL
                        | InputType.TYPE_NUMBER_FLAG_SIGNED);

        input.setSingleLine(true);
        input.setText(String.valueOf(mark));

        return input;
    }

    private boolean validMark(double mark) {
        return Double.isFinite(mark)
                && mark >= 0 && mark <= 100;
    }

    private void confirmDelete(int position) {

        Student student = StudentData.students.get(position);

        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage(
                        "Are you sure you want to delete "
                                + student.name + "?")
                .setPositiveButton("Delete", (dialog, which) -> {

                    StudentData.students.remove(position);
                    displayStudents();

                    Toast.makeText(this,
                            "Student deleted successfully",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

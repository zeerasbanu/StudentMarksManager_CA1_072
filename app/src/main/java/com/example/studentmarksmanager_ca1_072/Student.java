
package com.example.studentmarksmanager_ca1_072;

public class Student {

    public String name;
    public double math;
    public double science;
    public double english;

    public Student(String name, double math,
                   double science, double english) {
        this.name = name;
        this.math = math;
        this.science = science;
        this.english = english;
    }

    public double getTotal() {
        return math + science + english;
    }

    public double getAverage() {
        return getTotal() / 3.0;
    }

    public String getGrade() {
        double average = getAverage();

        if (average >= 75) {
            return "A";
        } else if (average >= 65) {
            return "B";
        } else if (average >= 50) {
            return "C";
        } else if (average >= 35) {
            return "S";
        } else {
            return "F";
        }
    }
}

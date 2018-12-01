package com.exercise;

import java.util.ArrayList;

public class RowDataModel {
    ArrayList<Integer> values = new ArrayList<>();

    public RowDataModel() {
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void addValue (int value)
    {
        values.add(value);
    }
}

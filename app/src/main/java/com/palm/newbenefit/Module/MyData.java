package com.palm.newbenefit.Module;

import jie.com.funnellib.IFunnelData;

public class MyData implements IFunnelData {
    public String label;
    public int color;

    public String num;

    public MyData() {
    }

    public MyData(String label, int color, String num) {
        this.label = label;
        this.color = color;
        this.num = num;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "MyData{" +
                "label='" + label + '\'' +
                ", color=" + color +
                ", num='" + num + '\'' +
                '}';
    }
}

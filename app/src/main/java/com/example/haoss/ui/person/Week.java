package com.example.haoss.ui.person;

import android.widget.CheckBox;

/**
 * <p>星期</p>
 *
 * @author chentt
 * @version 1.0.0 2017/3/31.
 */
public class Week {

    private String weekName;
    private CheckBox checkBox;
    private boolean isSelect;

    public Week(String weekName) {
        this.weekName = weekName;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}

package com.example.projectui_yeon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CheckableLayout extends ConstraintLayout implements Checkable {
    public CheckableLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = findViewById(R.id.checkBox2);

        cb.setChecked(checked);

    }

    @Override
    public boolean isChecked() {
        CheckBox cb = findViewById(R.id.checkBox2);
        return cb.isChecked();
    }

    @Override
    public void toggle() {
        CheckBox cb=findViewById(R.id.checkBox2);

        setChecked(cb.isChecked()? false:true);

    }
}

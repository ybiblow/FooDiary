package dev.jacob_ba.foodiary.handlers;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FloatingActionButtonHandler {

    private ExtendedFloatingActionButton fab;
    private static FloatingActionButtonHandler fabInstance;

    public static FloatingActionButtonHandler getInstance() {
        if (fabInstance == null) {
            fabInstance = new FloatingActionButtonHandler();
        }
        return fabInstance;
    }

    public void setFab(ExtendedFloatingActionButton fab) {
        this.fab = fab;
    }

    public ExtendedFloatingActionButton getFab() {
        return fab;
    }
}

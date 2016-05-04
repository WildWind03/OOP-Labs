package ru.nsu.ccfit.chirikhin.factoryview;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField
{
    private int maxValue = Integer.MAX_VALUE;

    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
            checkForMaxAndEmptyString();
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
            checkForMaxAndEmptyString();
        }
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    private boolean validate(String text)
    {
        return text.matches("[0-9]*");

    }

    private void checkForMaxAndEmptyString() {
        if (getText().equals("")) {
            setText("0");
        }

        if (Integer.parseInt(getText()) > maxValue) {
            setText(Integer.toString(maxValue));
        }
    }
}
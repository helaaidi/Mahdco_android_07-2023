package com.example.loginregister;

public class MyTask {
    public MyTask() {
    }

    private String txtCode;

    private boolean checked;

    public MyTask(String txtCode,boolean checked) {
        this.txtCode = txtCode;
        this.checked = checked;

    }

    public void setTxtCode(String txtCode) {
        this.txtCode = txtCode;
    }

    public String getTxtCode() {
        return txtCode;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

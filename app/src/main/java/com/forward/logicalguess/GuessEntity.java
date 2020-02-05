package com.forward.logicalguess;

public class GuessEntity {
    private String data;
    private boolean isSuccess;
    private int numA;
    private int numB;

    public GuessEntity() {
        isSuccess = false;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNumA() {
        return numA;
    }

    public void setNumA(int numA) {
        this.numA = numA;
    }

    public int getNumB() {
        return numB;
    }

    public void setNumB(int numB) {
        this.numB = numB;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String toResult() {
        if (isSuccess) {
            return data.concat("     ") + numA + "A" + numB + "B".concat(" ").concat("success");
        } else {
            return data.concat("     ") + numA + "A" + numB + "B";
        }
    }
}

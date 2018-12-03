package com.example.hpsus.parking;


import com.orm.SugarRecord;

public class Car  extends SugarRecord {
    private Integer caronoff1;
    private Integer caronoff2;
    private Integer caronoff3;
    private Integer caronoff4;
    private Integer caronoff5;
    private Integer caronoff6;
    private Integer caronoff7;
    private Integer caronoff8;
    private Integer caronoff9;
    private Integer caronoff10;
    public Car() {

    }

    public Car(Integer caronoff1, Integer caronoff2, Integer caronoff3, Integer caronoff4, Integer caronoff5, Integer caronoff6, Integer caronoff7, Integer caronoff8, Integer caronoff9, Integer caronoff10) {
        this.caronoff1 = caronoff1;
        this.caronoff2 = caronoff2;
        this.caronoff3 = caronoff3;
        this.caronoff4 = caronoff4;
        this.caronoff5 = caronoff5;
        this.caronoff6 = caronoff6;
        this.caronoff7 = caronoff7;
        this.caronoff8 = caronoff8;
        this.caronoff9 = caronoff9;
        this.caronoff10 = caronoff10;
    }

    public Integer getCaronoff1() {
        return caronoff1;
    }

    public void setCaronoff1(Integer caronoff1) {
        this.caronoff1 = caronoff1;
    }

    public Integer getCaronoff2() {
        return caronoff2;
    }

    public void setCaronoff2(Integer caronoff2) {
        this.caronoff2 = caronoff2;
    }

    public Integer getCaronoff3() {
        return caronoff3;
    }

    public void setCaronoff3(Integer caronoff3) {
        this.caronoff3 = caronoff3;
    }

    public Integer getCaronoff4() {
        return caronoff4;
    }

    public void setCaronoff4(Integer caronoff4) {
        this.caronoff4 = caronoff4;
    }

    public Integer getCaronoff5() {
        return caronoff5;
    }

    public void setCaronoff5(Integer caronoff5) {
        this.caronoff5 = caronoff5;
    }

    public Integer getCaronoff6() {
        return caronoff6;
    }

    public void setCaronoff6(Integer caronoff6) {
        this.caronoff6 = caronoff6;
    }

    public Integer getCaronoff7() {
        return caronoff7;
    }

    public void setCaronoff7(Integer caronoff7) {
        this.caronoff7 = caronoff7;
    }

    public Integer getCaronoff8() {
        return caronoff8;
    }

    public void setCaronoff8(Integer caronoff8) {
        this.caronoff8 = caronoff8;
    }

    public Integer getCaronoff9() {
        return caronoff9;
    }

    public void setCaronoff9(Integer caronoff9) {
        this.caronoff9 = caronoff9;
    }

    public Integer getCaronoff10() {
        return caronoff10;
    }

    public void setCaronoff10(Integer caronoff10) {
        this.caronoff10 = caronoff10;
    }

    @Override
    public String toString() {
        String s=caronoff1.toString()+","+caronoff2.toString()+","+caronoff3.toString()+","+
                caronoff4.toString()+","+caronoff5.toString()+","+caronoff6.toString()+","+
                caronoff7.toString()+","+caronoff8.toString()+","+caronoff9.toString()+","+
                caronoff10.toString();
        return s;
    }
}

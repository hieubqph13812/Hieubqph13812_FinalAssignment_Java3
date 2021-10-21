/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package New_Class;

/**
 *
 * @author BUI QUANG HIEU
 */
public class Grade {

    private int id;
    private String msv;
    private float tiengAnh, tinHoc, GDTC;

    public Grade() {
    }

    public Grade(int id, String msv, float tiengAnh, float tinHoc, float GDTC) {
        this.id = id;
        this.msv = msv;
        this.tiengAnh = tiengAnh;
        this.tinHoc = tinHoc;
        this.GDTC = GDTC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmasv() {
        return msv;
    }

    public void setmsv(String msv) {
        this.msv = msv;
    }

    public float getTiengAnh() {
        return tiengAnh;
    }

    public void setTiengAnh(float tiengAnh) {
        this.tiengAnh = tiengAnh;
    }

    public float getTinHoc() {
        return tinHoc;
    }

    public void setTinHoc(float tinHoc) {
        this.tinHoc = tinHoc;
    }

    public float getGDTC() {
        return GDTC;
    }

    public void setGDTC(float GDTC) {
        this.GDTC = GDTC;
    }

    public float DTB() {
        return (getTiengAnh() + getTinHoc() + getGDTC()) / 3;
    }
}

package br.com.vagners.sendmoney.model;

import java.io.Serializable;

/**
 * Created by vagnerss on 26/08/16.
 */
public class ContactModel implements Serializable {

    private Integer id;
    private String name;
    private String phone;
    private String photo;

    public ContactModel() {
    }

    public ContactModel(Integer _id, String _name, String _phone, String _photo) {
        this.id = _id;
        this.name = _name;
        this.phone = _phone;
        this.photo = _photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

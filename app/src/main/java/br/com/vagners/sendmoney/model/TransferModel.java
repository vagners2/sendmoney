package br.com.vagners.sendmoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vagnerss on 25/08/16.
 */
public class TransferModel implements Serializable {

    @SerializedName("Id")
    @Expose
    private Integer id;

    private String name;
    private String phone;
    private String photo;

    @SerializedName("ClienteId")
    @Expose
    private Integer clientId;

    @SerializedName("Valor")
    @Expose
    private Double value;

    @SerializedName("Token")
    @Expose
    private String token;

    @SerializedName("Data")
    @Expose
    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

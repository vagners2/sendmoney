package br.com.vagners.sendmoney.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.vagners.sendmoney.model.ContactModel;
import br.com.vagners.sendmoney.model.TransferModel;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vagnerss on 25/08/16.
 */
public class ServiceAPI {

    private static final String BASE_URL = "http://processoseletivoneon.azurewebsites.net";
    private static ServiceAPI instance = null;

    public static Retrofit retrofit;
    public static NeonAPI api;

    public static ServiceAPI getInstance() {
        if (instance == null) {
            Class _class = ServiceAPI.class;
            synchronized (_class) {
                instance = new ServiceAPI();
            }
        }
        return instance;
    }

    public ServiceAPI() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(NeonAPI.class);
    }

    public void getToken(String name, String email, Callback<String> callback) {
        api.generateToken(name, email).enqueue(callback);
    }

    public void sendMoney(String token, String clientid, Double value, Callback<Boolean> callback) {
        api.sendMoney(clientid, token, value).enqueue(callback);
    }

    public void getTransfers(String token, Callback<List<TransferModel>> callback) {
        api.getTransfers(token).enqueue(callback);
    }

    public List<ContactModel> getContacts() {
        List<ContactModel> list = new ArrayList<>();
        list.add(new ContactModel(0, "Bruce Wayne", "(11) 98453-3245", "user_00.png"));
        list.add(new ContactModel(1, "Scarlett Johansson", "(11) 92332-2415", "user_01.png"));
        list.add(new ContactModel(2, "Bruce Dickinson", "(11) 92332-2415", "user_02.png"));
        list.add(new ContactModel(3, "Barack Obama", "(11) 94112-4525", "user_03.png"));
        list.add(new ContactModel(4, "Sarah Connor", "(11) 93412-2175", "user_04.png"));
        list.add(new ContactModel(5, "Lara Croft", "(11) 94102-1142", "user_05.png"));
        list.add(new ContactModel(6, "Fofao Carreta", "(11) 94102-1142", "user_06.png"));
        list.add(new ContactModel(7, "Hikaru Kurosaki ", "(11) 98054-6389", "user_07.png"));
        list.add(new ContactModel(8, "Wesley Oliveira", "(11) 91341-2319", "user_08.png"));
        list.add(new ContactModel(9, "Marilyn Monroea", "(11) 93084-1542", "user_09.png"));
        list.add(new ContactModel(10, "Amy Winehouse", "(11) 92431-2113", "user_10.png"));
        list.add(new ContactModel(11, "John Romero", "(11) 92054-4319", "user_11.png"));
        list.add(new ContactModel(12, "Daddy Pig", "(11) 98123-5198", "user_12.png"));
        list.add(new ContactModel(13, "Robert Nesta", "(11) 91245-1351", null));
        list.add(new ContactModel(14, "Brad Paisley", "(11) 92054-6148", "user_14.png"));
        list.add(new ContactModel(15, "Rutherford Bohr", "(11) 98153-6389", null));

        return list;
    }


}

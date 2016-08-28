package br.com.vagners.sendmoney.presenter;

import br.com.vagners.sendmoney.service.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vagnerss on 27/08/16.
 */
public class MainPresenter {

    public MainView view;

    public MainPresenter(MainView _view) {
        this.view = _view;
    }

    public void getToken(String name, String email) {

        ServiceAPI.getInstance().getToken(name, email, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.resultToken(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.errorGenerateToken(t.getMessage());
            }
        });
    }
}

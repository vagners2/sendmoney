package br.com.vagners.sendmoney.presenter;

import java.util.ArrayList;
import java.util.List;

import br.com.vagners.sendmoney.model.ContactModel;
import br.com.vagners.sendmoney.service.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vagnerss on 27/08/16.
 */
public class SendMoneyPresenter {

    private SendMoneyView view;

    public SendMoneyPresenter(SendMoneyView _view) {
        this.view = _view;
    }

    public void getContacts() {
        view.resultContacts(ServiceAPI.getInstance().getContacts());
    }

    public void sendMoney(String token, String id, final Double value) {
        ServiceAPI.getInstance().sendMoney(token, id, value, new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body())
                    view.resultSend();
                else
                    view.onErrorSend("Erro ao tentar fazer o envio!");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                view.onErrorSend(t.getMessage());
            }
        });
    }
}

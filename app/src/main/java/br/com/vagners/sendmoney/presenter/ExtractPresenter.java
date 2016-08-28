package br.com.vagners.sendmoney.presenter;

import java.util.List;

import br.com.vagners.sendmoney.model.ContactModel;
import br.com.vagners.sendmoney.model.TransferModel;
import br.com.vagners.sendmoney.service.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vagnerss on 28/08/16.
 */
public class ExtractPresenter {

    protected ExtractView view;

    public ExtractPresenter(ExtractView _view) {
        this.view = _view;
    }

    public void getExtract(String token) {
        ServiceAPI.getInstance().getTransfers(token, new Callback<List<TransferModel>>() {
            @Override
            public void onResponse(Call<List<TransferModel>> call, Response<List<TransferModel>> response) {
                List<TransferModel> list = response.body();

                if (list == null)
                    return;

                for (TransferModel item : list) {
                    ContactModel c = ServiceAPI.getInstance().getContacts().get(item.getClientId());
                    item.setName(c.getName());
                    item.setPhone(c.getPhone());
                    item.setPhoto(c.getPhoto());
                }
                view.resultTransfers(list);
            }

            @Override
            public void onFailure(Call<List<TransferModel>> call, Throwable t) {
                view.onErrorTransfers(t.getMessage());
            }
        });
    }

}

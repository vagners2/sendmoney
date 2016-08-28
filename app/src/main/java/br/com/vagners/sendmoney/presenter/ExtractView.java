package br.com.vagners.sendmoney.presenter;

import java.util.List;

import br.com.vagners.sendmoney.model.TransferModel;

/**
 * Created by vagnerss on 28/08/16.
 */
public interface ExtractView {
    void resultTransfers(List<TransferModel> list);

    void onErrorTransfers(String message);
}

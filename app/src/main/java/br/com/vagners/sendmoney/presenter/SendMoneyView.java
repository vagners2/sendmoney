package br.com.vagners.sendmoney.presenter;

import java.util.List;

import br.com.vagners.sendmoney.model.ContactModel;

/**
 * Created by vagnerss on 27/08/16.
 */
public interface SendMoneyView {
    void resultContacts(List<ContactModel> list);

    void resultSend();

    void onErrorSend(String message);
}

package br.com.vagners.sendmoney.presenter;

/**
 * Created by vagnerss on 26/08/16.
 */
public interface MainView {
    void resultToken(String token);
    void errorGenerateToken(String msg);
}

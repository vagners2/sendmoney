package br.com.vagners.sendmoney.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.vagners.sendmoney.R;

/**
 * Created by vagnerss on 27/08/16.
 */
public class BaseActivity extends AppCompatActivity {
    protected SharedPreferences preferences;
    public static final String PREFERENCES = "sendmoney";
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public void startActivity(Class activity, Bundle extras) {
        Intent intent = new Intent(this, activity);
        if (extras != null)
            intent.putExtras(extras);
        startActivity(intent);
    }

    public String getPreference(String name) {
        return preferences.getString(name, null);
    }

    public void setPreference(String name, String value) {
        SharedPreferences.Editor e = preferences.edit();
        e.putString(name, value);
        e.commit();
    }

    public void showLoading() {
        loading = new ProgressDialog(this);
        loading.setMessage(getString(R.string.loading_message));
        loading.show();
    }

    public void hideLoading() {
        if (loading != null)
            loading.dismiss();
    }
}

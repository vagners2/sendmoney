package br.com.vagners.sendmoney.view;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import br.com.vagners.sendmoney.R;
import br.com.vagners.sendmoney.model.ContactModel;
import br.com.vagners.sendmoney.util.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vagnerss on 28/08/16.
 */
public class SendDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.user_photo)
    ImageView userPhoto;

    @BindView(R.id.user_initial)
    TextView userInitial;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_phone)
    TextView userPhone;

    @BindView(R.id.send_value)
    EditText sendValue;

    @BindView(R.id.btn_cancel)
    RelativeLayout btnCancel;

    @BindView(R.id.btn_confirm)
    RelativeLayout btnConfirm;

    protected ContactModel model;
    private SendDialogEvent delegate;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_send, container, false);
        ButterKnife.bind(this, view);

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        bind();
        return view;
    }

    private void bind() {
        if (model != null) {
            userName.setText(model.getName());
            userPhone.setText(model.getPhone());

            if (model.getPhoto() == null) {
                userPhoto.setImageDrawable(null);
                userInitial.setVisibility(View.VISIBLE);
                userInitial.setText((model.getName().charAt(0) + "" + model.getName().charAt(model.getName().indexOf(" ") + 1)));
            } else {
                userInitial.setVisibility(View.GONE);
                Picasso.with(this.getContext())
                        .load("file:///android_asset/" + model.getPhoto())
                        .transform(new CircleTransform())
                        .into(userPhoto);

            }

        }

        sendValue.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    sendValue.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted.replaceAll("[$]", "");
                    sendValue.setText(current);
                    sendValue.setSelection(current.length());
                    sendValue.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setModel(ContactModel _model) {
        this.model = _model;
    }

    public void setOnEvent(SendDialogEvent event) {
        delegate = event;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_cancel:
                this.dismiss();
                break;
            case R.id.btn_confirm:
                this.dismiss();
                delegate.onSend(model.getId().toString(), sendValue.getText().toString());
                break;
        }

    }

    public interface SendDialogEvent {
        void onSend(String id, String value);
    }
}
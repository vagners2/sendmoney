package br.com.vagners.sendmoney.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import br.com.vagners.sendmoney.R;

/**
 * Created by vagnerss on 27/08/16.
 */
public class UITools {

    public static void showMessage(final Context context, String title, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modal_dialog);


        if (title != null) {
            TextView titleView = (TextView) dialog.findViewById(R.id.ModalTitle);
            titleView.setText(title);
        } else {
            dialog.findViewById(R.id.ModalTitle).setVisibility(View.GONE);
            dialog.findViewById(R.id.ModalTitleLine).setVisibility(View.GONE);
        }

        TextView messageView = (TextView) dialog.findViewById(R.id.ModalMessage);
        messageView.setText(message);

        TextView dialogButton = (TextView) dialog.findViewById(R.id.ModalOk);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();

    }

}

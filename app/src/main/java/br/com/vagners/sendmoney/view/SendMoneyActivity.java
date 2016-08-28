package br.com.vagners.sendmoney.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

import br.com.vagners.sendmoney.R;
import br.com.vagners.sendmoney.model.ContactModel;
import br.com.vagners.sendmoney.presenter.SendMoneyPresenter;
import br.com.vagners.sendmoney.presenter.SendMoneyView;
import br.com.vagners.sendmoney.util.BaseActivity;
import br.com.vagners.sendmoney.util.CircleTransform;
import br.com.vagners.sendmoney.util.UITools;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SendMoneyActivity extends BaseActivity implements SendMoneyView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list)
    RecyclerView recyclerView;


    private SendMoneyPresenter presenter;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_sendmoney));
        toolbar.setTitleTextColor(Color.WHITE);

        presenter = new SendMoneyPresenter(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        presenter.getContacts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void resultContacts(List<ContactModel> list) {
        adapter = new SendMoneyAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void resultSend() {
        hideLoading();
        UITools.showMessage(this, getString(R.string.sucess), getString(R.string.send_sucess));
    }

    @Override
    public void onErrorSend(String message) {
        hideLoading();
        UITools.showMessage(this, getString(R.string.error_title), message);
    }

    public void openSendDialog(ContactModel contact) {
        SendDialog dialog = new SendDialog();
        dialog.setModel(contact);
        dialog.setOnEvent(new SendDialog.SendDialogEvent() {
            @Override
            public void onSend(String id, String value) {
                showLoading();
                presenter.sendMoney(getPreference("Token"), id, Double.parseDouble(value));
            }
        });
        dialog.show(getSupportFragmentManager(), null);
    }

    public class SendMoneyAdapter extends RecyclerView.Adapter<SendMoneyAdapter.ViewHolder> {

        private Context context;
        private List<ContactModel> list;
        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendDialog(list.get(recyclerView.getChildLayoutPosition(v)));
            }
        };

        public SendMoneyAdapter(Context _context, List<ContactModel> _list) {
            this.context = _context;
            this.list = _list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(onClickListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ContactModel model = this.list.get(position);
            if (model.getPhoto() == null) {
                holder.userPhoto.setImageDrawable(null);
                holder.userInitial.setVisibility(View.VISIBLE);
                holder.userInitial.setText((model.getName().charAt(0) + "" + model.getName().charAt(model.getName().indexOf(" ") + 1)));
            } else {
                holder.userInitial.setVisibility(View.GONE);
                Picasso.with(this.context)
                        .load("file:///android_asset/" + model.getPhoto())
                        .transform(new CircleTransform())
                        .into(holder.userPhoto);

            }
            holder.userName.setText(model.getName());
            holder.userPhone.setText(model.getPhone());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.user_photo)
            ImageView userPhoto;

            @BindView(R.id.user_initial)
            TextView userInitial;

            @BindView(R.id.user_name)
            TextView userName;

            @BindView(R.id.user_phone)
            TextView userPhone;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

    }


}

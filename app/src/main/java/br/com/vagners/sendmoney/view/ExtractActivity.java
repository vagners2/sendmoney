package br.com.vagners.sendmoney.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import br.com.vagners.sendmoney.R;
import br.com.vagners.sendmoney.model.ContactModel;
import br.com.vagners.sendmoney.model.TransferModel;
import br.com.vagners.sendmoney.presenter.ExtractPresenter;
import br.com.vagners.sendmoney.presenter.ExtractView;
import br.com.vagners.sendmoney.presenter.SendMoneyPresenter;
import br.com.vagners.sendmoney.presenter.SendMoneyView;
import br.com.vagners.sendmoney.util.BaseActivity;
import br.com.vagners.sendmoney.util.CircleTransform;
import br.com.vagners.sendmoney.util.UITools;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExtractActivity extends BaseActivity implements ExtractView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    private ExtractPresenter presenter;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_extract));
        toolbar.setTitleTextColor(Color.WHITE);

        presenter = new ExtractPresenter(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        showLoading();
        presenter.getExtract(getPreference("Token"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void resultTransfers(List<TransferModel> list) {
        hideLoading();
        adapter = new ExtractAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onErrorTransfers(String message) {
        hideLoading();
        UITools.showMessage(this, getString(R.string.error_title), message);

    }

    public class ExtractAdapter extends RecyclerView.Adapter<ExtractAdapter.ViewHolder> {

        private Context context;
        private List<TransferModel> list;

        public ExtractAdapter(Context _context, List<TransferModel> _list) {
            this.context = _context;
            this.list = _list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_extract, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TransferModel model = this.list.get(position);
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
            try {

                DateFormat iso8601 = new SimpleDateFormat("dd-MM-yyyy'T'hh:mm:ss", Locale.getDefault());
                iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = iso8601.parse(model.getData());
                holder.transactionDate.setText(date.toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }
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

            @BindView(R.id.transaction_date)
            TextView transactionDate;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

    }

}

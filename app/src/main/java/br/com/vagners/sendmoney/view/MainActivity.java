package br.com.vagners.sendmoney.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import br.com.vagners.sendmoney.R;
import br.com.vagners.sendmoney.presenter.MainPresenter;
import br.com.vagners.sendmoney.presenter.MainView;
import br.com.vagners.sendmoney.util.BaseActivity;
import br.com.vagners.sendmoney.util.CircleTransform;
import br.com.vagners.sendmoney.util.UITools;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {


    @BindView(R.id.user_photo)
    ImageView userPhoto;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_email)
    TextView userEmail;

    @BindView(R.id.load_message)
    TextView loadMessage;

    @BindView(R.id.btn_send)
    RelativeLayout btnSend;

    @BindView(R.id.btn_extract)
    RelativeLayout btnExtract;


    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        loadDataUser();

        presenter.getToken(getString(R.string.user_name), getString(R.string.user_email));
    }

    private void loadDataUser() {

        userName.setText(this.getString(R.string.user_name));
        userEmail.setText(this.getString(R.string.user_email));

        Picasso.with(this).load(R.raw.perfil)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .transform(new CircleTransform())
                .into(userPhoto);

        // TODO: 28/08/16 comentar animação quando for usar o espresso
        createAnimationPhoto();

    }

    private void createAnimationPhoto() {
        ScaleAnimation scale = new ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setRepeatCount(-1);
        scale.setRepeatMode(Animation.REVERSE);
        scale.setDuration(1000);
        userPhoto.startAnimation(scale);
    }

    private void createAnimationButton(View view, Long offset) {
        view.setVisibility(View.VISIBLE);
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2000);
        anim.setStartOffset(offset);
        view.startAnimation(anim);
    }

    @Override
    public void resultToken(String token) {
        setPreference("Token", token);
        loadMessage.setVisibility(View.GONE);

        //// TODO: 28/08/16 Comentar as animações dos buttons para usar o espresso
        createAnimationButton(btnSend, (long) 2000);
        createAnimationButton(btnExtract, (long) 4000);

        //descomentar usando espresso
        //btnSend.setVisibility(View.VISIBLE);
        //btnExtract.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorGenerateToken(String msg) {
        UITools.showMessage(this, getString(R.string.error_title), msg);
    }

    @OnClick(R.id.btn_send)
    public void btnSendOnClick() {
        startActivity(SendMoneyActivity.class, null);
    }

    @OnClick(R.id.btn_extract)
    public void btnExtractOnClick() {
        startActivity(ExtractActivity.class, null);
    }

}

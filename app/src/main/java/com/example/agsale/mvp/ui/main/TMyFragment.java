package com.example.agsale.mvp.ui.main;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseFragment;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.api.service.LoginApiService;
import com.example.agsale.mvp.model.bean.InformationTranslation;
import com.example.agsale.mvp.model.bean.PhotoTranslation;
import com.example.agsale.mvp.model.bean.RegisterTranslation;
import com.example.agsale.mvp.ui.log.PasswordLogActivity;
import com.example.agsale.mvp.ui.my.AddressActivity;
import com.example.agsale.mvp.ui.my.PravacyActivity;
import com.example.agsale.mvp.ui.my.SettingActivity;
import com.example.agsale.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TMyFragment extends BaseFragment {
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.account)
    TextView account;
    @OnClick(R.id.back)
    void back(){
        requireActivity().finish();
    }
    @OnClick(R.id.exit)
    void exit(){
        exitRequest();
    }
    @OnClick(R.id.privacy)
    void goToPrivacy(){
        Intent intent=new Intent();
        intent.setClass(requireActivity(), PravacyActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.address)
    void goToAddress(){
        Intent intent=new Intent();
        intent.setClass(requireActivity(), AddressActivity.class);
        startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformation();
        getPhoto();
    }
    @Override
    protected void initData() {
        getInformation();
        getPhoto();
    }
    private void exitRequest(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        LoginApiService loginApiService=retrofit.create(LoginApiService.class);
        Call<RegisterTranslation> call=loginApiService.exit();
        call.enqueue(new Callback<RegisterTranslation>() {
            @Override
            public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                RegisterTranslation registerTranslation=response.body();
                String msg=registerTranslation.getMsg();
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                        if(msg.equals("????????????")){
                            Intent intent=new Intent();
                            intent.setClass(requireActivity(), PasswordLogActivity.class);
                            startActivity(intent);
                            SharedPreferencesUtil.getInstance().delete();
                            requireActivity().finish();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RegisterTranslation> call, Throwable t) {

            }
        });
    }
    private void getInformation(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<InformationTranslation> call=informationApiService.getInformation();
        call.enqueue(new Callback<InformationTranslation>() {
            @Override
            public void onResponse(Call<InformationTranslation> call, Response<InformationTranslation> response) {
                InformationTranslation informationTranslation = response.body();
                assert informationTranslation != null;
                InformationTranslation.MyData data=informationTranslation.getData();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(data.getUsername());
                        account.setText(data.getUid());
                    }
                });
            }

            @Override
            public void onFailure(Call<InformationTranslation> call, Throwable t) {

            }
        });
    }
    private void getPhoto(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<PhotoTranslation> call=informationApiService.getPhoto();
        call.enqueue(new Callback<PhotoTranslation>() {
            @Override
            public void onResponse(Call<PhotoTranslation> call, Response<PhotoTranslation> response) {
                PhotoTranslation photoTranslation=response.body();
                if (photoTranslation==null){
                    return;
                }
                PhotoTranslation.MyData photo=photoTranslation.getData();
                System.out.println(photo);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        head.setImageBitmap(returnBitMap(photo.getPersonalphoto()));
                    }
                });
            }

            @Override
            public void onFailure(Call<PhotoTranslation> call, Throwable t) {
                Log.e(TAG, "info???" + t.getMessage() + "," + t.toString());
                Toast.makeText(requireActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

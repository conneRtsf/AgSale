package com.example.agsale.mvp.ui.my;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.bean.DivisionModel;
import com.example.agsale.mvp.model.bean.Divisions;
import com.example.agsale.mvp.model.bean.RegisterTranslation;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import top.defaults.view.Division;
import top.defaults.view.DivisionPickerView;

public class AddAreaActivity extends BaseActivity {
    @BindView(R.id.area)
    EditText area;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.submit)
    Button submit;
    @OnClick(R.id.back)
    void back(){
        finish();
    }
    final List<DivisionModel> divisions = Divisions.get(this);
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.divisionPicker)
    DivisionPickerView divisionPicker;
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {
        divisionPicker.setDivisions(divisions);
        divisionPicker.setOnSelectedDateChangedListener(new DivisionPickerView.OnSelectedDivisionChangedListener() {
            @Override
            public void onSelectedDivisionChanged(Division division) {
                System.out.println(division.getParent().getParent().getName() + " " + division.getParent().getName() + " " + division.getName());
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitArea(division);
                    }
                });
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_add_area;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
    private void submitArea(Division division){
        String detail=String.valueOf(area.getText());
        String number=String.valueOf(phone.getText());
        String name2=String.valueOf(name.getText());
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapterConsumption();
        InformationApiService informationApiService =retrofit.create(InformationApiService.class);
        System.out.println(division.getParent().getParent().getName());
        Call<RegisterTranslation> call=informationApiService.area(division.getParent().getName(),division.getName(),detail,name2,number,division.getParent().getParent().getName());
        call.enqueue(new Callback<RegisterTranslation>() {
            @Override
            public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                Log.e( "onResponse: ", String.valueOf(response));
                RegisterTranslation registerTranslation=response.body();
                AddAreaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("run: ",registerTranslation.getMsg() );
                        Toast.makeText(AddAreaActivity.this, registerTranslation.getMsg(), Toast.LENGTH_SHORT).show();
                        if(registerTranslation.getMsg().equals("更新成功")){
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RegisterTranslation> call, Throwable t) {

            }
        });
    }
}
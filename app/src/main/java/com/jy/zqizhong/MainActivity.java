package com.jy.zqizhong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRv;
    /**
     * 操作
     */
    private Button mBtCao;
    /**
     * 删除
     */
    private Button mBtDelect;
    /**
     * 完成
     */
    private Button mBtStop;
    private ArrayList<WanAndroidBean.DataBean.DatasBean> list;
    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        mBtCao = (Button) findViewById(R.id.bt_cao);
        mBtCao.setOnClickListener(this);
        mBtDelect = (Button) findViewById(R.id.bt_delect);
        mBtDelect.setOnClickListener(this);
        mBtStop = (Button) findViewById(R.id.bt_stop);
        mBtStop.setOnClickListener(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new HomeAdapter(list, this);
        mRv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApIServer.baseurl)
                .build();
        ApIServer apIServer = retrofit.create(ApIServer.class);
        Observable<WanAndroidBean> data = apIServer.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanAndroidBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidBean wanAndroidBean) {
                        List<WanAndroidBean.DataBean.DatasBean> datas = wanAndroidBean.getData().getDatas();
                        list.addAll(datas);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_cao:
                adapter.isChed = true;
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt_delect:
                if (adapter.isChed()) {
                    for (int i = 0; i < list.size(); i++) {
                        WanAndroidBean.DataBean.DatasBean datasBean = list.get(i);
                        if (datasBean.isVist()) {
                            list.remove(i);
                            i--;
                        }
                    adapter.notifyDataSetChanged();
                    }
                }

                break;
            case R.id.bt_stop:
                adapter.isChed = false;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}

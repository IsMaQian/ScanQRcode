package com.example.mqtest.scanqrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.example.mqtest.scanqrcode.Dao.UserGreenDao;
import com.example.mqtest.scanqrcode.Dao.UserRealmDao;
import com.example.mqtest.scanqrcode.Data.DroneDate;
import com.example.mqtest.scanqrcode.Data.DroneMsgBean;
import com.example.mqtest.scanqrcode.Data.ToastShow;
import com.example.mqtest.scanqrcode.Http.HttpUtil;
import com.example.mqtest.scanqrcode.View.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.textView)
    TextView textContent;
    @BindView(R.id.scan)
    Button scan;

    String mdroneID;
    String mactivationState;
    String mdroneColor;
    String mdroneWeight;

    String selecturl1 = "http://192.168.1.211:8800/orcal/aydrone_select.php";
    String selecturl = "http://192.168.1.211:8800/orcal/aydrone_select2.php";
    String updateurl = "http://192.168.1.211:8800/orcal/aydrone_update.php";

    public static final int REQ_CODE_PERMISSION = 1;
    Realm realm;
    String ButtonText = "确定";

    CustomDialog.Builder builder;

    UserGreenDao userGreenDao;
    UserRealmDao userRealmDao;
    CustomDialog dialog;
    @BindView(R.id.scan_sd)
    Button scanSd;

    boolean IsScan2Http = false;
    @BindView(R.id.add_date)
    Button addDate;
    @BindView(R.id.editID)
    EditText editID;
    @BindView(R.id.deleteByID)
    Button deleteByID;
    @BindView(R.id.deleteAll)
    Button deleteAll;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化Realm
        realm = Realm.getDefaultInstance();
        userRealmDao = new UserRealmDao(realm);

        userGreenDao = new UserGreenDao();

        builder = new CustomDialog.Builder(MainActivity.this);
        scan.setOnClickListener(this);

        scanSd.setOnClickListener(this);

        addDate.setOnClickListener(this);
        deleteByID.setOnClickListener(this);
        deleteAll.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);

                } else {
                    IsScan2Http = true;
                    startCaptureActivityForResult();
                }
                //mSendOkHttpGet();
                break;
            case R.id.scan_sd:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);

                } else {
                    IsScan2Http = false;
                    startCaptureActivityForResult();
                }
                break;
            case R.id.add_date:
            case R.id.deleteByID:
                boolean IssuccessInsertOrDelete = false;
                String aydroneId = editID.getText().toString();
                if (aydroneId.equals("")) {
                    ToastShow.show("飞控编号不能为空");
                } else {
                    if (v.getId() == R.id.add_date) {
                        // userRealmDao.addDate(aydroneId);//Realm
                        IssuccessInsertOrDelete = userGreenDao.addDateToSQL(aydroneId);//GreenDao
                    } else {
                        IssuccessInsertOrDelete = userGreenDao.DeleteSQLByID(aydroneId);
                    }
                    if (IssuccessInsertOrDelete) {
                        editID.setText("");
                    }
                }
                break;
            case R.id.deleteAll:
//                userRealmDao.deleteAllDate();
                userGreenDao.DeleteSQLAll();
                editID.setText("");
                break;
        }
    }

    //二维码扫描界面跳转
    private void startCaptureActivityForResult() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    //二维码扫描结果反馈
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String scanData = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        textContent.setText(scanData);
                        if (IsScan2Http) {
                            mSendOkHttpPost(selecturl, scanData);
                        } else {
//                            DroneDate droneDate = userRealmDao.queryDraoneById(scanData);
                            /*if (droneDate != null) {
                                getDatebyRealm(droneDate);
                            } else {
                                ToastShow.show("没有此飞控编号");
                            }*/
                            DroneMsgBean msgBean = userGreenDao.querySQL(scanData);

                            if (msgBean != null) {
                                getDatebyRealm(msgBean);

                            } else {
                                ToastShow.show("没有此飞控编号");
                            }

                        }
                        //mSendOkHttpGet();
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            textContent.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        }
                        break;
                }
                break;
        }

    }

    //显示查询到的数据
    private void getDatebyRealm(DroneDate droneDate) {
        mdroneID = droneDate.getDrone_id();
        mactivationState = droneDate.getDrone_activation();
        mdroneColor = droneDate.getDrone_color();
        mdroneWeight = droneDate.getDrone_weight();
        IsChangeButtonText();
        showDialog();
    }

    private void getDatebyRealm(DroneMsgBean droneDate) {
        mdroneID = droneDate.getDrone_id();
        mactivationState = droneDate.getDrone_activation();
        mdroneColor = droneDate.getDrone_color();
        mdroneWeight = droneDate.getDrone_weight();
        IsChangeButtonText();
        showDialog();
    }


    private void IsChangeButtonText() {

        if (mactivationState.equals("是")) {
            ButtonText = "修改";
        } else if (mactivationState.equals("否")) {
            ButtonText = "激活";
        }
    }

    //对话框显示
    private void showDialog() {
        dialog = builder
                .style(R.style.MyDialog)
                .heightdp(400)
                .widthdp(300)
                .cancelTouchout(false)
                .view(R.layout.drone_edit_info)
                .addTextView(R.id.droneId, mdroneID)
                .addTextView(R.id.activation, mactivationState)
                .addEditText(R.id.color, mdroneColor)
                .addEditText(R.id.weight, mdroneWeight)
                .addButtonText(R.id.confirm, ButtonText)
                .addViewOnClick(R.id.cancel, listener)
                .addViewOnClick(R.id.confirm, listener)
                .build();
        dialog.show();
    }

    //对话框中按钮点击
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel:
                    dialog.dismiss();
                    break;
                case R.id.confirm:
                    mdroneColor = builder.getEditText(R.id.color);
                    mdroneWeight = builder.getEditText(R.id.weight);
                    if (IsScan2Http) {
                        mSendOkHttpPost(updateurl, mdroneID, mdroneColor, mdroneWeight);
                    } else {
//                        userRealmDao.updateDroneDate(mdroneID, mdroneColor, mdroneWeight);
                        userGreenDao.updateSQL(mdroneID, mdroneColor, mdroneWeight);
                        dialog.dismiss();
                        //Toast.makeText(MainActivity.this, ButtonText + "成功", Toast.LENGTH_SHORT).show();
                        ToastShow.show(ButtonText + "成功");

                    }
                    break;
            }
        }
    };

    //扫描二维码时发送网址
    private void mSendOkHttpPost(String url, String drone_id) {
        HttpUtil.sendOkHttpPost(url, drone_id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseDate = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseDate.equals("[]") || responseDate.equals("")) {
                            Toast.makeText(MainActivity.this, "没有此数据", Toast.LENGTH_LONG).show();
                        } else {
                            parseJSONWithJSONObject(responseDate);
                            showDialog();
                        }
                    }
                });

            }
        });
    }

    //激活并修给数据
    private void mSendOkHttpPost(String url, String drone_id, String drone_color, String drone_weight) {
        HttpUtil.sendOkHttpPost(url, drone_id, drone_color, drone_weight, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseDate = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseDate.equals("1")) {
                            dialog.dismiss();
                            // Toast.makeText(MainActivity.this, ButtonText + "成功", Toast.LENGTH_SHORT).show();
                            ToastShow.show(ButtonText + "成功");

                        } else if (responseDate.equals("0")) {
                            //  Toast.makeText(MainActivity.this, ButtonText + "失败", Toast.LENGTH_SHORT).show();
                            ToastShow.show(ButtonText + "失败");

                        }
                    }
                });
            }
        });
    }

    //解析JSON
    private void parseJSONWithJSONObject(String jsonDate) {
        try {
            JSONArray jsonArray = new JSONArray(jsonDate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mdroneID = jsonObject.getString("DRONE_ID");
                mactivationState = jsonObject.getString("ACTIVATION");
                mdroneColor = jsonObject.getString("COLOR");
                mdroneWeight = jsonObject.getString("WEIGHT");
            }
            IsChangeButtonText();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void mSendOkHttpGet() {
        HttpUtil.sendOkHttpRequest(selecturl1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseDate = response.body().string();
                parseJSONWithJSONObject(responseDate);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textContent.setText(responseDate);
                        //MyDialog myDialog = new MyDialog(MainActivity.this);
                        //myDialog.show();
                        /*CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
                        CustomDialog dialog = builder
                                .style(R.style.MyDialog)
                                .heightdp(400)
                                .widthdp(300)
                                .cancelTouchout(false)
                                .view(R.layout.droneinfo_layout)
                                .build();
                        dialog.show();*/
                    }
                });
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCaptureActivityForResult();
                } else {
                    Toast.makeText(this, "You must agree the camera permission request before you use the code scan function", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        deleteAllDate();
        realm.close();
    }
}

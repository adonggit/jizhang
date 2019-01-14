package com.per.notezzw;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.per.notezzw.db.SqliteManage;
import com.per.notezzw.ui.load.LoadingActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.List;

public class LauncherActivity extends Activity {
    private static final int REQUEST_CODE_PERMISSION_SINGLE = 100;
    private static final int REQUEST_CODE_PERMISSION_MULTI = 101;

    private static final int REQUEST_CODE_SETTING = 300;
    public String CONFDIR;
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_MULTI)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .callback(this)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(LauncherActivity.this, rationale).show();
                    }
                })
                .start();

    }



    @PermissionYes(REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiYes(@NonNull List<String> grantedPermissions) {
        //创建配置目录的文件夹
        CONFDIR = this.getFilesDir() + "/config";
        makeConfigDir();
        initTime();
        mContext = this;


        Intent intent=new Intent(this,LoadingActivity.class);
        startActivity(intent);
        finish();
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_MULTI)
    private void getMultiNo(@NonNull List<String> deniedPermissions) {


        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                    .setTitle("权限申请")
                    .setMessage("您是否同意本App获取此权限")
                    .setPositiveButton("是")
                    .setNegativeButton("否",null)
                    .show();

            // 更多自定dialog，请看上面。
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(this, "请赋予权限", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    private void initTime() {
        if (!SqliteManage.getInstance(this).isExitInTable("time", "time=?", new String[]{"firsttime"})) {
            ContentValues values = new ContentValues();
            values.put("time", "firsttime");
            values.put("value", System.currentTimeMillis());
            SqliteManage.getInstance(this).insertItem("time", values);
        }

        if (!SqliteManage.getInstance(this).isExitInTable("time", "time=?", new String[]{"bdtime"})) {
            ContentValues values = new ContentValues();
            values.put("time", "bdtime");
            values.put("value", 0);
            SqliteManage.getInstance(this).insertItem("time", values);
        }
    }

    private void makeConfigDir() {
        File file = new File(CONFDIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}

package com.yitong.requestframe.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.yitong.requestframe.R;
import com.yitong.requestframe.utils.SPUtil;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SettingActivity extends SwipeBackActivity implements View.OnClickListener {

    private EditText protocol, ip, port, project;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_setting);
        protocol = (EditText) findViewById(R.id.protocol);
        ip = (EditText) findViewById(R.id.ip);
        port = (EditText) findViewById(R.id.port);
        project = (EditText) findViewById(R.id.project);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        initDta();
    }

    private void initDta() {
        protocol.setText((String) SPUtil.get(this, "BaseProtocol", ""));
        ip.setText((String) SPUtil.get(this, "BaseIp", ""));
        port.setText((String) SPUtil.get(this, "BasePort", ""));
        project.setText((String) SPUtil.get(this, "BaseProject", ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                StringBuffer stringBuffer = new StringBuffer();
                if (!TextUtils.isEmpty(protocol.getText().toString())
                        && !TextUtils.isEmpty(ip.getText().toString())
                        && !TextUtils.isEmpty(project.getText().toString())) {
                    stringBuffer.append(protocol.getText().toString() + "://");
                    stringBuffer.append(ip.getText().toString());
                    if (!TextUtils.isEmpty(port.getText().toString())) {
                        stringBuffer.append(":" + port.getText().toString());
                    }
                    stringBuffer.append("/" + project.getText().toString() + "/");
                }
                Log.e("TAG", stringBuffer.toString());
                SPUtil.put(this, "BaseUrl", stringBuffer.toString());
                SPUtil.put(this, "BaseProtocol", protocol.getText().toString());
                SPUtil.put(this, "BaseIp", ip.getText().toString());
                SPUtil.put(this, "BasePort", port.getText().toString());
                SPUtil.put(this, "BaseProject", project.getText().toString());
                Toast.makeText(this, "更新成功，杀掉进程后生效!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

}

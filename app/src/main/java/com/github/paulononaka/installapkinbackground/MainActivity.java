package com.github.paulononaka.installapkinbackground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.paulononaka.installapkinbackground.apihelper.ApplicationManager;
import com.github.paulononaka.installapkinbackground.apihelper.OnInstalledPackaged;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    EditText txtApkFilePath;
    Button btnInstall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtApkFilePath = (EditText) findViewById(R.id.txtApkFilePath);

        btnInstall = (Button) findViewById(R.id.btnInstall);

        try {
            final ApplicationManager am = new ApplicationManager(MainActivity.this);
            am.setOnInstalledPackaged(new OnInstalledPackaged() {

                public void packageInstalled(String packageName, int returnCode) {
                    if (returnCode == ApplicationManager.INSTALL_SUCCEEDED) {
                        Log.d(TAG, "Install succeeded");
                    } else {
                        Log.d(TAG, "Install failed: " + returnCode);
                    }
                }
            });


            btnInstall.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    try {
                        am.installPackage(txtApkFilePath.getText().toString());
                    } catch (Exception e) {
                        logError(e);
                    }
                }
            });

        } catch (Exception e) {
            logError(e);
        }
    }

    private void logError(Exception e) {
        e.printStackTrace();
        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}

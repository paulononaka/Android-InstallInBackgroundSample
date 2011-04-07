package com.paulononaka;

import com.paulononaka.apihelper.ApplicationManager;
import com.paulononaka.apihelper.OnInstalledPackaged;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InstallInBackgroundSample extends Activity {

	public static final String TAG = "InstallInBackgroundSample";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try {
			final ApplicationManager am = new ApplicationManager(InstallInBackgroundSample.this);
			am.setOnInstalledPackaged(new OnInstalledPackaged() {

				public void packageInstalled(String packageName, int returnCode) {
					if (returnCode == ApplicationManager.INSTALL_SUCCEEDED) {
						Log.d(TAG, "Install succeeded");
					} else {
						Log.d(TAG, "Install failed: " + returnCode);
					}
				}
			});

			final TextView txtApkFilePath = (TextView) findViewById(R.id.txtApkFilePath);

			Button btnInstall = (Button) findViewById(R.id.btnInstall);
			btnInstall.setOnClickListener(new OnClickListener() {

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
		Toast.makeText(InstallInBackgroundSample.this, R.string.error, Toast.LENGTH_LONG).show();
	}
}
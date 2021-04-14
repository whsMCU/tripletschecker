package com.checker.tripletschecker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AdView mAdView;

    AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 100;

    Button twins_button, triplets_button, quadruplets_button;

    long m_backKeyPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER); //광고 사이즈는 배너 사이즈로 설정
        adView.setAdUnitId("ca-app-pub-9708062265777742/4628892374");

        twins_button = (Button) findViewById(R.id.bt_Twins);
        triplets_button = (Button) findViewById(R.id.bt_Triple);
        quadruplets_button = (Button) findViewById(R.id.bt_Quad);

        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        appUpdateManager.registerListener(listener);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            MainActivity.this,
                            MY_REQUEST_CODE
                    );
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            }
        });

        View.OnClickListener Listener = new Button.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_Twins:
                        intent = new Intent(getApplicationContext(), TwinsActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.bt_Triple:
                        intent = new Intent(getApplicationContext(), TripletsActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.bt_Quad:
                        intent = new Intent(getApplicationContext(), QuadrupletsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        twins_button.setOnClickListener(Listener);
        triplets_button.setOnClickListener(Listener);
        quadruplets_button.setOnClickListener(Listener);
    }

    private InstallStateUpdatedListener listener = state -> {
        // (Optional) Provide a download progress bar.
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            long bytesDownloaded = state.bytesDownloaded();
            long totalBytesToDownload = state.totalBytesToDownload();
            // Implement progress bar.
        } else if (state.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate();
        } else if (state.installStatus() == InstallStatus.INSTALLED) {
            if (appUpdateManager != null) {
                //appUpdateManager.unregisterListener(listener);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    /* Displays the snackbar notification and call to action. */
    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.imageView),
                        R.string.installed,
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.install, view -> {
            if (appUpdateManager != null) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(
                getResources().getColor(R.color.teal_200));
        snackbar.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(listener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.description:
                Intent intent = new Intent(this, DescriptionActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //기존의 뒤로가기 버튼의 기능 제거
        //super.onBackPressed();

        if (System.currentTimeMillis() > m_backKeyPressedTime + 2000) {
            m_backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.back_button, Toast.LENGTH_SHORT).show();
            return;
        }

        //2초 이내에 뒤로가기 버튼을 한번 더 클릭시 finish()
        if (System.currentTimeMillis() <= m_backKeyPressedTime + 2000) {
            finish();
        }
    }
}
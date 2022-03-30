package com.example.accessibilityservice1;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = MyAccessibilityService.class.getSimpleName();

    public MyAccessibilityService() {

        super();

        Log.d(TAG, "MyAccessibilityService");

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent " +
                AccessibilityEvent.eventTypeToString(event.getEventType()));
    }

    @Override
    protected void onServiceConnected() {

        Log.d(TAG, "onServiceConnected");
        super.onServiceConnected();

        checkSecurityProviderAndUpdateIfNeeded(getBaseContext());

    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
    /**
     * Use the Google Play Services to update the security provider.
     *
     * Android relies on the security provider to provide secure network communications.
     * The default device cryptographic libraries are typically older versions of OpenSSL
     * that contain known flaws. To overcome this, Google provides a mechanism for an
     * application to patch their local copy.
     *
     * Once the provider is updated all calls to security APIs (including SSL APIs) are
     * routed through it. Most apps should use APIs like HttpsURLConnection which uses
     * the device default security provider.
     *
     * https://developer.android.com/training/articles/security-gms-provider.html
     */
    private void checkSecurityProviderAndUpdateIfNeeded(final Context context) {

        if(true) {

            try {

                // **** Comment out this line and the service is not killed when
                // **** when the playstore libraries are updated
                ProviderInstaller.installIfNeeded(context);

            } catch (GooglePlayServicesRepairableException
                    | GooglePlayServicesNotAvailableException ex) {

                // GooglePlayServicesRepairableException
                // Indicates that Google Play services is out of date, disabled, etc.
                // Log the error and continue, will try again with next call

                // GooglePlayServicesNotAvailableException
                // Indicates a non-recoverable error
                // the ProviderInstaller is not able to install an up-to-date Provider.
                // Log the error and continue, will try again with next call

                Log.e(TAG, ex.toString());

            }

        }

    }

}

package com.rperryng.picsync.common;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Ryan PerryNguyen on 2014-12-06.
 */
public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    public static void logKeyHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.rperryng.picsync",
                    PackageManager.GET_SIGNATURES
            );
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Couldn't get hash key");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Couldn't get hash key");
            e.printStackTrace();
        }
    }
}

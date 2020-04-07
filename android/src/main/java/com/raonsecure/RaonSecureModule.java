
package com.raonsecure;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.facebook.react.bridge.*;
import com.raonsecure.ksw.RSKSWCertManager;
import com.raonsecure.ksw.RSKSWCertificate;
import com.raonsecure.ksw.RSKSWICRProtocol;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class RaonSecureModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private RSKSWICRProtocol icrp;

    public RaonSecureModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        RSKSWICRProtocol.securityLevel = RSKSWICRProtocol.RSKSWConstCRSecLevel_SHA1;
    }

    @Override
    public String getName() {
        return "RaonSecure";
    }


    @ReactMethod
    public void getItems(Promise promise) {

        try {
            WritableArray rows = Arguments.createArray();

            List<RSKSWCertificate> certifies = new ArrayList<RSKSWCertificate>();

            RSKSWCertManager manager = RSKSWCertManager.getInstance(reactContext);
            manager.setCertLoadingMode(RSKSWCertManager.CERT_IN_SDCARD | RSKSWCertManager.CERT_FILTER_NPKI | RSKSWCertManager.CERT_FILTER_GPKI);
            for (Object o : manager.getArrCert()) {
                RSKSWCertificate certify = ((RSKSWCertificate) o);
                WritableMap map = Arguments.createMap();
                map.putString("serial", certify.getSerialNumber());
                map.putString("subjectDn", certify.getSubjectDn());
                map.putString("subjectCn", certify.getSubjectCn());
                map.putString("policy", certify.getPolicy());
                map.putString("notAfterDate", certify.getNotAfterDate());
                map.putBoolean("isExpired", certify.isExpired() != RSKSWCertificate.RSKSWConstCertExpModeNORMAL);
                rows.pushMap(map);
            }

            promise.resolve(rows);
        } catch (Exception ex) {
            ex.printStackTrace();
            promise.reject(ex);
        }
    }

    @ReactMethod
    public void removeItem(String subjectDn, Promise promise) {

        try {

            RSKSWCertManager manager = RSKSWCertManager.getInstance(reactContext);

            int n = 0;
            boolean isRemove = false;
            manager.setCertLoadingMode(RSKSWCertManager.CERT_IN_SDCARD | RSKSWCertManager.CERT_FILTER_NPKI | RSKSWCertManager.CERT_FILTER_GPKI);
            for (Object o : manager.getArrCert()) {
                RSKSWCertificate certify = ((RSKSWCertificate) o);
                if (certify.getSubjectDn().equalsIgnoreCase(subjectDn)) {
                    manager.delCert(certify);
                    isRemove = true;
                    break;
                }
                n++;
            }

            WritableMap map = Arguments.createMap();
            map.putInt("removeTo", (!isRemove ? -1 : n));
            promise.resolve(map);

        } catch (Exception ex) {
            promise.reject(ex);
        }
    }

    @ReactMethod
    public void clear(Promise promise) {

        try {

            RSKSWCertManager manager = RSKSWCertManager.getInstance(reactContext);
            manager.setCertLoadingMode(RSKSWCertManager.CERT_IN_APP | RSKSWCertManager.CERT_FILTER_NPKI | RSKSWCertManager.CERT_FILTER_GPKI);
            for (Object o : manager.getArrCert()) {
                RSKSWCertificate certify = ((RSKSWCertificate) o);
                manager.delCert(certify);
            }

            WritableMap map = Arguments.createMap();
            map.putBoolean("success", true);
            promise.resolve(map);
        } catch (Exception ex) {
            promise.reject(ex);
        }
    }


    @ReactMethod
    public void getPath(String subjectDn, Promise promise) {
        try {
            String keyPath = "";
            String certPath = "";

            RSKSWCertManager manager = RSKSWCertManager.getInstance(reactContext);
            manager.setCertLoadingMode(RSKSWCertManager.CERT_IN_SDCARD | RSKSWCertManager.CERT_FILTER_NPKI | RSKSWCertManager.CERT_FILTER_GPKI);
            for (Object o : manager.getArrCert()) {
                RSKSWCertificate certify = ((RSKSWCertificate) o);
                if (certify.getSubjectDn().equalsIgnoreCase(subjectDn)) {
                    keyPath = certify.getKeyPath();
                    certPath = certify.getCertPath();
                    break;
                }
            }

            WritableMap map = Arguments.createMap();
            map.putString("keyPath", keyPath);
            map.putString("certPath", certPath);
            promise.resolve(map);
        } catch (Exception ex) {
            promise.reject(ex);
        }
    }

    @ReactMethod
    public void checkPassword(String subjectDn, String password, Promise promise) {
        try {
            RSKSWCertManager manager = RSKSWCertManager.getInstance(reactContext);

            boolean success = false;
            manager.setCertLoadingMode(RSKSWCertManager.CERT_IN_SDCARD | RSKSWCertManager.CERT_FILTER_NPKI | RSKSWCertManager.CERT_FILTER_GPKI);
            for (Object o : manager.getArrCert()) {
                RSKSWCertificate certify = ((RSKSWCertificate) o);
                if (certify.getSubjectDn().equalsIgnoreCase(subjectDn)) {
                    success = manager.checkPassword(certify, password.getBytes());
                    break;
                }
            }

            WritableMap map = Arguments.createMap();
            map.putBoolean("success", success);
            promise.resolve(map);
        } catch (Exception ex) {
            promise.reject(ex);
        }
    }


    @ReactMethod
    public void getReceiveCode(Promise promise) {

        try {

            if (icrp == null) {
                int ret1 = RSKSWICRProtocol.getLibraryState(reactContext);
                if (ret1 != 8) throw new Exception("License Not Allowed");

                String e = reactContext.getPackageName();
                ApplicationInfo ai = reactContext
                        .getPackageManager()
                        .getApplicationInfo(e, PackageManager.GET_META_DATA);

                Bundle bundle = ai.metaData;

                final String SERVER_IP = bundle.getString("RAON_SERVER_IP");
                final int SERVER_PORT = bundle.getInt("RAON_SERVER_PORT");

                icrp = new RSKSWICRProtocol(reactContext, SERVER_IP, SERVER_PORT);
            }

            Hashtable<String, Object> res = icrp.import1();
            if (res.containsKey("RANDOMNUMBER") && res.get("RANDOMNUMBER") != null) {
                WritableMap result = Arguments.createMap();
                result.putString("code", res.get("RANDOMNUMBER").toString());
                promise.resolve(result);
            } else {
                throw new Exception(res.get("MESSAGE").toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            promise.reject(ex);
        }
    }

    @ReactMethod
    public void importCertify(Promise promise) {

        try {

            Hashtable<String, Object> res = icrp.import2();

            RSKSWCertificate cert;
            RSKSWCertManager manager = RSKSWCertManager.getInstance(reactContext);
            manager.setCertSavingMode(RSKSWCertManager.CERT_IN_SDCARD);

            if (res.get("CODE").toString().equalsIgnoreCase("SC201")) {
                byte[] importedCert = icrp.getCert();
                byte[] importedKey = icrp.getKey();

                boolean save = manager.saveCert(importedCert, importedKey);
                if (!save) throw new Exception("Type B");

                cert = new RSKSWCertificate(importedCert, null);
                WritableMap result = Arguments.createMap();
                result.putString("serial", cert.getSerialNumber());
                result.putString("subjectDn", cert.getSubjectDn());
                result.putString("subjectCn", cert.getSubjectCn());
                result.putString("policy", cert.getPolicy());
                result.putString("notAfterDate", cert.getNotAfterDate());
                result.putBoolean("isExpired", cert.isExpired() != RSKSWCertificate.RSKSWConstCertExpModeNORMAL);
                promise.resolve(result);
            } else if (res.get("CODE").toString().equalsIgnoreCase("SC203")) {
                byte[] importedCert = icrp.getCert();
                byte[] importedKey = icrp.getKey();

                boolean save = manager.saveCert(importedCert, importedKey, icrp.getKmCert(), icrp.getKmKey());
                if (!save) throw new Exception("Type C");

                cert = new RSKSWCertificate(importedCert, null);
                WritableMap result = Arguments.createMap();
                result.putString("serial", cert.getSerialNumber());
                result.putString("subjectDn", cert.getSubjectDn());
                result.putString("subjectCn", cert.getSubjectCn());
                result.putString("policy", cert.getPolicy());
                result.putString("notAfterDate", cert.getNotAfterDate());
                result.putBoolean("isExpired", cert.isExpired() != RSKSWCertificate.RSKSWConstCertExpModeNORMAL);
                promise.resolve(result);
            } else {
                throw new Exception(res.get("MESSAGE").toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            promise.reject(ex);
        }
    }

}

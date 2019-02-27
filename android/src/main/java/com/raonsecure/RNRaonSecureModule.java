
package com.raonsecure;

import com.facebook.react.bridge.*;
import com.raonsecure.ksw.RSKSWCertManager;
import com.raonsecure.ksw.RSKSWCertificate;
import com.raonsecure.ksw.RSKSWICRProtocol;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class RNRaonSecureModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private RSKSWICRProtocol icrp;

    public RNRaonSecureModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        RSKSWICRProtocol.securityLevel = RSKSWICRProtocol.RSKSWConstCRSecLevel_SHA1;
    }

    @Override
    public String getName() {
        return "RNRaonSecure";
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
                map.putString("subjectDn", certify.getSubjectDn());
                map.putString("subjectCn", certify.getSubjectCn());
                map.putString("policy", certify.getPolicy());
                map.putString("notAfterDate", certify.getNotAfterDate());
                rows.pushMap(map);
            }

            promise.resolve(rows);
        }
        catch(Exception ex) {
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

        }
        catch(Exception ex) {
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
        }
        catch(Exception ex) {
            promise.reject(ex);
        }
    }


    @ReactMethod
    public void exportFile(String subjectDn, String path, Promise promise) {
        try {
            RSKSWCertManager manager = RSKSWCertManager.getInstance(reactContext);
            promise.resolve(null);
        }
        catch(Exception ex) {
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
        }
        catch(Exception ex) {
            promise.reject(ex);
        }
    }


    @ReactMethod
    public void getReceiveCode(Promise promise) {

        try {

            if (icrp == null) {
                int ret1 = RSKSWICRProtocol.getLibraryState(reactContext);
                if (ret1 != 8) throw new Exception("License Not Allowed");

                icrp = new RSKSWICRProtocol(reactContext, "211.32.131.182", 10500);
            }

            Hashtable<String, Object> res = icrp.import1();
            if (res.containsKey("RANDOMNUMBER") && res.get("RANDOMNUMBER") != null) {
                WritableMap result = Arguments.createMap();
                result.putString("code", res.get("RANDOMNUMBER").toString());
                promise.resolve(result);
            }
            else {
                throw new Exception(res.get("MESSAGE").toString());
            }
        }
        catch(Exception ex) {
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
                result.putString("subjectDn", cert.getSubjectDn());
                result.putString("subjectCn", cert.getSubjectCn());
                result.putString("policy", cert.getPolicy());
                result.putString("notAfterDate", cert.getNotAfterDate());
                promise.resolve(result);
            }
            else if (res.get("CODE").toString().equalsIgnoreCase("SC203")) {
                byte[] importedCert = icrp.getCert();
                byte[] importedKey = icrp.getKey();

                boolean save = manager.saveCert(importedCert, importedKey, icrp.getKmCert(), icrp.getKmKey());
                if (!save) throw new Exception("Type C");

                cert = new RSKSWCertificate(importedCert, null);
                WritableMap result = Arguments.createMap();
                result.putString("subjectDn", cert.getSubjectDn());
                result.putString("subjectCn", cert.getSubjectCn());
                result.putString("policy", cert.getPolicy());
                result.putString("notAfterDate", cert.getNotAfterDate());
                promise.resolve(result);
            }
            else {
                throw new Exception(res.get("MESSAGE").toString());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
            promise.reject(ex);
        }
    }

}
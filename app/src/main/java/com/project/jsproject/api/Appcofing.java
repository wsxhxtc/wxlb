package com.project.jsproject.api;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

public class Appcofing {
    public static boolean state = true;
    public static String username = "";

    public static OkHttpClient getOkHttpClient() {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectionSpecs(Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS,
                    ConnectionSpec.CLEARTEXT)).build();
         return okHttpClient;
    }
}

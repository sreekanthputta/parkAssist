package com.slabs.parkassist;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by Sreekanth Putta on 22-12-2017.
 */

public class BookNowActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ImageView imageView;
    private int minutesElapsed = 0;

    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        setContentView(R.layout.activity_booknow);
        imageView = (ImageView) findViewById(R.id.qrcode);

        Long time = sharedPreferences.getLong("timeSync", 0);
        Long trueTime = (System.currentTimeMillis() + time);
        long seconds = trueTime%60000;
        trueTime -= seconds;
        trueTime /= 1000;

        generateQrCode(trueTime);

        final Long finalTrueTime = trueTime;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                generateQrCode(finalTrueTime + minutesElapsed);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, seconds, 60000);
    }

    void generateQrCode(Long trueTime){
        try {
            String secretkey = sharedPreferences.getString("secretKey","");
            Log.d("SDfc", secretkey+trueTime+"slabs");
            String message = encode(secretkey+trueTime+"slabs");
            Bitmap bitmap = encodeAsBitmap(secretkey + message);
            imageView.invalidate();
            imageView.setImageBitmap(bitmap);
            minutesElapsed++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 500, 500, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

    public static String encode(String data) throws Exception {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(data.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

}

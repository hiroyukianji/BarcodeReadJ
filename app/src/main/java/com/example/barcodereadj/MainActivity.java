package com.example.barcodereadj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class MainActivity extends AppCompatActivity {

    /**
     * 参考サイト
     * https://tech.every.tv/entry/2023/12/16/1
     * https://developers.google.com/ml-kit/vision/barcode-scanning/code-scanner?hl=ja#java
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.read_barcode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textBarcode = findViewById(R.id.textBarcode);
                textBarcode.setText("");
                startReader();
            }
        });
    }
    /**
     * バーコード読取処理
     */
    private void startReader() {

        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(            // 読み取るバーコードの種別を設定
                    Barcode.FORMAT_QR_CODE     // 今回は QR コードを読み取るよう設定
            )
            .enableAutoZoom()              // 自動ズーム有効
            .build();

        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this, options);

        // ここを実行するとバーコードスキャンの画面が起動する
        scanner
            .startScan()
            .addOnSuccessListener(
                barcode -> {
                    // Task completed successfully
                    //
                    String rawValue = barcode.getRawValue();
                    Toast toast;
                    int duration = Toast.LENGTH_LONG;
                    toast = Toast.makeText(this,  rawValue, duration);
                    toast.show();
                    // 画面表示
                    TextView textBarcode = findViewById(R.id.textBarcode);
                    textBarcode.setText(rawValue);
                })
            .addOnCanceledListener(
                () -> {
                    // Task canceled
                    Toast toast;
                    int duration = Toast.LENGTH_LONG;
                    toast = Toast.makeText(this,  "@string/barcode_cancel", duration);
                })
            .addOnFailureListener(
                e -> {
                    // Task failed with an exception
                    Toast toast;
                    int duration = Toast.LENGTH_LONG;
                    toast = Toast.makeText(this,  "@string/barcode_fail", duration);
                });
    }
}
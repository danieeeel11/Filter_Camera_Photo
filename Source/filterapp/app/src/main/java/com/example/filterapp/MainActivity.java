package com.example.filterapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Core;

import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button button;
    Button buttonGalery;
    TextView textView;

    private Bitmap selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        OpenCVLoader.initDebug();

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        img = findViewById(R.id.img);
        button = findViewById(R.id.button);
        buttonGalery = findViewById(R.id.buttonGaleria);
        textView = findViewById(R.id.txt);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 101);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });

        buttonGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 102);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFilters();
            }
        });
    }

    public Bitmap filterBordes(Bitmap originalBitmap){
        //Filtro realce de bordes
        // Convert Bitmap to Mat
        Mat imgMat = new Mat();
        Utils.bitmapToMat(originalBitmap, imgMat);

        // Apply OpenCV filter
        Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_RGB2BGRA);
        Mat imgResult = new Mat();
        Imgproc.Canny(imgMat, imgResult, 80, 90);

        // Convert Mat to Bitmap
        Bitmap filteredBitmap = Bitmap.createBitmap(imgResult.cols(), imgResult.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(imgResult, filteredBitmap);

        return filteredBitmap;
    }

    public Bitmap filterNegative(Bitmap originalBitmap){
        Mat originalMat = new Mat();
        Mat negativeMat = new Mat();
        Bitmap negativeBitmap;

        // Convertir el Bitmap original a Mat
        Utils.bitmapToMat(originalBitmap, originalMat);

        // Aplicar el filtro negativo
        Imgproc.cvtColor(originalMat, negativeMat, Imgproc.COLOR_RGBA2RGB);
        Core.bitwise_not(negativeMat, negativeMat);

        // Convertir el Mat resultante a Bitmap
        negativeBitmap = Bitmap.createBitmap(negativeMat.cols(), negativeMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(negativeMat, negativeBitmap);

        return negativeBitmap;
    }

    public Bitmap rotatedImg(int grados, Bitmap originalBitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(grados);
        Bitmap rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

        return rotatedBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Abrir Camara
        if (requestCode == 101) {
            Bitmap originalBitmap = (Bitmap) data.getExtras().get("data");
            // Guardar la imagen seleccionada para su posterior uso
            selectedImage = originalBitmap;

            //img.setImageBitmap(filterNegative(rotatedImg(90, originalBitmap)));
            img.setImageBitmap(filterNegative(originalBitmap));
            textView.setText("Filtro: Negativo");
        }

        // Abrir Galeria
        if (requestCode == 102 && resultCode == RESULT_OK && data != null) {
            //Bitmap originalBitmap = (Bitmap) data.getExtras().get("data");
            //img.setImageBitmap(filterNegative(originalBitmap));
            Uri selectedImageUri = data.getData();
            //img.setImageURI(selectedImageUri);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                // Guardar la imagen seleccionada para su posterior uso
                selectedImage = bitmap;
                // Aquí puedes hacer lo que necesites con el Bitmap
                img.setImageBitmap(filterNegative(bitmap));
                textView.setText("Filtro: Negativo");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void switchFilters() {
        if (selectedImage != null) {
            // Cambiar entre filtros
            if (img.getTag() == null || img.getTag().equals("NEGATIVO")) {
                img.setImageBitmap(filterNegative(selectedImage));
                img.setTag("BORDES");
                textView.setText("Filtro: Negativo");
            } else {
                img.setImageBitmap(filterBordes(selectedImage));
                img.setTag("NEGATIVO");
                textView.setText("Filtro: Realce de bordes");
            }
        }
    }

}
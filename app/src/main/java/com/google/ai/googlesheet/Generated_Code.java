package com.google.ai.googlesheet;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Generated_Code extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog datePickerDialog;
    private FrameLayout      dateofdelivery;
    private Button           inputdata, ResultQrCode, Cancel, weekpick, timepick, endofcampaigndate;
    private EditText campaignid, deliveryperson, totaldeliverycoast, deliveryarea, deliverpostcode, totalnumbercoupon, couponofferdiskonvalue;
    private TextView         tvDateResult;
    private SimpleDateFormat dateFormatter;
    private ImageView        hasil;
    String text2Qr,userImage;

    MultiFormatWriter multi = new MultiFormatWriter();
    private StringRequest stringRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated__code);

        ResultQrCode = findViewById(R.id.btn_save_details);
        campaignid = findViewById(R.id.Campaign_Data);
        hasil = findViewById(R.id.tv_qrcode);
        ResultQrCode.setOnClickListener(this);

        Cancel = findViewById(R.id.btn_cancel);
        Cancel.setOnClickListener(this);

        inputdata = findViewById(R.id.btn_Input_Data);
        inputdata.setOnClickListener(this);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        tvDateResult = (TextView) findViewById(R.id.tv_dateresult);
        dateofdelivery = (FrameLayout) findViewById(R.id.btn_datepicker);
        dateofdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {


        Calendar newCalendar = Calendar.getInstance();


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                tvDateResult.setText("Date of Delivery : " + dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    private void Input_DataToSheet() {
        qrcode();

        final ProgressDialog loading  = ProgressDialog.show(this, "Adding Item", "Please wait");
        final String         brand    = tvDateResult.getText().toString().trim();
        final String         ItemName = campaignid.getText().toString().trim();
final String u = userImage;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzq9n121NSzWcxaTyooc7PdyQGnQaK7Wwcu0pdMQJNOYOI2qQ0/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Generated_Code.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action", "addItem");
                parmas.put("umur", brand);
                parmas.put("nama", ItemName);
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_details:

                Input_DataToSheet();
                break;


            case R.id.btn_cancel:
                Intent cancelintent = new Intent(Generated_Code.this, MainActivity.class);
                startActivity(cancelintent);
                break;
        }
    }

    private void qrcode() {

        text2Qr = campaignid.getText() + "\n" + tvDateResult.getText().toString();
        try {
            BitMatrix      bitMatrix = multi.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder encode    = new BarcodeEncoder();
            Bitmap         bitmap    = encode.createBitmap(bitMatrix);
                    userImage = getStringImage(bitmap);
            hasil.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImages = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImages;
    }
}
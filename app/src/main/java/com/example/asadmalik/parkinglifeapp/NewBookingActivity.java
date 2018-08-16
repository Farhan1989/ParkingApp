package com.example.asadmalik.parkinglifeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewBookingActivity extends AppCompatActivity {

//    TextView textViewUserName;
//    TextView textViewDate;
//    TextView textViewTime;
//    TextView textViewDuration;
//
//    EditText editTextDate;
//    EditText editTextTime;
//    EditText editTextDuration;
//    Button buttonAddBooking;
    SurfaceView cameraPreview;
    TextView txtResult;
    Button payButton;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionId = 1001;
    private static final String SHARED_PREF_NAME = "email";
    private static final String KEY_NAME_EMAIL = "key_email";
    private static final String KEY_NAME_START_TIME = "key_start_time";
    private static final double parkingRate = 0.50; //parking rate per min in Euros
    DatabaseReference databaseReference;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case RequestCameraPermissionId: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);
//        textViewUserName = (TextView) findViewById(R.id.textViewName);
//        textViewDate = (TextView) findViewById(R.id.textViewDate);
//        textViewTime = (TextView) findViewById(R.id.textViewTime);
//        textViewDuration = (TextView) findViewById(R.id.textViewDuration);
//        editTextDate = (EditText) findViewById(R.id.editTextDate);
//        editTextTime = (EditText) findViewById(R.id.editTextTime);
//        editTextDuration = (EditText) findViewById(R.id.editTextDuration);
//
//        buttonAddBooking = (Button) findViewById(R.id.buttonAddBooking);
//
//        Intent intent = getIntent();
//
//        String id = intent.getStringExtra(MyAccountActivity.USER_ID);
//        String name = intent.getStringExtra(MyAccountActivity.USER_NAME);
//
//   textViewUserName.setText(name);
//        databaseReference = FirebaseDatabase.getInstance().getReference("booking").child(id);
//
//        buttonAddBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveBooking();
//            }
//        });
//    }
//
//        private void saveBooking(){
//            String date = editTextDate.getText().toString().trim();
//            String time = textViewTime.getText().toString().trim();
//            String duration = textViewDuration.getText().toString().trim();
//
//            if(!TextUtils.isEmpty(date)){
//                String id = databaseReference.push().getKey();
//                Booking booking = new Booking(id, date, time, duration);
//
//                databaseReference.child(id).setValue(booking);
//                Toast.makeText(this,"Booking Save Successfully", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(this, "date should not be empty", Toast.LENGTH_LONG).show();
//            }
        }
        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);
        payButton = (Button) findViewById(R.id.payButton);
        payButton.setVisibility(View.GONE);


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    //Request Permission
                    ActivityCompat.requestPermissions(NewBookingActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionId);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if(qrCodes.size() != 0)
                {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //Create vibrate
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(500);
                            if("Start timer".equalsIgnoreCase(qrCodes.valueAt(0).displayValue)){
                                Date currentTime = Calendar.getInstance().getTime();

                                SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(KEY_NAME_START_TIME, currentTime.toString());
                                editor.apply();
                                String email = sp.getString(KEY_NAME_EMAIL, null);

                                txtResult.setText("Welcome "+ email + "\nMeter start time is: "+ sp.getString(KEY_NAME_START_TIME, null));
                            }else if("End timer".equalsIgnoreCase(qrCodes.valueAt(0).displayValue)){

                                SharedPreferences spObj = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                String dateString = spObj.getString(KEY_NAME_START_TIME, null);
                                DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                                try {
                                    Date startDate = format.parse(dateString);
                                    Date endDate = Calendar.getInstance().getTime();
                                    long diffInMs = endDate.getTime() - startDate.getTime();
                                    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMs);
                                    double costOfParking = diffInMinutes * parkingRate;

                                    txtResult.setText("Total cost of parking is: €"+ costOfParking);
                                    payButton.setVisibility(View.VISIBLE);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                txtResult.setText("QR code not supported!!");
                            }
                        }
                    });
                }
            }
        });
    }
}

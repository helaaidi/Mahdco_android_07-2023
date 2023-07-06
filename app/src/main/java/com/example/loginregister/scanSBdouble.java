package com.example.loginregister;


import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraSelector.Builder;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysis.Analyzer;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
        mv = {1, 5, 1},
        k = 1,
        d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 62\u00020\u0001:\u00016B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nH\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020 H\u0002J\b\u0010\"\u001a\u00020 H\u0002J\b\u0010#\u001a\u00020$H\u0002J\u0012\u0010%\u001a\u00020 2\b\u0010&\u001a\u0004\u0018\u00010'H\u0014J+\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020\n2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\u0006\u0010-\u001a\u00020.H\u0016¢\u0006\u0002\u0010/J\u0018\u00100\u001a\u00020 2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0003J\b\u00105\u001a\u00020 H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u00067"},
        d2 = {"Lcom/example/loginregister/scanSBdouble;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "analysisUseCase", "Landroidx/camera/core/ImageAnalysis;", "cameraProvider", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "cameraSelector", "Landroidx/camera/core/CameraSelector;", "lensFacing", "", "previewUseCase", "Landroidx/camera/core/Preview;", "pvScan", "Landroidx/camera/view/PreviewView;", "getPvScan", "()Landroidx/camera/view/PreviewView;", "setPvScan", "(Landroidx/camera/view/PreviewView;)V", "scanResultTextView", "Landroid/widget/TextView;", "getScanResultTextView", "()Landroid/widget/TextView;", "setScanResultTextView", "(Landroid/widget/TextView;)V", "screenAspectRatio", "getScreenAspectRatio", "()I", "aspectRatio", "width", "height", "bindAnalyseUseCase", "", "bindCameraUseCases", "bindPreviewUseCase", "isCameraPermissionGranted", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "requestCode", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "processImageProxy", "barcodeScanner", "Lcom/google/mlkit/vision/barcode/BarcodeScanner;", "imageProxy", "Landroidx/camera/core/ImageProxy;", "setupCamera", "Companion", "LoginRegister.app"}
)
public final class scanSBdouble extends AppCompatActivity {
    public PreviewView pvScan;
    public  TextView scanResultTextView;
    private ProcessCameraProvider cameraProvider;
    private CameraSelector cameraSelector;
    private int lensFacing = 1;
    private Preview previewUseCase;
    private ImageAnalysis analysisUseCase;
    private static final int PERMISSION_CAMERA_REQUEST = 1;
    private static final double RATIO_4_3_VALUE = 1.3333333333333333D;
    private static final double RATIO_16_9_VALUE = 1.7777777777777777D;
    public static String qrcode, machineid, digitex, machine;
    RequestQueue queue;
    public static boolean state = false;
    @NotNull
    public static final scanSBdouble.Companion Companion = new scanSBdouble.Companion((DefaultConstructorMarker)null);

    @NotNull
    public final PreviewView getPvScan() {
        PreviewView var10000 = this.pvScan;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pvScan");
        }

        return var10000;
    }

    public final void setPvScan(@NotNull PreviewView var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.pvScan = var1;
    }

    @NotNull
    public final TextView getScanResultTextView() {
        TextView var10000 = this.scanResultTextView;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scanResultTextView");
        }

        return var10000;
    }

    public final void setScanResultTextView(@NotNull TextView var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.scanResultTextView = var1;

    }

    private final int getScreenAspectRatio() {
        DisplayMetrics var2 = new DisplayMetrics();
        boolean var4 = false;
        PreviewView var10000 = this.pvScan;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pvScan");
        }

        Display var5 = var10000.getDisplay();
        if (var5 != null) {
            var5.getRealMetrics(var2);
        }

        return this.aspectRatio(var2.widthPixels, var2.heightPixels);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scansbdouble);
        scanResultTextView = findViewById(R.id.scanResultTextView);
        pvScan = findViewById(R.id.scanPreview);
        this.setupCamera();
    }

    @SuppressLint("WrongConstant")
    private final void setupCamera() {
        this.cameraSelector = (new Builder()).requireLensFacing(this.lensFacing).build();
        ListenableFuture var10000 = ProcessCameraProvider.getInstance((Context)this);
        Intrinsics.checkNotNullExpressionValue(var10000, "ProcessCameraProvider.getInstance(this)");
        final ListenableFuture cameraProviderFuture = var10000;
        cameraProviderFuture.addListener((Runnable)(new Runnable() {
            public final void run() {
                try {
                    scanSBdouble.this.cameraProvider = (ProcessCameraProvider)cameraProviderFuture.get();
                    if (scanSBdouble.this.isCameraPermissionGranted()) {
                        scanSBdouble.this.bindCameraUseCases();
                    } else if (VERSION.SDK_INT >= 23) {
                        scanSBdouble.this.requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
                    }
                } catch (ExecutionException var2) {
                    Log.e("QrScanViewModel", "Unhandled exception", (Throwable)var2);
                } catch (InterruptedException var3) {
                    Log.e("QrScanViewModel", "Unhandled exception", (Throwable)var3);
                }

            }
        }), ContextCompat.getMainExecutor((Context)this));
    }

    private final void bindCameraUseCases() {
        this.bindPreviewUseCase();
        this.bindAnalyseUseCase();
    }

    private final void bindPreviewUseCase() {
        if (this.cameraProvider != null) {
            ProcessCameraProvider var10000;
            if (this.previewUseCase != null) {
                var10000 = this.cameraProvider;
                if (var10000 != null) {
                    var10000.unbind(new UseCase[]{(UseCase)this.previewUseCase});
                }
            }

            Preview.Builder var10001 = (new Preview.Builder()).setTargetAspectRatio(this.getScreenAspectRatio());
            PreviewView var10002 = this.pvScan;
            if (var10002 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pvScan");
            }

            Display var9 = var10002.getDisplay();
            Intrinsics.checkNotNullExpressionValue(var9, "pvScan.display");
            this.previewUseCase = var10001.setTargetRotation(var9.getRotation()).build();
            Preview var6 = this.previewUseCase;
            if (var6 != null) {
                PreviewView var8 = this.pvScan;
                if (var8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pvScan");
                }

                var6.setSurfaceProvider(var8.getSurfaceProvider());
            }

            try {
                CameraSelector var7 = this.cameraSelector;
                if (var7 != null) {
                    CameraSelector var1 = var7;
                    boolean var3 = false;
                    var10000 = this.cameraProvider;
                    if (var10000 != null) {
                        var10000.bindToLifecycle((LifecycleOwner)this, var1, new UseCase[]{(UseCase)this.previewUseCase});
                    }
                }
            } catch (IllegalStateException var4) {
            } catch (IllegalArgumentException var5) {
            }

        }
    }

    private final void bindAnalyseUseCase() {
        BarcodeScanner var10000 = BarcodeScanning.getClient();
        Intrinsics.checkNotNullExpressionValue(var10000, "BarcodeScanning.getClient()");
        final BarcodeScanner barcodeScanner = var10000;
        if (this.cameraProvider != null) {
            ProcessCameraProvider var8;
            if (this.analysisUseCase != null) {
                var8 = this.cameraProvider;
                if (var8 != null) {
                    var8.unbind(new UseCase[]{(UseCase)this.analysisUseCase});
                }
            }

            ImageAnalysis.Builder var10001 = (new ImageAnalysis.Builder()).setTargetAspectRatio(this.getScreenAspectRatio());
            PreviewView var10002 = this.pvScan;
            if (var10002 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pvScan");
            }

            Display var11 = var10002.getDisplay();
            Intrinsics.checkNotNullExpressionValue(var11, "pvScan.display");
            this.analysisUseCase = var10001.setTargetRotation(var11.getRotation()).build();
            ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();
            ImageAnalysis var9 = this.analysisUseCase;
            if (var9 != null) {
                var9.setAnalyzer((Executor)cameraExecutor, (Analyzer)(new Analyzer() {
                    public final void analyze(@NotNull ImageProxy imageProxy) {
                        Intrinsics.checkNotNullParameter(imageProxy, "imageProxy");
                        scanSBdouble.this.processImageProxy(barcodeScanner, imageProxy);
                    }
                }));
            }

            try {
                CameraSelector var10 = this.cameraSelector;
                if (var10 != null) {
                    CameraSelector var3 = var10;
                    boolean var5 = false;
                    var8 = this.cameraProvider;
                    if (var8 != null) {
                        var8.bindToLifecycle((LifecycleOwner)this, var3, new UseCase[]{(UseCase)this.analysisUseCase});
                    }
                }
            } catch (IllegalStateException var6) {
            } catch (IllegalArgumentException var7) {
            }

        }
    }

    @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
    private final void processImageProxy(BarcodeScanner barcodeScanner, final ImageProxy imageProxy) {
        if (imageProxy.getImage() != null) {
            Image var10000 = imageProxy.getImage();
            ImageInfo var10001 = imageProxy.getImageInfo();
            Intrinsics.checkNotNullExpressionValue(var10001, "imageProxy.imageInfo");
            InputImage var4 = InputImage.fromMediaImage(var10000, var10001.getRotationDegrees());
            Intrinsics.checkNotNullExpressionValue(var4, "InputImage.fromMediaImag…mageInfo.rotationDegrees)");
            InputImage inputImage = var4;
            barcodeScanner.process(inputImage).addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
                // $FF: synthetic method
                // $FF: bridge method
                public void onSuccess(Object var1) {
                    this.onSuccess((List)var1);
                }

                public final void onSuccess(List barcodes) {
                    Intrinsics.checkNotNullExpressionValue(barcodes, "barcodes");
                    Barcode barcode = (Barcode)CollectionsKt.getOrNull(barcodes, 0);
                    if (barcode != null) {
                        String var10000 = barcode.getRawValue();
                        if (var10000 != null) {
                            String var3 = var10000;
                            boolean var5 = false;
                            scanSBdouble.this.getScanResultTextView().setText((CharSequence)var3);
                            qrcode = ((CharSequence)var3).toString();
                            if(!qrcode.equals("")) {

                                if (state) {
                                    queue = Volley.newRequestQueue(getApplicationContext());
                                    String url3 = controller.ip2 + "/productionLine/bydigitex/" + qrcode;
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                            (Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    if (!response.toString().equals("{}")) {
                                                        machineid = response.optString("machine_id");
                                                    } else {
                                                        machineid = "";
                                                    }
                                                }
                                            }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // TODO: Handle error

                                                }
                                            });

                                    queue.add(jsonObjectRequest);

                                    // on below line we are calling a string
                                    // request method to post the data to our API
                                    // in this we are calling a post method.

                                    String url = controller.ip2 + "/operation/MultiMachine/?model=" + DashboardFragment.selectedCountry + "&operation_code=" + DashboardFragment.resultArray2[0];

                                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {

                                            for (int i = 0; i < response.length(); i++) {
                                                // creating a new json object and
                                                // getting each object from our json array.
                                                try {
                                                    // we are getting each json object.
                                                    JSONObject responseObj = response.getJSONObject(i);
                                                    digitex = responseObj.optString("smartbox");
                                                    machine = responseObj.optString("machine_id");

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            if (!digitex.equals("")) {

                                                if (DashboardFragment.op_secondaire.isChecked()) {
                                                    String url4 = controller.ip2 + "/operation/MultiMachine";
                                                    JSONObject postObject = new JSONObject();

                                                    try {
                                                        //historyObject.put("id","1");
                                                        postObject.put("digitex", digitex + "/" + qrcode);
                                                        postObject.put("operation_code", DashboardFragment.resultArray2[0]);
                                                        postObject.put("model", DashboardFragment.selectedCountry);
                                                        postObject.put("machine_ref", machine + "/" + machineid);
//                            postObject.put("potential",DashboardFragment.rend);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    Log.e("LoginActivityJsonObject", "" + postObject);
                                                    JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url4, postObject,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    Log.e("LoginActivity", "OnResponse: " + response);
                                                                    Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                                                    DashboardFragment.alertDialog1.cancel();
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.e("OnError", String.valueOf(error.getMessage()));
                                                        }
                                                    });


                                                    queue.add(objRequest);
                                                } else if (DashboardFragment.op_principale.isChecked()) {

                                                    String url1 = controller.ip2 + "/operation/bymodelPrin";
                                                    JSONObject postObject = new JSONObject();

                                                    try {
                                                        //historyObject.put("id","1");
                                                        postObject.put("digitex", qrcode);
                                                        postObject.put("digitexMulti", digitex + "/" + qrcode);
                                                        postObject.put("operation_code", DashboardFragment.resultArray2[0]);
                                                        postObject.put("model", DashboardFragment.selectedCountry);
                                                        postObject.put("machine_ref", machine + "/" + machineid);
//                            postObject.put("potential",DashboardFragment.rend);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    Log.e("LoginActivityJsonObject", "" + postObject);
                                                    JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url1, postObject,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    Log.e("LoginActivity", "OnResponse: " + response);
                                                                    Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                                                    DashboardFragment.alertDialog1.cancel();
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.e("OnError", String.valueOf(error.getMessage()));
                                                        }
                                                    });


                                                    queue.add(objRequest);
                                                }

                                            } else {

                                                if (DashboardFragment.op_principale.isChecked()) {
                                                    String url2 = controller.ip2 + "/operation/bymodel";
                                                    JSONObject postObject = new JSONObject();

                                                    try {
                                                        //historyObject.put("id","1");
                                                        postObject.put("digitex", qrcode);
                                                        postObject.put("operation_code", DashboardFragment.resultArray2[0]);
                                                        postObject.put("model", DashboardFragment.selectedCountry);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    Log.e("LoginActivityJsonObject", "" + postObject);
                                                    JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url2, postObject,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    Log.e("LoginActivity", "OnResponse: " + response);
                                                                    Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                                                    DashboardFragment.alertDialog1.cancel();
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.e("OnError", String.valueOf(error.getMessage()));
                                                        }
                                                    });


                                                    queue.add(objRequest);
                                                } else if (DashboardFragment.op_secondaire.isChecked()) {
                                                    JSONObject postObject = new JSONObject();
                                                    String url6 = controller.ip2 + "/operation/bymodelSec";

                                                    try {
                                                        //historyObject.put("id","1");
                                                        postObject.put("digitex", qrcode);
                                                        postObject.put("operation_code", DashboardFragment.resultArray2[0]);
                                                        postObject.put("model", DashboardFragment.selectedCountry);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    Log.e("LoginActivityJsonObject", "" + postObject);
                                                    JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url6, postObject,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    Log.e("LoginActivity", "OnResponse: " + response);
                                                                    Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                                                    DashboardFragment.alertDialog1.cancel();
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.e("OnError", String.valueOf(error.getMessage()));
                                                        }
                                                    });


                                                    queue.add(objRequest);
                                                }
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    });
                                    queue.add(jsonArrayRequest);
                                    state=false;
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                            }
                            //SystemClock.sleep(1000);
                            finish();
                        }
                    }

                }
            })).addOnFailureListener((OnFailureListener)null).addOnCompleteListener((OnCompleteListener)(new OnCompleteListener() {
                public final void onComplete(Task it) {
                    imageProxy.close();
                }
            }));
        }
    }

    private final int aspectRatio(int width, int height) {
        double previewRatio = (double)Math.max(width, height) / (double)Math.min(width, height);
        double var5 = previewRatio - 1.3333333333333333D;
        double var10000 = Math.abs(var5);
        var5 = previewRatio - 1.7777777777777777D;
        return var10000 <= Math.abs(var5) ? 0 : 1;
    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        if (requestCode == 1 && this.isCameraPermissionGranted()) {
            this.setupCamera();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final boolean isCameraPermissionGranted() {
        scanSBdouble it = (scanSBdouble)this;
        boolean var3 = false;
        return ContextCompat.checkSelfPermission((Context)it, "android.permission.CAMERA") == 0;
    }

    // $FF: synthetic method
    public static final ProcessCameraProvider access$getCameraProvider$p(scanSBdouble $this) {
        return $this.cameraProvider;
    }

    @Metadata(
            mv = {1, 5, 1},
            k = 1,
            d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b"},
            d2 = {"Lcom/example/loginregister/scanSBdouble$Companion;", "", "()V", "PERMISSION_CAMERA_REQUEST", "", "RATIO_16_9_VALUE", "", "RATIO_4_3_VALUE", "LoginRegister.app"}
    )
    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

}

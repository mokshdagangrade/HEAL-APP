package com.example.heal.customerui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.heal.CustomerHomeActivity;
import com.example.heal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VolunteerFragment extends Fragment {
    private EditText mNameField, mPhoneField, mContactField, mAadharField, mCarNameField, mCarNoField;
    private Button mBack, mConfirm;
    ImageButton mUpload1, mUpload2, mUpload3;
    private ImageView mProfileImage;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private String userID;
    private String mName;
    private String mPhone;
    private String mContact;
    private String mAadhar;
    private String mCarName;
    private String mCarNo;
    private String mProfileImageUrl;
    private String mAadharUrl;

    private Uri resultUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer, null, false);
        mNameField = (EditText) view.findViewById(R.id.name_vol);
        mPhoneField = (EditText) view.findViewById(R.id.phone_vol);
        mContactField = (EditText)view.findViewById(R.id.contact_vol);
        mCarNameField = (EditText)view.findViewById(R.id.car_name);
        mCarNoField = (EditText)view.findViewById(R.id.car_no);

        mProfileImage = (ImageView) view.findViewById(R.id.profileImage_vol);

        mBack = (Button) view.findViewById(R.id.back_vol);
        mConfirm = (Button) view.findViewById(R.id.confirm_vol);
        mUpload1 = (ImageButton) view.findViewById(R.id.upload_aadhar);
        mUpload2 = (ImageButton) view.findViewById(R.id.upload_license);
        mUpload3 = (ImageButton) view.findViewById(R.id.upload_car);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Volunteers").child(userID);

        getUserInfo();

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CustomerHomeActivity.class));
                getActivity().finish();
                return;
            }
        });
        mUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();


            }
        });
        mUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });
        mUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });

        return view;
    }

    private void selectPdf() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);
    }

    private void getUserInfo(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        mName = map.get("name").toString();
                        mNameField.setText(mName);
                    }
                    if(map.get("phone")!=null){
                        mPhone = map.get("phone").toString();
                        mPhoneField.setText(mPhone);
                    }
                    if(map.get("contact")!=null){
                        mContact = map.get("contact").toString();
                        mContactField.setText(mContact);
                    }
                    if(map.get("car name")!=null){
                        mCarName = map.get("car name").toString();
                        mCarNameField.setText(mCarName);
                    }
                    if(map.get("car no")!=null){
                        mCarNo = map.get("car no").toString();
                        mCarNoField.setText(mCarNo);
                    }
                    if(map.get("profileImageUrl")!=null){
                        mProfileImageUrl = map.get("profileImageUrl").toString();
                        Glide.with(getActivity()).load(mProfileImageUrl).into(mProfileImage);
                    }
                    if(map.get("aadharUrl")!=null){
                        mAadharUrl = map.get("aadharUrl").toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    private void saveUserInformation() {
        mName = mNameField.getText().toString();
        mPhone = mPhoneField.getText().toString();
        mContact = mContactField.getText().toString();
        mCarName = mCarNameField.getText().toString();
        mCarNo = mCarNoField.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name", mName);
        userInfo.put("phone", mPhone);
        userInfo.put("contact",mContact);
        userInfo.put("car name",mCarName);
        userInfo.put("car no",mCarNo);
        databaseReference.updateChildren(userInfo);

        if(resultUri != null) {

            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
            Bitmap bitmap = null;
            if(Build.VERSION.SDK_INT >= 29) {
                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getApplicationContext().getContentResolver(),resultUri);
                try{
                    bitmap = ImageDecoder.decodeBitmap(source);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplication().getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    startActivity(new Intent(getActivity(), CustomerHomeActivity.class));
                    getActivity().finish();
                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();

                    Map newImage = new HashMap();
                    newImage.put("profileImageUrl", downloadUrl.toString());
                    databaseReference.updateChildren(newImage);

                    startActivity(new Intent(getActivity(), AddAppointmentActivity.class));
                    getActivity().finish();
                    return;
                }
            });
        }else{
            startActivity(new Intent(getActivity(), AddAppointmentActivity.class));
            getActivity().finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
        if(requestCode == 12 && resultCode == Activity.RESULT_OK && data != null && data.getData()!=null){
            uploadPdf(data.getData());
        }
    }

    private void uploadPdf(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("File is loading...");
        progressDialog.show();

        StorageReference reference = FirebaseStorage.getInstance().getReference().child("aadhar_upload").child(userID);
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //mUpload1.setText("Uploaded");
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        Map newUpload = new HashMap();
                        newUpload.put("aadharUrl", uriTask.toString());
                        databaseReference.updateChildren(newUpload);
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        putPDF putPDF = new putPDF(uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
                        Toast.makeText(getActivity(),"File Uploaded",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Upload..." +(int) progress+ "%");
            }
        });

    }
}
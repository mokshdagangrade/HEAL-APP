package com.example.heal.customerui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    private EditText mNameField, mPhoneField, mContactField, mNotesField, mAgeField;

    private Button mBack, mConfirm;

    private ImageView mProfileImage, mbuttonContact;

    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;

    private String userID;
    private String mName;
    private String mPhone;
    private String mContact;
    private String mNotes, mAge;
    private String mProfileImageUrl;

    private Uri resultUri;
    public static final int REQUEST_SELECT_CONTACT = 10;
    public static final int REQUEST_READ_CONTACTS = 79;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings2, null, false);
        mNameField = (EditText) view.findViewById(R.id.name);
        mPhoneField = (EditText) view.findViewById(R.id.phone);
        mContactField = (EditText) view.findViewById(R.id.contact);
        mNotesField = (EditText) view.findViewById(R.id.notes);
        mAgeField = (EditText) view.findViewById(R.id.age);

        mProfileImage = (ImageView) view.findViewById(R.id.profileImage);
        mbuttonContact = (ImageView) view.findViewById(R.id.buttonContact);

        mBack = (Button) view.findViewById(R.id.back);
        mConfirm = (Button) view.findViewById(R.id.confirm);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userID);

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
        mbuttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED) {
                    openContact();
                } else {
                    requestPermission();
                }
            }
        });
        return view;
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openContact();
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void openContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        //if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        //}
    }

    private void getUserInfo() {
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        mName = map.get("name").toString();
                        mNameField.setText(mName);
                    }
                    if (map.get("phone") != null) {
                        mPhone = map.get("phone").toString();
                        mPhoneField.setText(mPhone);
                    }
                    if (map.get("contact") != null) {
                        mContact = map.get("contact").toString();
                        mContactField.setText(mContact);
                    }
                    if (map.get("notes") != null) {
                        mNotes = map.get("notes").toString();
                        mNotesField.setText(mNotes);
                    }
                    if (map.get("age") != null) {
                        mAge = map.get("age").toString();
                        mAgeField.setText(mAge);
                    }
                    if (map.get("profileImageUrl") != null) {
                        mProfileImageUrl = map.get("profileImageUrl").toString();
                        Glide.with(getActivity().getApplication()).load(mProfileImageUrl).into(mProfileImage);
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
        mNotes = mNotesField.getText().toString();
        mAge = mAgeField.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name", mName);
        userInfo.put("phone", mPhone);
        userInfo.put("contact", mContact);
        userInfo.put("notes", mNotes);
        userInfo.put("age", mAge);
        mCustomerDatabase.updateChildren(userInfo);

        if (resultUri != null) {

            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
            Bitmap bitmap = null;
            if (Build.VERSION.SDK_INT >= 29) {
                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getApplicationContext().getContentResolver(), resultUri);
                try {
                    bitmap = ImageDecoder.decodeBitmap(source);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
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
                    mCustomerDatabase.updateChildren(newImage);

                    startActivity(new Intent(getActivity(), CustomerHomeActivity.class));
                    getActivity().finish();
                    return;
                }
            });
        } else {
            startActivity(new Intent(getActivity(), CustomerHomeActivity.class));
            getActivity().finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK && null != data) {
            Uri contactUri = data.getData();
            //do what you want...
            Cursor phone = getActivity().getContentResolver().query(contactUri, null, null, null, null);
            if (phone.moveToFirst()) {
                /*String contactNumberName = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // Todo something when contact number selected
                mContactField.setText("Name: " + contactNumberName);*/
                String contactId = phone.getString(phone.getColumnIndex(ContactsContract.Contacts._ID));
                String hasNumber = phone.getString(phone.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String num = "";
                if (Integer.valueOf(hasNumber) == 1) {
                    Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (numbers.moveToNext()) {
                        num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (num.contains("+")) {
                            mContactField.setText(num.substring(3, num.length()).replaceAll("[^\\d.]", ""));
                        } else if (num.charAt(0) == 0) {
                            mContactField.setText(num.substring(1, num.length()).replaceAll("[^\\d.]", ""));
                        } else if (num.toString().startsWith("0")) {
                            String mContact = num.substring(num.indexOf("0") + 1);
                            mContactField.setText(mContact.replaceAll("[^\\d.]", ""));
                        } else {
                            mContactField.setText(num.replaceAll("[^\\d.]", ""));
                        }
                        mContactField.clearFocus();
                        mContactField.setSelection(mContactField.length());
                    }
                }
            }
        }
    }
}
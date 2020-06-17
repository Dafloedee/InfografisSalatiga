package com.dafloe.infografissalatiga.InsertKost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dafloe.infografissalatiga.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UploadGambar extends AppCompatActivity {

Button b1,b2,b3,b4,b5,simpan;
private DatabaseReference mDatabase;
int a = 0;
String namaKost;
ImageView img1,img2,img3,img4,img5;
StorageReference mStorageRef;
HashMap data,cover;
public Uri imgUri,imgUri2,imgUri3,imgUri4,imgUri5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_gambar);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        simpan = findViewById(R.id.button6);
        img1 = findViewById(R.id.ivKost1);
        img2 = findViewById(R.id.ivKost2);
        img3 = findViewById(R.id.ivKost3);
        img4 = findViewById(R.id.ivKost4);
        img5 = findViewById(R.id.ivKost5);
        data = new HashMap<>();
        cover = new HashMap<>();
        Intent intent = getIntent();
        namaKost = intent.getStringExtra("nama");
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageKost").child(namaKost);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 1;
                FileChooser();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=2;
                FileChooser();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=3;
                FileChooser();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=4;
                FileChooser();
            }
        });


        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=5;
                FileChooser();
            }
        });


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploader();
            }
        });

    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getFileExtensionFromUrl(cr.getType(uri));
    }

    private void FileUploader(){
       final StorageReference storageReference = mStorageRef.child
                ((System.currentTimeMillis()+"."+getExtension(imgUri)));

        final StorageReference storageReference2 = mStorageRef.child
                ((System.currentTimeMillis()+"."+getExtension(imgUri2)));

        final StorageReference storageReference3 = mStorageRef.child
                ((System.currentTimeMillis()+"."+getExtension(imgUri3)));

        final StorageReference storageReference4 = mStorageRef.child
                ((System.currentTimeMillis()+"."+getExtension(imgUri4)));

        final StorageReference storageReference5 = mStorageRef.child
                ((System.currentTimeMillis()+"."+getExtension(imgUri5)));

        storageReference.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl = uri;
                                String url = downloadUrl.toString();
                                cover.put("Cover",url);
                                data.put("Gambar1",url);
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                });

                    }
                });

        storageReference2.putFile(imgUri2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl2 = uri;
                                String url = downloadUrl2.toString();
                                data.put("Gambar2",url);
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                });

                    }
                });

        storageReference3.putFile(imgUri3)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl3 = uri;
                                String url = downloadUrl3.toString();
                                data.put("Gambar3",url);
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                });

                    }
                });

        storageReference4.putFile(imgUri4)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl4 = uri;
                                String url = downloadUrl4.toString();
                                data.put("Gambar4",url);
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                });

                    }
                });

        storageReference5.putFile(imgUri5)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl5 = uri;
                                String url = downloadUrl5.toString();
                                data.put("Gambar5",url);
                                callFirebase();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                });

                    }
                });

        mDatabase = FirebaseDatabase.getInstance().getReference("Kost");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.child(namaKost).child("Gambar").setValue(data);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(a==1){
            startActivityForResult(intent, 1);
        }
        else if (a==2){
            startActivityForResult(intent, 2);
        }
        else if (a==3){
            startActivityForResult(intent, 3);
        }
        else if (a==4){
            startActivityForResult(intent, 4);
        }
        else if (a==5){
            startActivityForResult(intent, 5);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            img1.setImageURI(imgUri);
        }
        else if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri2 = data.getData();
            img2.setImageURI(imgUri2);
        }
        else if (requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri3 = data.getData();
            img3.setImageURI(imgUri3);
        }
        else if (requestCode == 4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri4 = data.getData();
            img4.setImageURI(imgUri4);
        }
        else if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri5 = data.getData();
            img5.setImageURI(imgUri5);
        }

    }

    private void callFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Kost");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.child(namaKost).child("Gambar").setValue(data);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
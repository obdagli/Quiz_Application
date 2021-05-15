package tr.yildiz.OmerBurakDagli;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yildiz.edu.OmerBurakDagli.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class register extends AppCompatActivity {
    EditText etUsernamer, etPasswordr,etNamer,etSurnamer,etPhonenumberr,etEmailr,etBirthdater,etRePasswordr;
    Button btRegister,btSignin,btpchoice;
    String username;
    ImageView userphoto;
    Bitmap bitmap;
    int request_imageopen=0;
    private database v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        callInputs();
        v1 = new database(this);

        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,MainActivity.class));
                finish();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFill()){
                    if(etPasswordr.getText().toString().equals(etRePasswordr.getText().toString())){
                        try{
                            username = etUsernamer.getText().toString();
                            if(showdata(readdata(),username)){
                                createUser();
                                Intent intent = new Intent(register.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"Username already exists!", Toast.LENGTH_SHORT).show();
                            }

                        }finally{
                            v1.close();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Şifreler uyuşmuyor!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btpchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, request_imageopen);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == request_imageopen && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try{
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),uri);
                    bitmap = ImageDecoder.decodeBitmap(source); userphoto.setImageBitmap(bitmap);
                }else{
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    userphoto.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public byte[] BytetoArray(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }
    public boolean isFill(){
        if(etUsernamer.getText().toString().equals("")||etPasswordr.getText().toString().equals("")||etNamer.getText().toString().equals("")||etSurnamer.getText().toString().equals("")||etEmailr.getText().toString().equals("")||
        etPhonenumberr.getText().toString().equals("")||etBirthdater.getText().toString().equals("")||etRePasswordr.getText().toString().equals("")){
            return false;
        }
        return true;
    }
    public void createUser(){
        String name = etNamer.getText().toString();
        String surname = etSurnamer.getText().toString();
        String salt = passhash.getSalt(30);
        String hash = passhash.generateSecurePassword(etPasswordr.getText().toString(),salt);
        String email = etEmailr.getText().toString();
        String birthdate = etBirthdater.getText().toString();
        String phonenumber = etPhonenumberr.getText().toString();
        byte[] photo = BytetoArray();

        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("name",name);
        data.put("surname",surname);
        data.put("username",username);
        data.put("password",hash);
        data.put("email",email);
        data.put("phonenumber",phonenumber);
        data.put("birthdate",birthdate);
        data.put("userphoto",photo);
        data.put("salt",salt);
        db.insertOrThrow("User",null,data);
    }
    private String[] columns={"username"};
    private Cursor readdata(){
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor read = db.query("User",columns,null,null,null,null,null);
        return read;
    }
    private boolean showdata(Cursor show,String username){
        while( show.moveToNext()) {
            if(username.equals(show.getString(show.getColumnIndex("username")))){
                return false;
            }
        }
        return true;
    }
    public void callInputs(){
        etUsernamer = findViewById(R.id.et_usernamer);
        etPasswordr = findViewById(R.id.et_passwordr);
        etNamer = findViewById(R.id.et_name);
        etSurnamer = findViewById(R.id.et_surname);
        etEmailr = findViewById(R.id.et_email);
        etPhonenumberr = findViewById(R.id.et_phonenumber);
        etBirthdater = findViewById(R.id.et_birthdate);
        etRePasswordr = findViewById(R.id.et_repasswordr);
        btRegister = findViewById(R.id.bt_register);
        btSignin = findViewById(R.id.bt_signin);
        btpchoice = findViewById(R.id.bt_pchoice);
        userphoto = findViewById(R.id.u_photo);
    }
}
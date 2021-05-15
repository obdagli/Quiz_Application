package tr.yildiz.OmerBurakDagli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yildiz.edu.OmerBurakDagli.R;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btLogin,btSignup;
    private database v1;
    SharedPreferences sp;
    SharedPreferences.Editor speditor;
    int c_err = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callInputs();
        getSharedInformation();
        v1 = new database(this);
        /*SQLiteDatabase db = v1.getReadableDatabase();
        v1.onUpgrade(db,1,1);*/
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,register.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tb_username = etUsername.getText().toString();
                String tb_password = etPassword.getText().toString();
                    if(showdata(readdata(),tb_username,tb_password)){
                        speditor.putString("username",tb_username);
                        speditor.commit();
                        Intent intent = new Intent(MainActivity.this, welcome.class);
                        startActivity(intent);
                        finish();
                    }else{
                        c_err++;
                        if(c_err < 3){
                            Toast.makeText(getApplicationContext(),"Kullanıcı girişi hatalı! ", Toast.LENGTH_SHORT).show();
                        }else{
                            btLogin.setEnabled(false);
                            Toast.makeText(getApplicationContext(),"Hatalı kullanıcı girişi, belirlenen limiti aştı! ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,register.class));
                            finish();
                        }
                        clearInput();

                    }
                };
        });
    }
    public void clearInput(){
        etUsername.setText("");
        etPassword.setText("");
    }
    private String[] columns={"username","password","salt"};
    private Cursor readdata(){
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor read = db.query("User",columns,null,null,null,null,null);
        return read;
    }
    private boolean showdata(Cursor show,String username,String password){
        while( show.moveToNext()) {
            if(username.equals(show.getString(show.getColumnIndex("username"))) &&
                    passhash.verifyUserPassword(password,show.getString(show.getColumnIndex("password")),show.getString(show.getColumnIndex("salt")))){
                return true;
            }
        }
        return false;
    }
    /*
    public boolean LogCheck(String username, String password){
        for(Users obj : Users.users){
            if(username.equals(obj.getUsername()) && passhash.verifyUserPassword(password, obj.getHash(), obj.getSalt())){
                return true;
            }
        }
        return false;
    }*/
    public void callInputs(){
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btLogin = findViewById(R.id.bt_login);
        btSignup = findViewById(R.id.bt_signup);
    }
    public void getSharedInformation(){
        sp = getSharedPreferences("LogInInformation",MODE_PRIVATE);
        speditor = sp.edit();
    }
}
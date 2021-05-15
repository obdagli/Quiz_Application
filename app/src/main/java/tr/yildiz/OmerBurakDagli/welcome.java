package tr.yildiz.OmerBurakDagli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yildiz.edu.OmerBurakDagli.R;

public class welcome extends AppCompatActivity {
    Button btLogout,bt_question,bt_listquestion,bt_newquiz,bt_qsettings;
    TextView textViewMessage;
    String username,qtime,qpoint,qdiff;
    SharedPreferences sp;
    private database v1;
    ImageView u_photo;
    Bitmap bitmap;
    SharedPreferences.Editor speditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        callInputs();
        getSharedInformation();
        v1 = new database(this);
        username = sp.getString("username","not defined.");
        qtime = sp.getString("time","not defined.");
        qpoint = sp.getString("point","not defined.");
        qdiff = sp.getString("difficulty","not defined.");
        showdata(readdata(),username);



        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(welcome.this, MainActivity.class);
                    startActivity(intent);
                    finish();
            }
        });
        bt_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome.this, AddQuestion.class);
                startActivity(intent);
            }
        });
        bt_listquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome.this, ListQuestions.class);
                startActivity(intent);
            }
        });
        bt_qsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome.this, QuizSettings.class);
                startActivity(intent);
            }
        });
        bt_newquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speditor.putString("time",qtime);
                speditor.putString("point",qpoint);
                speditor.putString("difficulty",qdiff);
                speditor.commit();
                Intent intent = new Intent(welcome.this, NewQuiz.class);
                startActivity(intent);
            }
        });
    }
    private String[] columns={"username","name","surname","userphoto"};
    private Cursor readdata(){
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor read = db.query("User",columns,null,null,null,null,null);
        return read;
    }
    private void showdata(Cursor show,String username){
        while( show.moveToNext()) {
            if(username.equals(show.getString(show.getColumnIndex("username")))){
                textViewMessage.setText("Hi, "+show.getString(show.getColumnIndex("name"))+" "+show.getString(show.getColumnIndex("surname"))+"!");
                byte[] b = show.getBlob(show.getColumnIndex("userphoto"));
                bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
                u_photo.setImageBitmap(bitmap);
            }
        }
    }
    public void callInputs(){
        btLogout = findViewById(R.id.bt_logout);
        textViewMessage = findViewById(R.id.textViewMessage);
        bt_question = findViewById(R.id.bt_question);
        bt_listquestion = findViewById(R.id.bt_listquestion);
        bt_newquiz = findViewById(R.id.bt_createquiz);
        bt_qsettings = findViewById(R.id.bt_qsettings);
        u_photo = findViewById(R.id.u_photo);
    }
    public void getSharedInformation(){
        sp = getSharedPreferences("LogInInformation",MODE_PRIVATE);
        speditor = sp.edit();
    }
}
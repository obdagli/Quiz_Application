package tr.yildiz.OmerBurakDagli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yildiz.edu.OmerBurakDagli.R;

public class QuestionUpdate extends AppCompatActivity {
    EditText et_question, et_answer1, et_answer2, et_answer3, et_answer4;
    Button bt_submit;
    RadioButton cb_a,cb_b,cb_c,cb_d,rb_2,rb_3,rb_4;
    String username;
    SharedPreferences sp;
    SharedPreferences.Editor speditor;
    private database v1;
    String questionname;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_update);
        callInputs();
        getSharedInformation();
        Intent intent = getIntent();
        questionname = intent.getStringExtra("questionname");
        position = intent.getIntExtra("position",0);
        username = sp.getString("username","not defined.");
        v1 = new database(this);
        showdata(readdata(),questionname);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFill()){
                    AddQuestion(questionname);
                    Intent intent = new Intent(QuestionUpdate.this,ListQuestions.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"-------------------- ", Toast.LENGTH_SHORT).show();
                }
            }

        });
        rb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_answer3.setEnabled(false);
                et_answer4.setEnabled(false);
                et_answer3.setText(null);
                et_answer4.setText(null);
                cb_c.setEnabled(false);
                cb_d.setEnabled(false);
                cb_a.setChecked(true);

            }
        });
        rb_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_answer3.setEnabled(true);
                et_answer4.setEnabled(false);
                et_answer4.setText(null);
                cb_c.setEnabled(true);
                cb_d.setEnabled(false);
                cb_a.setChecked(true);
            }
        });
        rb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_answer3.setEnabled(true);
                et_answer4.setEnabled(true);
                cb_c.setEnabled(true);
                cb_d.setEnabled(true);
                cb_a.setChecked(true);
            }
        });
    }
    public void getSharedInformation(){
        sp = getSharedPreferences("LogInInformation",MODE_PRIVATE);
        speditor = sp.edit();
    }
    private String[] columns={"question","choice1","choice2","choice3","choice4","answer","choicesize","userfk"};
    private Cursor readdata(){
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor read = db.query("Question",columns,null,null,null,null,null);
        return read;
    }
    private void showdata(Cursor show,String qname){
        while( show.moveToNext()) {
            if(qname.equals(show.getString(show.getColumnIndex("question")))){
                et_question.setText(show.getString(show.getColumnIndex("question")));
                String temp = show.getString(show.getColumnIndex("choicesize"));
                if(temp.equals("2")){
                    rb_2.setChecked(true);
                    et_answer3.setEnabled(false);
                    et_answer4.setEnabled(false);
                    et_answer3.setText(null);
                    et_answer4.setText(null);
                    cb_c.setEnabled(false);
                    cb_d.setEnabled(false);
                    cb_a.setChecked(true);
                    et_answer1.setText(show.getString(show.getColumnIndex("choice1")));
                    et_answer2.setText(show.getString(show.getColumnIndex("choice2")));
                }else if(temp.equals("3")){
                    rb_3.setChecked(true);
                    et_answer3.setEnabled(true);
                    et_answer4.setEnabled(false);
                    et_answer4.setText(null);
                    cb_c.setEnabled(true);
                    cb_d.setEnabled(false);
                    cb_a.setChecked(true);
                    et_answer1.setText(show.getString(show.getColumnIndex("choice1")));
                    et_answer2.setText(show.getString(show.getColumnIndex("choice2")));
                    et_answer3.setText(show.getString(show.getColumnIndex("choice3")));
                }else if(temp.equals("4")){
                    rb_4.setChecked(true);
                    et_answer3.setEnabled(true);
                    et_answer4.setEnabled(true);
                    cb_c.setEnabled(true);
                    cb_d.setEnabled(true);
                    cb_a.setChecked(true);
                    et_answer1.setText(show.getString(show.getColumnIndex("choice1")));
                    et_answer2.setText(show.getString(show.getColumnIndex("choice2")));
                    et_answer3.setText(show.getString(show.getColumnIndex("choice3")));
                    et_answer4.setText(show.getString(show.getColumnIndex("choice4")));
                }
                if(cb_a.getText().toString().equals(show.getString(show.getColumnIndex("answer")))){
                    cb_a.setChecked(true);
                }else if(cb_b.getText().toString().equals(show.getString(show.getColumnIndex("answer")))){
                    cb_b.setChecked(true);
                }else if(cb_c.getText().toString().equals(show.getString(show.getColumnIndex("answer")))){
                    cb_c.setChecked(true);
                }else if(cb_d.getText().toString().equals(show.getString(show.getColumnIndex("answer")))){
                    cb_d.setChecked(true);
                }
            }
        }
    }
    public void callInputs(){
        et_question = findViewById(R.id.et_qtime);
        et_answer1 = findViewById(R.id.et_qpoint);
        et_answer2 = findViewById(R.id.et_answer2);
        et_answer3 = findViewById(R.id.et_answer3);
        et_answer4 = findViewById(R.id.et_answer4);
        cb_a = findViewById(R.id.rb_a);
        cb_b = findViewById(R.id.rb_b);
        cb_c = findViewById(R.id.rb_c);
        cb_d = findViewById(R.id.rb_d);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        rb_4 = findViewById(R.id.rb_4);
        bt_submit = findViewById(R.id.bt_questionupdate);


    }
    public boolean isFill(){
        if(et_question.getText().toString().equals("")||et_answer1.getText().toString().equals("")||et_answer2.getText().toString().equals("")|| !rbc() || !rbd()
                ||(!cb_a.isChecked() && !cb_b.isChecked() && !cb_c.isChecked() && !cb_d.isChecked())){
            return false;
        }
        return true;
    }
    public String checker(){
        if(cb_a.isChecked()){
            return "A";
        }else if(cb_b.isChecked()){
            return "B";
        }else if(cb_c.isChecked()){
            return "C";
        }else if(cb_d.isChecked()){
            return "D";
        }else{
            return "DQ'DWADWADQW";
        }
    }
    public void AddQuestion(String questionname){
        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("question",et_question.getText().toString());
        if(rb_2.isChecked()){
            data.put("choice1",et_answer1.getText().toString());
            data.put("choice2",et_answer2.getText().toString());
            data.put("choicesize","2");
        }else if(rb_3.isChecked()){
            data.put("choice1",et_answer1.getText().toString());
            data.put("choice2",et_answer2.getText().toString());
            data.put("choice3",et_answer3.getText().toString());
            data.put("choicesize","3");
        }else if(rb_4.isChecked()){
            data.put("choice1",et_answer1.getText().toString());
            data.put("choice2",et_answer2.getText().toString());
            data.put("choice3",et_answer3.getText().toString());
            data.put("choice4",et_answer4.getText().toString());
            data.put("choicesize","4");
        }
        data.put("userfk",username);
        data.put("answer",checker());
        db.update("Question",data,"question"+"=?",new String[]{questionname});
    }
    public boolean rbc(){
        if(et_answer3.isEnabled()) {
            if (et_answer3.getText().toString().equals("")) {
                return false;
            } else {
                return true;
            }
        }else{
            return true;
        }
    }
    public boolean rbd(){
        if(et_answer4.isEnabled()) {
            if (et_answer4.getText().toString().equals("")) {
                return false;
            } else {
                return true;
            }
        }else{
            return true;
        }
    }
}
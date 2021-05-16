package tr.yildiz.OmerBurakDagli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yildiz.edu.OmerBurakDagli.R;

public class AddQuestion extends AppCompatActivity {
    EditText et_question, et_answer1, et_answer2, et_answer3, et_answer4;
    Button bt_submit;
    RadioButton cb_a,cb_b,cb_c,cb_d,rb_2,rb_3,rb_4;
    String username;
    SharedPreferences sp;
    SharedPreferences.Editor speditor;
    private database v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        callInputs();
        getSharedInformation();
        username = sp.getString("username","not defined.");
        v1 = new database(this);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFill()){
                    AddQuestion();
                    Toast.makeText(getApplicationContext(),"Successfully added.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"You must fill the blank areas.", Toast.LENGTH_SHORT).show();
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
        bt_submit = findViewById(R.id.bt_submit);
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
    public void AddQuestion(){
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
        db.insertOrThrow("Question",null,data);
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
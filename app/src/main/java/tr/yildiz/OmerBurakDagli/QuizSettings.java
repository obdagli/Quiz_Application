package tr.yildiz.OmerBurakDagli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.yildiz.edu.OmerBurakDagli.R;

public class QuizSettings extends AppCompatActivity {
    EditText et_qtime, et_qpoint;
    RadioButton rb_2,rb_3,rb_4,rb_5;
    Button bt_apply;
    String diff,username;
    SharedPreferences sp;
    SharedPreferences.Editor speditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_settings);

        callInputs();
        getSharedInformation();
        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFill()){
                    diff = checker();
                    speditor.putString("time",et_qtime.getText().toString());
                    speditor.putString("point",et_qpoint.getText().toString());
                    speditor.putString("difficulty",diff);

                    speditor.commit();
                    Intent intent = new Intent(QuizSettings.this, welcome.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    public void callInputs(){
        et_qtime = findViewById(R.id.et_qtime);
        et_qpoint = findViewById(R.id.et_qpoint);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        rb_4 = findViewById(R.id.rb_4);
        bt_apply = findViewById(R.id.bt_apply);
    }
    public boolean isFill(){
        if(et_qtime.getText().toString().equals("")||et_qpoint.getText().toString().equals("")||(!rb_2.isChecked() && !rb_3.isChecked() && !rb_4.isChecked() && !rb_5.isChecked())){
            return false;
        }
        return true;
    }
    public String checker(){
        if(rb_2.isChecked()){
            return "2";
        }else if(rb_3.isChecked()){
            return "3";
        }else if(rb_4.isChecked()){
            return "4";
        }else{
            return "5";
        }
    }
    public void getSharedInformation(){
        sp = getSharedPreferences("LogInInformation",MODE_PRIVATE);
        speditor = sp.edit();
    }
}
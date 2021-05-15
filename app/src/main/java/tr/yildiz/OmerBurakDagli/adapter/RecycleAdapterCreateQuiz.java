package tr.yildiz.OmerBurakDagli.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView; import androidx.annotation.NonNull; import androidx.recyclerview.widget.RecyclerView;

import tr.yildiz.OmerBurakDagli.Question;
import com.yildiz.edu.OmerBurakDagli.R;

import java.util.ArrayList;

import static tr.yildiz.OmerBurakDagli.NewQuiz.questions2;
import static tr.yildiz.OmerBurakDagli.NewQuiz.rb_2;
import static tr.yildiz.OmerBurakDagli.NewQuiz.rb_3;
import static tr.yildiz.OmerBurakDagli.NewQuiz.rb_4;

public class RecycleAdapterCreateQuiz extends RecyclerView.Adapter<RecycleAdapterCreateQuiz.MyViewHolder> {

    private ArrayList<Question> questions;
    Context context;


    public RecycleAdapterCreateQuiz(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiplequestion,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.cb_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    questions2.add(questions.get(position));
                }else{
                    questions2.remove(questions.get(position));
                }
            }
        });
        if(rb_2.isChecked() && questions.get(position).getChoices().size() == 2){
            holder.question.setText("Soru : " + questions.get(position).getQuestion());
            holder.text_answera.setText("A) " + questions.get(position).getChoices().get(0));
            holder.text_answerb.setText("B) " + questions.get(position).getChoices().get(1));
            holder.tv_correctanswer.setText("Correct Answer is '" + questions.get(position).getAnswer() + "'");
        }else if(rb_3.isChecked() && questions.get(position).getChoices().size() == 3){
            holder.question.setText("Soru : " + questions.get(position).getQuestion());
            holder.text_answera.setText("A) " + questions.get(position).getChoices().get(0));
            holder.text_answerb.setText("B) " + questions.get(position).getChoices().get(1));
            holder.text_answerc.setText("C) " + questions.get(position).getChoices().get(2));
            holder.tv_correctanswer.setText("Correct Answer is '" + questions.get(position).getAnswer() + "'");
        }else if(rb_4.isChecked() && questions.get(position).getChoices().size() == 4){
            holder.question.setText("Soru : " + questions.get(position).getQuestion());
            holder.text_answera.setText("A) " + questions.get(position).getChoices().get(0));
            holder.text_answerb.setText("B) " + questions.get(position).getChoices().get(1));
            holder.text_answerc.setText("C) " + questions.get(position).getChoices().get(2));
            holder.text_answerd.setText("D) " + questions.get(position).getChoices().get(3));
            holder.tv_correctanswer.setText("Correct Answer is '" + questions.get(position).getAnswer() + "'");
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView question,text_answera,text_answerb,text_answerc,text_answerd,tv_correctanswer;
        CheckBox cb_save;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            question = itemView.findViewById(R.id.text_question);
            text_answera = itemView.findViewById(R.id.text_answera);
            text_answerb = itemView.findViewById(R.id.text_answerb);
            text_answerc = itemView.findViewById(R.id.text_answerc);
            text_answerd = itemView.findViewById(R.id.text_answerd);
            tv_correctanswer = itemView.findViewById(R.id.tv_correctanswer);
            cb_save = itemView.findViewById(R.id.cb_save);
        }
    }
}

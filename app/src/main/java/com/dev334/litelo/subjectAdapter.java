package com.dev334.litelo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class subjectAdapter extends RecyclerView.Adapter<subjectAdapter.mViewholder> {

    private List<subjectModel> subjectModels;

    private onNoteListener mOnNoteListener;
    public subjectAdapter(List<subjectModel> subjectModels,onNoteListener onNoteListener){
        this.subjectModels=subjectModels;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public mViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new subjectAdapter.mViewholder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewholder holder, int position) {
        holder.setAbsentTxt(subjectModels.get(position).getAbsent());
        holder.setPresentTxt(subjectModels.get(position).getPresent());
        holder.setProgressBar(subjectModels.get(position).getPresent(),subjectModels.get(position).getAbsent());
        holder.setSubjectName(subjectModels.get(position).getDocumentId());
    }

    @Override
    public int getItemCount() {
        return subjectModels.size();
    }
    public interface onNoteListener{
        void onNoteClick(int position);
    }

    public class mViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View view;
        TextView PresentTxt, AbsentTxt,perTxt,Subject;
        CircularSeekBar Percentage;
        onNoteListener onNotelistener;
        public mViewholder(@NonNull View itemView, onNoteListener onNotelistener) {
            super(itemView);
            view=itemView;
            itemView.setOnClickListener(this);
            this.onNotelistener=onNotelistener;
        }

        public void setSubjectName(String subject){
            Subject=view.findViewById(R.id.subject_name);
            Subject.setText(subject);
        }

        public void setPresentTxt(Integer present){
            PresentTxt= view.findViewById(R.id.presentNum);
            PresentTxt.setText(present.toString());
        }
        public void setAbsentTxt(Integer absent){
            PresentTxt= view.findViewById(R.id.absentNum);
            PresentTxt.setText(absent.toString());
        }
        public void setProgressBar(Integer present, Integer absent){
            Percentage=view.findViewById(R.id.Attseekbar2);
            perTxt=view.findViewById(R.id.attText2);
            Percentage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            if(present+absent==0){
                Percentage.setMax(100);
                Percentage.setProgress(0);
                perTxt.setText("0%");

            }else{
                Integer percentage=present*100/(present+absent);
                Percentage.setMax(100);
                Percentage.setProgress(percentage);
                String per=percentage.toString()+"%";
                perTxt.setText(per);
                if (percentage >= 75)   //colour green if Att is more than 75
                    Percentage.setCircleProgressColor(Color.parseColor("#1DB824"));

                else {             //colour red if Att is more than 75
                    Percentage.setCircleProgressColor(Color.parseColor("#FF7597"));
                }
            }
        }

        @Override
        public void onClick(View view) {
                 onNotelistener.onNoteClick(getAdapterPosition());
        }
    }

}

/*if(TotalClass==0){
            holder.seekBar.setMax(100);
            holder.seekBar.setProgress(0);
            holder.attText.setText("0%");
        }
        else {
            Double percentage = Double.valueOf(model.getPresent() * 100 / (model.getPresent() + model.getAbsent()));

            Integer Perc = percentage.intValue();
            holder.attText.setText(Perc.toString() + "%");
            holder.seekBar.setMax(TotalClass);
            holder.seekBar.setProgress(Present);
            if (Perc >= 75)   //colour green if Att is more than 75
                holder.seekBar.setCircleProgressColor(Color.parseColor("#1DB824"));

            else              //colour red if Att is more than 75
                holder.seekBar.setCircleProgressColor(Color.parseColor("#FF7597"));
        }*/

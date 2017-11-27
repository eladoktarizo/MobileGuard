package arisryan.mobileeg.activity;

/**
 * Created by Aris Riyanto on 5/18/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import arisryan.mobileeg.R;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MyViewHolder> {

    private List<Mail> mailList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView from, subject, content;

        public MyViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            subject = (TextView) view.findViewById(R.id.subject);
            content = (TextView) view.findViewById(R.id.content);
        }
    }


    public MailAdapter(List<Mail> mailList) {
        this.mailList = mailList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mail_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mail mail = mailList.get(position);
        holder.from.setText(mail.getFrom());
        holder.subject.setText(mail.getSubject());
        holder.content.setText(mail.getContent());
    }

    @Override
    public int getItemCount() {
        return mailList.size();
    }
}

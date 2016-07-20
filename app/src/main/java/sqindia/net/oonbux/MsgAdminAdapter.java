package sqindia.net.oonbux;

/**
 * Created by Ramya on 20-06-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.Pair;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MsgAdminAdapter extends BaseAdapter {
    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;
    private List<Pair<ChatMessage, Integer>> messages;

    private List<Pair<ChatMessage, Integer>> message_id;
    private LayoutInflater layoutInflater;
    Activity activity;

    private ArrayList<String> msg_id = new ArrayList<>();
    //private Context context;

    DbC dbclass;
    Context context ;
    String photo_path;



    public MsgAdminAdapter(Activity activity) {
        this.activity = activity;

        layoutInflater = activity.getLayoutInflater();
        messages = new ArrayList<Pair<ChatMessage, Integer>>();
        message_id = new ArrayList<Pair<ChatMessage, Integer>>();
    }
    public void addMessage(ChatMessage message, int direction,String msg_id,String photo) {
        messages.add(new Pair(message, direction));
        message_id.add(new Pair(msg_id,direction));

        this.photo_path = photo;
        this.msg_id.add(msg_id);

        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return messages.size();
    }
    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int i) {
        return messages.get(i).second;
    }
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        int direction = getItemViewType(i);
        //show message on left or right, depending on if
        //it's incoming or outgoing

        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/prox.otf");

        context = activity;

        dbclass = new DbC(context);

        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.item_chat_right_admin;



            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.item_chat_left_admin;
            }
            convertView = layoutInflater.inflate(res, viewGroup, false);
        }
        final ChatMessage message = messages.get(i).first;
///        final ChatMessage messageids = message_id.get(i).first;
        TextView txtMessage = (TextView) convertView.findViewById(R.id.txt_msg);
        txtMessage.setTypeface(tf);
        txtMessage.setText(message.getContent());

        ImageView user_image = (ImageView) convertView.findViewById(R.id.image_profile);

        Log.e("tag",""+photo_path);

            Picasso.with(context)
                    .load(photo_path)
                    .fit()
                    .into(user_image);





     /*   convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ChatMessage msss = new ChatMessage();


                //String s  = messageids.getMessage_id();
                Log.e("tag",""+"\t"+msg_id.get(i)+i+ "\t"+ message_id.get(i).first+"\t"+ message.getContent());

                dbclass.chat_delete(msg_id.get(i));
                notifyDataSetChanged();

            }
        });*/


        return convertView;
    }
}

package sqindia.net.oonbux;

/**
 * Created by Ramya on 20-06-2016.
 */

import android.app.Activity;
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
    private LayoutInflater layoutInflater;
    Activity activity;
    //private Context context;


    public MsgAdminAdapter(Activity activity) {
        this.activity = activity;

        layoutInflater = activity.getLayoutInflater();
        messages = new ArrayList<Pair<ChatMessage, Integer>>();
    }
    public void addMessage(ChatMessage message, int direction) {
        messages.add(new Pair(message, direction));
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        int direction = getItemViewType(i);
        //show message on left or right, depending on if
        //it's incoming or outgoing
        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.item_chat_right_admin;
            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.item_chat_left_admin;
            }
            convertView = layoutInflater.inflate(res, viewGroup, false);
        }
        ChatMessage message = messages.get(i).first;
        TextView txtMessage = (TextView) convertView.findViewById(R.id.txt_msg);
        txtMessage.setText(message.getContent());
       /* TextView userName = (TextView)convertView.findViewById(R.id.name);
        userName.setText(message.getName());*/
        /*ImageView profilepicture=(ImageView) convertView.findViewById(R.id.profileEdit);
       String profileic = message.getUserImage();
        try {

            Log.e("tag","eee"+profileic);

            if(profileic.equals("null"))
        {
            Picasso.with(activity).load(R.drawable.photo_ico).into(profilepicture);
        }
        else
        {
            profileic = profileic.replaceAll("\\/", "/");
            Picasso.with(activity).load(profileic).into(profilepicture);
        }

        }
        catch (NullPointerException e)
        {
          Log.e("tag","eee"+e.toString());
        }*/

        return convertView;
    }
}

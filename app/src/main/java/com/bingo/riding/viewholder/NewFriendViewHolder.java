package com.bingo.riding.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.SaveCallback;
import com.bingo.riding.R;
import com.bingo.riding.bean.AddFriendsRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bingo on 16/1/7.
 */
public class NewFriendViewHolder extends RecyclerView.ViewHolder {
    TextView nameView;
    ImageView avatarView;
    Button addBtn;
    TextView agreedView;
    private AddFriendsRequest addFriendsRequest;
    private Context mContext;

    public NewFriendViewHolder(View itemView) {
        super(itemView);

        mContext = itemView.getContext();
        nameView = (TextView)itemView.findViewById(R.id.name);
        avatarView = (ImageView)itemView.findViewById(R.id.avatar);
        addBtn = (Button)itemView.findViewById(R.id.add);
        agreedView = (TextView) itemView.findViewById(R.id.agreedView);
    }

    public void bindData(final AddFriendsRequest addFriendsRequest){
        this.addFriendsRequest = addFriendsRequest;

        nameView.setText(addFriendsRequest.getFromUser().getString("nikeName"));
        AVFile userPhoto = addFriendsRequest.getFromUser().getAVFile("userPhoto");
        if (userPhoto != null){
            Glide.with(mContext.getApplicationContext())
                    .load(userPhoto.getUrl())
                    .signature(new StringSignature(userPhoto.getUrl()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.loaderror)
                    .into(avatarView);
        }else{
            avatarView.setImageResource(R.drawable.default_photo);
        }

        final int status = addFriendsRequest.getStatus();
        if (status == AddFriendsRequest.STATUS_WAIT) {
            addBtn.setVisibility(View.VISIBLE);
            agreedView.setVisibility(View.GONE);
        } else {
            addBtn.setVisibility(View.GONE);
            agreedView.setVisibility(View.VISIBLE);

            if (status == AddFriendsRequest.STATUS_ACCEPT){
                agreedView.setText("已同意");
            } else if(status == AddFriendsRequest.STATUS_REFUSE){
                agreedView.setText("已拒绝");
            }
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVObject avObject = addFriendsRequest.getAvObject();
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("objectId", avObject.getObjectId());
                parameters.put("fromUser", avObject.getAVUser("fromUser").getObjectId());
                parameters.put("toUser", avObject.getAVUser("toUser").getObjectId());

                AVCloud.callFunctionInBackground("dealAddFriendsRequest", parameters, new FunctionCallback() {
                    @Override
                    public void done(Object o, AVException e) {
                        if (e == null){
                            Toast.makeText(mContext, "已同意", Toast.LENGTH_SHORT).show();

                            addBtn.setVisibility(View.GONE);
                            agreedView.setVisibility(View.VISIBLE);
                            agreedView.setText("已同意");

                        }else{
                            Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}

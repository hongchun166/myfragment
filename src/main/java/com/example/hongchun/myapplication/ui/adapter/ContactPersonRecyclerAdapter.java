package com.example.hongchun.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.util.ImagerUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by TianHongChun on 2016/4/11.
 */
public class ContactPersonRecyclerAdapter extends RecyclerView.Adapter<ContactPersonRecyclerAdapter.ViewHolder> {

    private  Context context;
    LayoutInflater inflater;
    private List<ContactPersonPojo> contactPersonPojoList;
    public ContactPersonRecyclerAdapter(Context context,List<ContactPersonPojo> contactPersonPojoList){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.contactPersonPojoList=contactPersonPojoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.contact_person_item_layout,null);
        ViewHolder viewHolder=new ViewHolder(view);
        x.view().inject(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactPersonPojo contactPersonPojo=contactPersonPojoList.get(position);
        if(contactPersonPojo!=null){
            holder.setContactPersonPojo(contactPersonPojo);
            holder.getTextViewPersonName().setText(contactPersonPojo.getPersonName());
            holder.getTextViewPersonPhone().setText(contactPersonPojo.getPersonPhone());
            x.image().bind(holder.getImageViewPersonHead(), "assets://avatar.jpg", ImagerUtils.getCircularImageOptions());
        }
    }

    @Override
    public int getItemCount() {
        return contactPersonPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.textview_personName)
        TextView textViewPersonName;

        @ViewInject(R.id.textview_personPhone)
        TextView textViewPersonPhone;

        @ViewInject(R.id.imageView_personHead)
        ImageView imageViewPersonHead;

        @ViewInject(R.id.itemRootView)
        View itemRootView;

        ContactPersonPojo contactPersonPojo;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Event(value = {R.id.itemRootView,R.id.imageView_personHead},type = View.OnClickListener.class)
        private void onEvenOnClick(View view){
            switch (view.getId()){
                case R.id.itemRootView:
                    Toast.makeText(context,"点击了item:"+getContactPersonPojo().getPersonName(),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageView_personHead:
                    Toast.makeText(context,"点击了头像:"+getContactPersonPojo().getPersonName(),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        @Event(value = {R.id.itemRootView},type = View.OnLongClickListener.class)
        private boolean onEvenOnLongClick(View view){
            switch (view.getId()){
                case R.id.itemRootView:
                    Toast.makeText(context,"长按了item:"+getContactPersonPojo().getPersonName(),Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }

        public TextView getTextViewPersonName() {
            return textViewPersonName;
        }

        public void setTextViewPersonName(TextView textViewPersonName) {
            this.textViewPersonName = textViewPersonName;
        }

        public TextView getTextViewPersonPhone() {
            return textViewPersonPhone;
        }

        public void setTextViewPersonPhone(TextView textViewPersonPhone) {
            this.textViewPersonPhone = textViewPersonPhone;
        }

        public ImageView getImageViewPersonHead() {
            return imageViewPersonHead;
        }

        public void setImageViewPersonHead(ImageView imageViewPersonHead) {
            this.imageViewPersonHead = imageViewPersonHead;
        }

        public View getItemRootView() {
            return itemRootView;
        }

        public void setItemRootView(View itemRootView) {
            this.itemRootView = itemRootView;
        }
        public ContactPersonPojo getContactPersonPojo() {
            return contactPersonPojo;
        }

        public void setContactPersonPojo(ContactPersonPojo contactPersonPojo) {
            this.contactPersonPojo = contactPersonPojo;
        }

    }

}

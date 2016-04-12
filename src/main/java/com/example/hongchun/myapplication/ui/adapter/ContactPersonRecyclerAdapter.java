package com.example.hongchun.myapplication.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.interfacem.PinnedHeaderAdapter;
import com.example.hongchun.myapplication.ui.dialog.ContactListDialog;
import com.example.hongchun.myapplication.util.ImagerUtils;
import com.example.hongchun.myapplication.util.MStringUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by TianHongChun on 2016/4/11.
 */
public class ContactPersonRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {

    private  Context context;
    LayoutInflater inflater;
    private List<ContactPersonPojo> contactPersonPojoList;


    public ContactPersonRecyclerAdapter(Context context,List<ContactPersonPojo> contactPersonPojoList){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.contactPersonPojoList=contactPersonPojoList;
    }

    public void addData(int position,ContactPersonPojo personPojo) {
        contactPersonPojoList.add(position, personPojo);
        notifyItemInserted(position);
    }
    public void addDataAll(int position,List<ContactPersonPojo> contactPersonPojos) {
        contactPersonPojoList.addAll(contactPersonPojos);
        notifyItemInserted(position);
    }
    public void removeData(int position) {
        contactPersonPojoList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return contactPersonPojoList.size();
    }
    @Override
    public int getItemViewType(int position) {
           return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.contact_person_item_layout,null);
        RecyclerView.ViewHolder holder=new ItemViewHolder(view);
        x.view().inject(holder,view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         if(holder instanceof ItemViewHolder){
            ContactPersonPojo contactPersonPojo=contactPersonPojoList.get(position);
            ItemViewHolder itemViewHolder=(ItemViewHolder)holder;
            if(contactPersonPojo!=null){
                itemViewHolder.setContactPersonPojo(contactPersonPojo);
                itemViewHolder.getTextViewPersonName().setText(contactPersonPojo.getPersonName());
                itemViewHolder.getTextViewPersonPhone().setText(contactPersonPojo.getPersonPhone());
                x.image().bind(itemViewHolder.getImageViewPersonHead(), "assets://avatar.jpg", ImagerUtils.getCircularImageOptions());

                //根据position获取分类的首字母的char ascii值
                String firstLetter=MStringUtils.converterToFirstSpell(contactPersonPojo.getPersonName()).substring(0,1);
                // 正则表达式，判断首字母是否是英文字母
                if(firstLetter.matches("[A-Z]")){
                }else{
                    firstLetter="#";
                }
                int section =firstLetter.toUpperCase().charAt(0);
                //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
                if(position == getPositionForSection(section)){
                    itemViewHolder.getTextViewGroup().setVisibility(View.VISIBLE);
                    itemViewHolder.getTextViewGroup().setText(firstLetter);
                }else{
                    itemViewHolder.getTextViewGroup().setVisibility(View.GONE);
                }
            }
        }
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i <getItemCount(); i++) {
            String sortStr = MStringUtils.converterToFirstSpell((contactPersonPojoList.get(i).getPersonName())).substring(0,1);
            if(sortStr.matches("[A-Z]")){
            }else{
                sortStr="#";
            }
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.textview_personName)
        TextView textViewPersonName;

        @ViewInject(R.id.textview_personPhone)
        TextView textViewPersonPhone;

        @ViewInject(R.id.imageView_personHead)
        ImageView imageViewPersonHead;

        @ViewInject(R.id.textView_group)
        TextView textViewGroup;

        @ViewInject(R.id.itemRootView)
        View itemRootView;

        ContactPersonPojo contactPersonPojo;

        public ItemViewHolder(View itemView) {
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
                    ContactListDialog.getInstance()
                            .setContactPersonPojo(getContactPersonPojo())
                            .show(((AppCompatActivity) context).getSupportFragmentManager());

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

        public TextView getTextViewGroup() {
            return textViewGroup;
        }

        public void setTextViewGroup(TextView textViewGroup) {
            this.textViewGroup = textViewGroup;
        }

        public ContactPersonPojo getContactPersonPojo() {
            return contactPersonPojo;
        }

        public void setContactPersonPojo(ContactPersonPojo contactPersonPojo) {
            this.contactPersonPojo = contactPersonPojo;
        }

    }

}

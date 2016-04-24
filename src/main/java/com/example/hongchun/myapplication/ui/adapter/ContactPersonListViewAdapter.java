package com.example.hongchun.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.interfacem.PinnedHeaderAdapter;
import com.example.hongchun.myapplication.ui.dialog.ContactListDialog;
import com.example.hongchun.myapplication.util.ImagerUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by TianHongChun on 2016/4/11.
 */
public class ContactPersonListViewAdapter extends BaseAdapter implements PinnedHeaderAdapter {

    private  Context context;
    LayoutInflater inflater;
    private List<ContactPersonPojo> contactPersonPojoList;

    public ContactPersonListViewAdapter(Context context, List<ContactPersonPojo> contactPersonPojoList){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.contactPersonPojoList=contactPersonPojoList;
    }

    @Override
    public int getCount() {
        return contactPersonPojoList.size();
    }

    @Override
    public Object getItem(int position) {
        return (position >= 0 && position < contactPersonPojoList.size()) ? contactPersonPojoList.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactPersonPojo contactPersonPojo=contactPersonPojoList.get(position);
        ItemViewHolder itemViewHolder=null;

        if(convertView==null){
            convertView=inflater.inflate(R.layout.contact_person_item_layout,null);
            itemViewHolder=new ItemViewHolder(convertView);
            x.view().inject(itemViewHolder,convertView);
            convertView.setTag(itemViewHolder);
        }else {
            itemViewHolder=(ItemViewHolder)convertView.getTag();
        }
        if(contactPersonPojo!=null){
            itemViewHolder.setContactPersonPojo(contactPersonPojo);
            itemViewHolder.getTextViewPersonName().setText(contactPersonPojo.getName());
            itemViewHolder.getTextViewPersonPhone().setText(contactPersonPojo.getPhone());
            x.image().bind(itemViewHolder.getImageViewPersonHead(), "assets://avatar.jpg", ImagerUtils.getCircularImageOptions());
            //根据position获取分类的首字母的char ascii值
            String firstLetter=contactPersonPojo.getSortLetter();
            int section =firstLetter.charAt(0);
            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if(position == getPositionForSection(section)){
                itemViewHolder.getTextViewGroup().setVisibility(View.VISIBLE);
                itemViewHolder.getTextViewGroup().setText(contactPersonPojo.getSortLetter());
            }else{
                itemViewHolder.getTextViewGroup().setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i <getCount(); i++) {
            String sortStr =contactPersonPojoList.get(i).getSortLetter();
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

    @Override
    public int getPinnedHeaderState(int position) {
        if (position < 0) {
            return PINNED_HEADER_GONE;
        }
        ContactPersonPojo item = (ContactPersonPojo) getItem(position);
        Object object=getItem(position + 1);
        ContactPersonPojo itemNext=null;
        if(object!=0){
            itemNext=(ContactPersonPojo)object;
        }
        boolean isNextSection =false;
        boolean isSection=false;
        if(null!=itemNext){
            if(position+1== getPositionForSection(itemNext.getSortLetter().charAt(0))){
                isNextSection=true;
            }
        }
        if(position == getPositionForSection(item.getSortLetter().charAt(0))){
            isSection=true;
        }
        if (!isSection && isNextSection) {
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View headerView, int firstPosition,int alpha) {
        TextView textView=(TextView)headerView.findViewById(R.id.textView_group);
        ContactPersonPojo contactPersonPojo=contactPersonPojoList.get(firstPosition);
       String firstLetter= contactPersonPojo.getSortLetter();
        // 正则表达式，判断首字母是否是英文字母
        if(firstLetter.matches("[A-Z]")){
        }else{
            firstLetter="#";
        }
        textView.setText(firstLetter);
    }

    @Override
    public View getPinnedHeaderView(Context context) {
        View view=LayoutInflater.from(context).inflate(R.layout.contact_person_group_layout,null);
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    public class ItemViewHolder {
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
        }

        @Event(value = {R.id.itemRootView,R.id.imageView_personHead},type = View.OnClickListener.class)
        private void onEvenOnClick(View view){
            switch (view.getId()){
                case R.id.itemRootView:
                    Toast.makeText(context,"点击了item:"+getContactPersonPojo().getName(),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageView_personHead:
                    Toast.makeText(context,"点击了头像:"+getContactPersonPojo().getName(),Toast.LENGTH_SHORT).show();
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

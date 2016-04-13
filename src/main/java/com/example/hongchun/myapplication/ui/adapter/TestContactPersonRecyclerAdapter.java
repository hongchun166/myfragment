package com.example.hongchun.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.data.pojo.ContactPersonPojo;
import com.example.hongchun.myapplication.interfacem.PinnedHeaderAdapter;
import com.example.hongchun.myapplication.ui.dialog.ContactListDialog;
import com.example.hongchun.myapplication.util.ImagerUtils;
import com.example.hongchun.myapplication.util.MStringUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by TianHongChun on 2016/4/11.
 */
public class TestContactPersonRecyclerAdapter extends BaseAdapter implements PinnedHeaderAdapter {

    private  Context context;
    LayoutInflater inflater;
    private List<ContactPersonPojo> contactPersonPojoList;


    public TestContactPersonRecyclerAdapter(Context context, List<ContactPersonPojo> contactPersonPojoList){
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
        return contactPersonPojoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder itemViewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.contact_person_item_layout,null);
            itemViewHolder=new ItemViewHolder(convertView);
            x.view().inject(itemViewHolder,convertView);
            convertView.setTag(convertView);
        }else {
            itemViewHolder=(ItemViewHolder)convertView.getTag();
        }
        ContactPersonPojo contactPersonPojo=contactPersonPojoList.get(position);
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

        return convertView;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i <getCount(); i++) {
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

    @Override
    public int getPinnedHeaderState(int position) {
        ContactPersonPojo contactPersonPojo=contactPersonPojoList.get(position);
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
            // 是组item,headview可以移除了
            return PinnedHeaderAdapter.PINNED_HEADER_GONE;
        }else{
            // 不是组,headview显示
            return PinnedHeaderAdapter.PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configurePinnedHeader(View headerView, int firstPosition) {
        TextView textView=(TextView)headerView;
        ContactPersonPojo contactPersonPojo=contactPersonPojoList.get(firstPosition);
        String firstLetter=MStringUtils.converterToFirstSpell(contactPersonPojo.getPersonName()).substring(0,1);
        // 正则表达式，判断首字母是否是英文字母
        if(firstLetter.matches("[A-Z]")){
        }else{
            firstLetter="#";
        }
        textView.setText(firstLetter);
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

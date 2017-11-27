package arisryan.mobileeg.activity;

/**
 * Created by Elad Oktarizo on 24/08/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import arisryan.mobileeg.R;

public class AddressBookAdapter extends RecyclerView.Adapter {

    ArrayList<AddressBookModel> addressbookArrayList;
    private Context mContext;

    public AddressBookAdapter(ArrayList<AddressBookModel> addressbookArrayList, Context mContext) {
        this.addressbookArrayList = addressbookArrayList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressbookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_addressbook, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddressBookModel addressbook = addressbookArrayList.get(position);
        if (holder instanceof AddressbookViewHolder){
            final AddressbookViewHolder myHolder = (AddressbookViewHolder) holder;
            Glide.with(mContext)
                    .load("")
                    .placeholder(R.drawable.eg_logo)
                    .into(myHolder.imageView);
            myHolder.tvName.setText(addressbook.getName());
            myHolder.tvEmail.setText(addressbook.getEmail());
            myHolder.overflowMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(mContext, myHolder.overflowMenu);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.menu_popup_addressbook, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(mContext, item.getTitle() + " menu item",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }
            });
            myHolder.itemAddressBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, DetailContact.class);
                    mContext.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return addressbookArrayList.size();
    }
    private static class AddressbookViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvName, tvEmail;
        ImageButton overflowMenu;
        CardView itemAddressBook;

        AddressbookViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.iv_contact);
            tvName = (TextView) itemView.findViewById(R.id.tv_contact_name);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_contact_email);
            overflowMenu = (ImageButton) itemView.findViewById(R.id.overflow_addressbook);
            itemAddressBook = (CardView) itemView.findViewById(R.id.card_addressbook);
        }
    }

}

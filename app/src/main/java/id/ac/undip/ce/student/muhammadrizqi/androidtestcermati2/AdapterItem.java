package id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Model.ItemModel;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class AdapterItem extends RecyclerView.Adapter<AdapterItem.MyViewHolder> {


    // private List<MessageModel> inboxList;

    private Context mContext;
    private List<ItemModel> messageModels;
    private MessageAdapterListener listener;
    private SparseBooleanArray selectedItems;


    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }





    //membuat view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profilepict;
        TextView nameuser;


        public MyViewHolder(View view) {
            super(view);
            profilepict = view.findViewById(R.id.profilepict);
            nameuser = view.findViewById(R.id.username);

        }


    }

//    public InboxAdapter(LihatTrack lihatTrack, List<MessageModel> messageModels, LihatTrack lihatTrack1) {
//    }

//    public AdapterItem(MainActivity mContext, List<ItemModel> itemModels, MainActivity mainActivity) {
//    }

    public AdapterItem(Context mContext, List<ItemModel> messageModels, MessageAdapterListener listener) {
        this.mContext = mContext;
        this.messageModels = messageModels;
        this.listener = listener;

        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();


    }


    View itemView;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        final MyViewHolder vHolder = new MyViewHolder(itemView);

        return vHolder;
    }

    String a;

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ItemModel data = messageModels.get(position);


        holder.nameuser.setText(data.getLogin());
//                  holder.profilepict.set(data.getAvatarUrl());
        applyprofilepicture(holder,data);
//        holder.itemView.setActivated(selectedItems.get(position,false));


        // displaying the first letter of From in icon text
        // holder.iconText.setText(data.getIconText());

        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));
        // apply click events
//                applyClickEvents(holder, position);


    }

    private void applyprofilepicture(MyViewHolder holder, ItemModel data) {
        if (data.getAvatarUrl() == null) {
        }
        Glide.with(mContext).load(data.getAvatarUrl()).into(holder.profilepict);
    }




    @Override
    public long getItemId(int position) {
        return (messageModels.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        messageModels.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface MessageAdapterListener {
        void onIconClicked(int position,String id,MyViewHolder holder);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);

        boolean onCreateOptionsMenu(Menu menu);
    }
}






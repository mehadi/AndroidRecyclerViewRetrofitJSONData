package me.mehadi.androidrecyclerviewretrofitjsondata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.mehadi.androidrecyclerviewretrofitjsondata.R;
import me.mehadi.androidrecyclerviewretrofitjsondata.model.User;


public class AdapterAllView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    boolean value;
    private List<User> mList;
    private Context ctx;

    public AdapterAllView(Context ctx, List<User> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list, parent, false);
        vh = new SingleItemView(v);


        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SingleItemView) {
            final User dm = mList.get(position);
            ((SingleItemView) holder).name.setText(dm.getName());
            ((SingleItemView) holder).email.setText(dm.getEmail());
            ((SingleItemView) holder).mViewHeader.setOnClickListener(v -> {

            });

        }
    }

    public void refreshAdapter(boolean value, List<User> tempCardItem) {
        this.value = value;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    class SingleItemView extends RecyclerView.ViewHolder {
        public TextView name, email;
        public View mViewHeader;

        public SingleItemView(View v) {
            super(v);
            mViewHeader = v;
            name = v.findViewById(R.id.tv_name);
            email = v.findViewById(R.id.tv_email);
        }
    }


}





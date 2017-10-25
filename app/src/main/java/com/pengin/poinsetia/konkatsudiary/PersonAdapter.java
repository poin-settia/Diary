package com.pengin.poinsetia.konkatsudiary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.OrderedRealmCollection;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OrderedRealmCollection<Person> objects;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnRecyclerListener mListener;

    // 今はAだけ使用
    private final static int VIEWTYPE_A = 0;
    private final static int VIEWTYPE_B = 1;


    public PersonAdapter(Context context, OrderedRealmCollection<Person> persons, OnRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        objects = persons;
        mListener = listener;
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // 表示するレイアウトを設定
        /*
        switch (viewType) {
            case VIEWTYPE_A:
                return new viewHolderAlpha(mInflater
                        .inflate(R.layout.list_item_a, viewGroup, false));
            case VIEWTYPE_B:
                return new ViewHolderBeta(mInflater
                        .inflate(R.layout.list_item_b, viewGroup, false));
            default:
                return new viewHolderAlpha(mInflater
                        .inflate(R.layout.list_item_a, viewGroup, false));
        }
        */
        return new viewHolderAlpha(mInflater
                .inflate(R.layout.list_item_a, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        // データ表示

        // アルファ
        ((viewHolderAlpha)viewHolder).onBindViewHolder(objects.get(position));

        /* 一旦コメントアウト
        if (viewHolder instanceof viewHolderAlpha) {
            // アルファのデータセット
            ((viewHolderAlpha)viewHolder).onBindViewHolder(mDataList.get(position));

        } else if (viewHolder instanceof ViewHolderBeta) {
            // ベータのデータセット
            ((ViewHolderBeta)viewHolder).onBindViewHolderBeta(mDataList.get(position));
        }
        */


        // クリック処理
        viewHolder.itemView.setOnClickListener(v -> mListener.onRecyclerClicked(v, position));

    }

    @Override
    public int getItemViewType(int position) {
        return VIEWTYPE_A;
    }

    @Override
    public int getItemCount() {
        if (objects != null) {
            return objects.size();
        } else {
            return 0;
        }
    }


    // ViewHolder1
    private class viewHolderAlpha extends RecyclerView.ViewHolder {

        TextView titleTextAlpha;
        TextView detailTextAlpha;

        viewHolderAlpha(View itemView) {
            super(itemView);
            titleTextAlpha = (TextView) itemView.findViewById(R.id.title);
            detailTextAlpha = (TextView) itemView.findViewById(R.id.detail);
        }

        void onBindViewHolder (Person data) {
            this.titleTextAlpha.setText(String.valueOf(data.getAge()));
            this.detailTextAlpha.setText(data.getName());

        }


    }

    // 今は未使用
    private class ViewHolderBeta extends RecyclerView.ViewHolder {

        TextView titleTextBeta;
        TextView detailTextBeta;
        String textData;

        ViewHolderBeta(View itemView) {
            super(itemView);
            titleTextBeta = (TextView) itemView.findViewById(R.id.title_numa);
            detailTextBeta = (TextView) itemView.findViewById(R.id.detail_numa);
            detailTextBeta.setOnClickListener(v -> Toast.makeText(mContext,textData,Toast.LENGTH_SHORT).show());
        }

        void onBindViewHolderBeta(RowData data) {
            this.titleTextBeta.setText(data.getTitle());
            this.detailTextBeta.setText(data.getDetail());
            this.textData = data.getTitle() + " は " + data.getDetail();
        }
    }

}
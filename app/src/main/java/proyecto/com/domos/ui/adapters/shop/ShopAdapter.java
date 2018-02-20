package proyecto.com.domos.ui.adapters.shop;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import proyecto.com.domos.R;
import proyecto.com.domos.data.model.ItemShop;
import proyecto.com.domos.databinding.TemplateAudioMessageBinding;
import proyecto.com.domos.databinding.TemplateTextMessageBinding;



public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<ItemShop> list;

    public interface ShopAdapterCallback
    {
        void deleteItem(ItemShop itemShop);
        void playAudio(ItemShop itemShop);
    }

    ShopAdapterCallback callback;

    public ShopAdapter(List<ItemShop>data,ShopAdapterCallback callback)
    {
        list = data;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType)
        {
            case 0://TextViewHolder
                View viewText = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_text_message,parent,false);
                return  new TextViewHolader(viewText);
            case 1://AudioViewHolder
                View viewAudio = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_audio_message,parent,false);
                return new AudioViewHolader(viewAudio);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (holder.getItemViewType())
        {
            case 0:
                ((TextViewHolader)holder).bind(list.get(position),this);
                break;
            case 1:
                ((AudioViewHolader)holder).bind(list.get(position),this);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        return list.get(position).getType();
    }

    public void addItems(List<ItemShop>itemShops) {
        this.list = itemShops;
        notifyDataSetChanged();
    }

    public void deleteItem(ItemShop itemShop)
    {
        if(callback!=null)
        {
            callback.deleteItem(itemShop);
        }
    }

    public static class AudioViewHolader extends RecyclerView.ViewHolder
    {
        View itemView;
        TemplateAudioMessageBinding audioDataBinding;

        public AudioViewHolader(View itemView) {
            super(itemView);
            audioDataBinding = DataBindingUtil.bind(itemView);
            this.itemView = itemView;
        }

        public void bind(ItemShop itemShop ,ShopAdapter handler)
        {
            audioDataBinding.setHandler(handler);
            audioDataBinding.setItemAudio(itemShop);
            TextView durTextView = itemView.findViewById(R.id.txtDuration);
            if(itemShop.getDuration()<10)
                durTextView.setText("00:0"+itemShop.getDuration()+" "+itemView.getContext().getResources().getString(R.string.abbrev_seconds_lbl));
            else
                durTextView.setText("00:"+itemShop.getDuration()+" "+itemView.getContext().getResources().getString(R.string.abbrev_seconds_lbl));
         }
    }

    public static class TextViewHolader extends RecyclerView.ViewHolder
    {
        View itemView;
        TemplateTextMessageBinding textDataBinding;

        public TextViewHolader(View itemView) {
            super(itemView);
            textDataBinding = DataBindingUtil.bind(itemView);
            this.itemView = itemView;
        }
        public void bind(ItemShop itemShop,ShopAdapter handler)
        {
            textDataBinding.setHandler(handler);
            textDataBinding.setItemText(itemShop);
        }
    }

    public void playAudio(ItemShop itemShop)
    {
        if(callback!=null)
        {
            callback.playAudio(itemShop);
        }
    }
}


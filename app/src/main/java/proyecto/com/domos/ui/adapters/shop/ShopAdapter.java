package proyecto.com.domos.ui.adapters.shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import proyecto.com.domos.R;

/**
 * Created by aranda on 16/02/18.
 */

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<String> list = new ArrayList();

    public ShopAdapter()
    {
        list.add("holaholaholaho");

        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holag");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holah");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holam");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola holaola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola holla hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola holaa hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola hola hola hola hola hola hola hola hola hola hola holap");
        list.add("holaholaholaholaholaholaholaholaholaholaholaholaholaholaholahola hola hola hola  hola hola hola hola hola hola hola hola hola holap");



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
                ((TextViewHolader)holder).bind(list.get(position));
                break;
            case 1:
                ((AudioViewHolader)holder).bind();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int mod = position % 2;
        if(mod == 0)
        {
            return 0;
        }
        return 1;
    }

    public static class AudioViewHolader extends RecyclerView.ViewHolder
    {
        View itemView;

        public AudioViewHolader(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bind()
        {
            TextView textView = itemView.findViewById(R.id.txtDuration);
            textView.setText("11:00");
        }
    }

    public static class TextViewHolader extends RecyclerView.ViewHolder
    {
        View itemView;

        public TextViewHolader(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        public void bind(String data)
        {
            TextView textView = itemView.findViewById(R.id.txtMessage);
            textView.setText(data);
        }
    }
}


package proyecto.com.domos.ui.fragments.shop;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import proyecto.com.domos.R;
import proyecto.com.domos.ui.activities.main.MainActivity;
import proyecto.com.domos.ui.adapters.shop.ShopAdapter;
import proyecto.com.domos.util.AudioRecordHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener {



    private AudioRecordHandler mAudioRecordInstance;
    private ImageView btnShowBarBottom;
    private EditText txtMessage;
    private RecyclerView rcView;
    private ShopAdapter shopAdapter;


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        //link views
        btnShowBarBottom = view.findViewById(R.id.btnShowBarBottom);
        txtMessage = view.findViewById(R.id.txtMessage);
        rcView = view.findViewById(R.id.rcView);


        //set listeners
        btnShowBarBottom.setOnClickListener(this);
        txtMessage.setOnFocusChangeListener(this);
        rcView.setOnTouchListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setStackFromEnd(true);
        rcView.setLayoutManager(linearLayoutManager);
        shopAdapter = new ShopAdapter();
        rcView.setAdapter(shopAdapter);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnShowBarBottom:
                showBarBottom();
                break;

        }
    }

    private void showBarBottom()
    {
        ((MainActivity)getActivity()).showBarBottom(true);
    }

    private void hideBarBottom(){
        ((MainActivity)getActivity()).showBarBottom(false);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b)
        {
            hideBarBottom();
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.rcView:
                hideBarBottom();
                break;
        }
        return  false;
    }
}

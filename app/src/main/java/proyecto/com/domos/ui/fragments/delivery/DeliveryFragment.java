package proyecto.com.domos.ui.fragments.delivery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import proyecto.com.domos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends Fragment {


    public DeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_delivery, container, false);
        return view;
    }

}

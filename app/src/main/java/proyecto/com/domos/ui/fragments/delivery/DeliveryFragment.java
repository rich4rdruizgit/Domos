package proyecto.com.domos.ui.fragments.delivery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.layer_net.stepindicator.StepIndicator;

import proyecto.com.domos.R;
import proyecto.com.domos.ui.adapters.delivery.DeliveryViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends Fragment {


    private ViewPager mViewPagerDelivery;
    private StepIndicator stepIndicator;
    public DeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_delivery, container, false);
        mViewPagerDelivery = view. findViewById(R.id.viewpagerDelivery);
        mViewPagerDelivery.setAdapter(new DeliveryViewPagerAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext()));

        stepIndicator = (StepIndicator) view.findViewById(R.id.step_indicator);
        stepIndicator.setupWithViewPager(mViewPagerDelivery);
        return view;
    }

}

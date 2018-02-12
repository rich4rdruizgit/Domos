package proyecto.com.domos.ui.adapters.delivery;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import proyecto.com.domos.ui.fragments.delivery.StepOneFragment;
import proyecto.com.domos.ui.fragments.delivery.StepThreeFragment;
import proyecto.com.domos.ui.fragments.delivery.StepTwoFragment;

/**
 * Created by rich4 on 9/02/2018.
 */

public class DeliveryViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = DeliveryViewPagerAdapter.class.toString();

    //Fragments que intervienen en el proceso del domicilio
    private StepOneFragment stepOneFragment;
    private StepTwoFragment stepTwoFragment;
    private StepThreeFragment stepThreeFragment;

    public DeliveryViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        notifyDataSetChanged();
    }

    Context context;

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (stepOneFragment == null)
                    stepOneFragment = new StepOneFragment();
                return stepOneFragment;

            case 1:
                if (stepTwoFragment == null)
                    stepTwoFragment = new StepTwoFragment();
                return stepTwoFragment;

            case 2:
                if (stepThreeFragment == null)
                    stepThreeFragment = new StepThreeFragment();
                return stepThreeFragment;
        }
        return null;
    }
}

package proyecto.com.domos.ui.fragments.shop;


import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import proyecto.com.domos.R;
import proyecto.com.domos.util.AudioRecordHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements View.OnClickListener {


    private static final int IDLE = 0;
    private static final int RECORDING = 1;
    private static final String TAG = ShopFragment.class.getSimpleName();


    private ImageView mBtnRecord;
    private EditText mPath;

    private int status = IDLE;

    private AudioRecordHandler mAudioRecordInstance;


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        mBtnRecord = view.findViewById(R.id.btn_record);
        mPath = view.findViewById(R.id.et_path);

        mBtnRecord.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_record:
                onRecordClick();
                break;
        }
    }


    private void onRecordClick() {
        if (status == IDLE) {
            doActionRecord();
            status = RECORDING;
            mBtnRecord.setImageResource(R.mipmap.ic_record_pause);
        } else if (status == RECORDING) {
            doActionStopRecord();
            status = IDLE;
            mBtnRecord.setImageResource(R.mipmap.ic_record_start);
        }
    }

    private void doActionRecord() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECORD_AUDIO);
        Log.i(TAG, "i =" + permissionCheck);
        //开始录音
        mAudioRecordInstance = new AudioRecordHandler("/sdcard/hello.mp3",
                new AudioRecordHandler.ProgressListener() {
                    @Override
                    public boolean reportProgress(double duration) {
                        Log.i(TAG, "duration = " + duration);
                        return true;
                    }
                });
        Thread th = new Thread(mAudioRecordInstance);
        th.start();
        mAudioRecordInstance.setRecording(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAudioRecordInstance != null) {
            mAudioRecordInstance.setRecording(false);
        }
    }

    private void doActionStopRecord() {
        if (mAudioRecordInstance != null) {
            mAudioRecordInstance.setRecording(false);
        }
    }
}

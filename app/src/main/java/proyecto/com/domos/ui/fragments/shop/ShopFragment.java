package proyecto.com.domos.ui.fragments.shop;


import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proyecto.com.domos.R;
import proyecto.com.domos.data.model.ItemShop;
import proyecto.com.domos.ui.activities.main.MainActivity;
import proyecto.com.domos.ui.adapters.shop.ShopAdapter;
import proyecto.com.domos.util.AppConstants;
import proyecto.com.domos.util.AudioRecordHandler;
import proyecto.com.domos.util.FileUtils;
import proyecto.com.domos.util.HelperUtil;
import proyecto.com.lamemp3.Encoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, ShopAdapter.ShopAdapterCallback {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static final int REQUEST_ID_STORAGE_PERMISSIONS = 2;

    private int permissionCheck_READ_EXTERNAL_STORAGE= -1;
    private int permissionCheck_WRITE_EXTERNAL_STORAGE= -1;
    private int permissionCheck_RECORD_AUDIO= -1;
    List<String> listPermissionsNeeded;

    private AudioRecordHandler mAudioRecordInstance;
    private ImageView btnShowBarBottom;
    private EditText txtMessage;
    private RecyclerView rcView;
    private LinearLayout lyRecord;
    private TextView txtDurationRecord;
    private ImageView imgRecord;
    private ShopAdapter shopAdapter;
    private FrameLayout floatingActionButton;
    private ImageButton iconFloatingActionButton;
    private boolean showingMic = true;
    private ShopViewModel viewModel;
    private AlertDialog dialog;
    private boolean isRecording = false;
    private boolean actionUp = true;
    private MediaPlayer mediaPlayer;
    private int durationSecs;
    private int durationSecsDec;
    private int durationSecsTotal;
    /**
     * Manejador contador tiempo
     */
    private Handler customHandler;
    /**
     * Archivo de audio almacenado
     */
    private File audioFile;
    /**
     * bufffer lame
     */
    private int minBuffer;
    /**
     * Grabador de audio
     */
    private AudioRecord audioRecord;

    private int inSamplerate = 8000;
    /**
     * Escritura Salida archivo
     */
    private FileOutputStream outputStream;
    /**
     * variable Lame mp3
     */
    private Encoder encoder;
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
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        iconFloatingActionButton = view.findViewById(R.id.iconFloatingActionButton);
        lyRecord = view.findViewById(R.id.lyRecord);
        imgRecord = view.findViewById(R.id.imgRecord);
        txtDurationRecord = view.findViewById(R.id.txtDurationRecord);
        lyRecord.setVisibility(View.GONE);
        //set listeners
        btnShowBarBottom.setOnClickListener(this);
        txtMessage.setOnTouchListener(this);
        rcView.setOnTouchListener(this);
        iconFloatingActionButton.setOnClickListener(this);
        iconFloatingActionButton.setOnTouchListener(this);

        txtMessage.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0 && showingMic)
                {
                    iconFloatingActionButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_send));
                    showingMic = false;
                }
                else if(s.length()==0 && !showingMic)
                {
                    iconFloatingActionButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_mic));
                    showingMic = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setStackFromEnd(true);
        rcView.setLayoutManager(linearLayoutManager);
        shopAdapter = new ShopAdapter(new ArrayList<ItemShop>(),this);
        rcView.setAdapter(shopAdapter);

        viewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        viewModel.getAllItemShop().observe(this, new Observer<List<ItemShop>>() {
            @Override
            public void onChanged(List<ItemShop> itemShops) {
                shopAdapter.addItems(itemShops);
                rcView.scrollToPosition(shopAdapter.getItemCount() -1);
            }
        });
        customHandler = new Handler();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(dialog!=null && dialog.isShowing())
        {
            dialog.dismiss();
            resetRecordView();
            resetPlayAudio();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnShowBarBottom:
                showBarBottom();
                break;

            case R.id.iconFloatingActionButton:
                saveMessage();
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.rcView:
                hideBarBottom();
                break;
            case R.id.txtMessage:
                hideBarBottom();
                break;
            case R.id.iconFloatingActionButton:
                if(showingMic)
                {
                    onTouchFloatingActionButtonMic(motionEvent);
                    return true;
                }
                break;
        }
        return  false;
    }

    private void saveMessage()
    {
        if(!showingMic)
        {
            ItemShop itemShop = new ItemShop("",txtMessage.getText().toString(),0,0);
            viewModel.insertItem(itemShop);
            txtMessage.setText("");
        }
    }

    @Override
    public void deleteItem(final ItemShop itemShop) {
        dialog = HelperUtil.createAlertDialog(getActivity(),
                R.string.tittle_delete_itemShop_dialog,
                R.string.message_delete_itemShop_dialog,
                R.string.btn_positive_dialog_delete,
                R.string.btn_negative_dialog,
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(itemShop.getType()==1)
                        {
                            if(checkStoragePermissions())
                            {
                                File audio = new File(itemShop.getSrcAudio());
                                if(audio.exists())
                                {
                                    audio.delete();
                                }
                                viewModel.deleteItem(itemShop);
                            }
                        }
                        else
                        {
                            viewModel.deleteItem(itemShop);
                        }
                    }
                },new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
        dialog.show();

    }

    @Override
    public void playAudio(ItemShop itemShop) {

        if(checkStoragePermissions())
        {
            dialog = HelperUtil.createAlertDialogWithViewwithoutButtons(getActivity(),
                    R.layout.template_audio_play,R.string.tittle_audio_dialog);
            dialog.show();
            ImageView btnPlayAudio = dialog.findViewById(R.id.btnPlayAudio);
            btnPlayAudio.setVisibility(View.INVISIBLE);
            ImageView btnPauseAudio = dialog.findViewById(R.id.btnPauseAudio);
            btnPauseAudio.setVisibility(View.VISIBLE);
            btnPlayAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playAudioDialog();
                }
            });

            btnPauseAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pauseAudioDialog();
                }
            });
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(itemShop.getSrcAudio());
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.seekTo(0);
                        mediaPlayer.pause();
                        SeekBar seekBar = dialog.findViewById(R.id.seekBar);
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        TextView txtDuration = dialog.findViewById(R.id.txtDurationAudio);
                        txtDuration.setText("00 "+getActivity().getResources().getString(R.string.abbrev_seconds_lbl));
                        ImageView btnPlayAudio = dialog.findViewById(R.id.btnPlayAudio);
                        btnPlayAudio.setVisibility(View.VISIBLE);
                        ImageView btnPauseAudio = dialog.findViewById(R.id.btnPauseAudio);
                        btnPauseAudio.setVisibility(View.GONE);
                        durationSecs = 0;
                        customHandler.removeCallbacks(updateTimerPlayThread);
                    }
                });
                durationSecs = 0;
                durationSecsDec =  mediaPlayer.getDuration();
                durationSecsTotal =  mediaPlayer.getDuration();
                SeekBar seekBar = dialog.findViewById(R.id.seekBar);
                seekBar.setMax(durationSecsTotal);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar)
                    {
                        int seekBarProgress = seekBar.getProgress();
                        int seekBarProgressMod = (seekBarProgress % 1000);
                        if(seekBarProgress>=1000)
                        {
                            if(seekBarProgressMod <500)
                            {
                                seekBarProgress = (seekBarProgress -seekBarProgressMod);
                            }
                            else
                            {
                                seekBarProgress = (seekBarProgress -seekBarProgressMod) + 1000;
                            }
                        }
                        else
                        {
                            seekBarProgress = 0;
                        }
                        durationSecs = seekBarProgress;
                        mediaPlayer.seekTo(durationSecs);
                    }
                });
                mediaPlayer.start();
                customHandler.postDelayed(updateTimerPlayThread, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void playAudioDialog()
    {
        ImageView btnPlayAudio = dialog.findViewById(R.id.btnPlayAudio);
        btnPlayAudio.setVisibility(View.INVISIBLE);
        ImageView btnPauseAudio = dialog.findViewById(R.id.btnPauseAudio);
        btnPauseAudio.setVisibility(View.VISIBLE);
        customHandler.postDelayed(updateTimerPlayThread, 0);
        mediaPlayer.seekTo(durationSecs);
        mediaPlayer.start();
    }

    private void pauseAudioDialog()
    {
        ImageView btnPlayAudio = dialog.findViewById(R.id.btnPlayAudio);
        btnPlayAudio.setVisibility(View.VISIBLE);
        ImageView btnPauseAudio = dialog.findViewById(R.id.btnPauseAudio);
        btnPauseAudio.setVisibility(View.GONE);
        mediaPlayer.pause();
        customHandler.removeCallbacks(updateTimerPlayThread);
    }

    private void onTouchFloatingActionButtonMic(MotionEvent motionEvent)
    {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startRecord();
                break;
            case MotionEvent.ACTION_UP:
                stopRecord();
                break;
        }
    }
    /**
     * Maneja el grabado de audios si
     * el atributo isRecording es false inicia la grabacion del audio
     * sino no es false fija isRecording igual a false para que la ejecucion
     * del metodo record finalice
     */

    private void startRecord()
    {
        //startTime = SystemClock.uptimeMillis();
        if(checkMultipPermissions())
        {
            durationSecs = -1;
            lyRecord.setVisibility(View.VISIBLE);
            txtDurationRecord.setText("");
            isRecording = false;
            actionUp = false;
            customHandler.postDelayed(updateTimerThread, 1000);
            new Thread() {
                @Override
                public void run() {
                    record();
                }}.start();
        }
    }

    private void stopRecord()
    {
        if(!actionUp)
        {
            actionUp = true;
            if(isRecording)
            {
                isRecording = false;
            }
            else{
                Toast.makeText(getActivity(),"debes mantener presionado el boton",Toast.LENGTH_SHORT).show();
                resetRecordView();
            }
        }
    }

    /**
     * Hilo que controla y actualiza el tiempo en el timer del recorder
     */
    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            if(!actionUp)
            {
                durationSecs +=1;
                if((durationSecs % 2)==0)
                {
                    imgRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_record));
                }
                else
                {
                    imgRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_record_off));
                }
                String duration="";
                if(durationSecs<10)
                {
                    duration = "0"+durationSecs+" ";
                }
                else
                {
                    duration += durationSecs+" ";
                }
                txtDurationRecord.setText(duration + getActivity().getResources().getString(R.string.abbrev_seconds_lbl));
                customHandler.postDelayed(this, 1000);
            }

        }

    };

    /**
     * Hilo que controla y actualiza el tiempo en el timer del reproductor de audio
     */
    private Runnable updateTimerPlayThread = new Runnable() {

        public void run() {
            if(dialog!=null && dialog.isShowing())
            {
                if(durationSecs<=durationSecsTotal)
                {
                    SeekBar seekBar = dialog.findViewById(R.id.seekBar);
                    seekBar.setProgress(durationSecs);
                    TextView txtDuration = dialog.findViewById(R.id.txtDurationAudio);
                    if(durationSecs % 1000 == 0)
                    {
                        float durationFloat = (float)durationSecs / 1000;
                        int duration = (int)durationFloat;
                        if(duration<10)
                        {
                            txtDuration.setText("0"+duration+" "+getActivity().getResources().getString(R.string.abbrev_seconds_lbl));
                        }
                        else
                        {
                            txtDuration.setText(duration+" "+getActivity().getResources().getString(R.string.abbrev_seconds_lbl));
                        }
                    }
                    durationSecsDec -=100;
                    durationSecs +=100;
                    customHandler.postDelayed(this, 100);
                }
            }
            else
            {
                resetPlayAudio();
            }
        }
    };

    /**
     * Metodo que inicia la grabación del audio en formato mp3
     * */
    public void record() {
        long starTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - starTime)<1000){}
        if(!actionUp)
        {
            isRecording = true;
            this.audioFile = FileUtils.getOutputMediaFile(getActivity(), AppConstants.FileUpload.AUDIO_REQUEST);
            minBuffer = AudioRecord.getMinBufferSize(inSamplerate, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);

            audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC, inSamplerate,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, minBuffer * 2);
            //5 seconds data
            short[] buffer = new short[inSamplerate * 2 * 5];
            // 'mp3buf' should be at least 7200 bytes long
            // to hold all possible emitted data.
            byte[] mp3buffer = new byte[(int) (7200 + buffer.length * 2 * 1.25)];
            try {
                outputStream = new FileOutputStream(new File(this.audioFile.getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            encoder = new Encoder.Builder(inSamplerate,1,inSamplerate,32).create();
            audioRecord.startRecording();
            int bytesRead = 0;
            while (isRecording) {
                bytesRead = audioRecord.read(buffer, 0, minBuffer);
                if (bytesRead > 0) {

                    int bytesEncoded = encoder.encode(buffer, buffer, bytesRead, mp3buffer);
                    if (bytesEncoded > 0) {
                        try {

                            outputStream.write(mp3buffer, 0, bytesEncoded);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            resetRecordView();
            int outputMp3buf = encoder.flush(mp3buffer);
            if (outputMp3buf > 0) {
                try {

                    outputStream.write(mp3buffer, 0, outputMp3buf);

                    outputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            audioRecord.stop();
            audioRecord.release();
            encoder.close();
            saveAudioDir(audioFile.getPath(),durationSecs);
        }
    }

    private void resetRecordView()
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lyRecord.setVisibility(View.GONE);
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
    }

    private void saveAudioDir(String path,int duration)
    {
        ItemShop itemShop = new ItemShop(path,"",1,duration);
        viewModel.insertItem(itemShop);
    }

    /**
     * manejo de permisos apartir de la version M api=23
     */

    private boolean checkMultipPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            permissionCheck_RECORD_AUDIO = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO);
            permissionCheck_READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            permissionCheck_WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            listPermissionsNeeded = new ArrayList<>();
            if(permissionCheck_RECORD_AUDIO != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
            }
            if(permissionCheck_READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if(permissionCheck_WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if(listPermissionsNeeded.isEmpty())
            {
                return true;
            }
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
    }

    /**
     * manejo de permisos apartir de la version M api=23
     */

    private boolean checkStoragePermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            permissionCheck_READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            permissionCheck_WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            listPermissionsNeeded = new ArrayList<>();

            if(permissionCheck_READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if(permissionCheck_WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if(listPermissionsNeeded.isEmpty())
            {
                return true;
            }
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_STORAGE_PERMISSIONS);
            return false;
        }
    }

    /**
     * Metodo invocado al aceptar o rechasar los permisos
     * */

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Map<String, Integer> perms = new HashMap<>();
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                    } else {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) ||
                                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                                ) {

                            dialog= HelperUtil.createAlertDialog(getActivity(),
                                    R.string.tittle_request_permissions,
                                    R.string.all_permissions_request,
                                    R.string.btn_positive_dialog_ok, R.string.btn_negative_dialog,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            checkMultipPermissions();
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                        else {
                            Toast.makeText(getContext(),  getString(R.string.go_settings), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            case REQUEST_ID_STORAGE_PERMISSIONS:
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                    } else {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                                ) {

                            dialog= HelperUtil.createAlertDialog(getActivity(),
                                    R.string.tittle_request_permissions,
                                    R.string.all_permissions_request,
                                    R.string.btn_positive_dialog_ok, R.string.btn_negative_dialog,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            checkStoragePermissions();
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                        else {
                            Toast.makeText(getContext(),  getString(R.string.go_settings), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        resetRecordView();
        resetPlayAudio();
        super.onDestroy();
    }

    private void resetPlayAudio()
    {
        if(mediaPlayer!=null) {

            customHandler.removeCallbacks(updateTimerPlayThread);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

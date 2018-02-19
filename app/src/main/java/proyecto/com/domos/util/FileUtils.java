package proyecto.com.domos.util;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;


public class FileUtils {

    /**
     * Crea un archivo a partir de un timestamp
     * @param type tipo de archivo
     * @return archivo
     */
    public static java.io.File getOutputMediaFile(Context context, int type){
        // Create a media file name
        // For unique file name appending current timeStamp with file name
        java.util.Date date= new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(date.getTime());
        String fileDir = FileUtils.getFolderPath(context);

        java.io.File mediaFile_0;
        java.io.File mediaFile;
        if (type == AppConstants.FileUpload.IMAGE_REQUEST) {
            mediaFile_0 = new java.io.File(fileDir);
            mediaFile_0.mkdirs();
            mediaFile = new java.io.File(mediaFile_0 , "image_"+ timeStamp + ".jpg");
        }
        else if (type == AppConstants.FileUpload.VIDEO_REQUEST) {
            mediaFile_0 = new java.io.File(fileDir);
            mediaFile_0.mkdirs();
            mediaFile = new java.io.File(mediaFile_0 , "video_"+ timeStamp + ".mp4");

        }
        else if (type == AppConstants.FileUpload.AUDIO_REQUEST) {
            mediaFile_0 = new java.io.File(fileDir);
            mediaFile_0.mkdirs();
            mediaFile = new java.io.File(mediaFile_0 , "audio_"+ timeStamp + ".mp3");
        }
        else {
            return null;
        }

        return mediaFile;
    }

    /**
     * Obtiene la ruta raiz para almacenamiento de archivos
     * @param context contexto de la aplicación
     * @return ruta
     */
    public static String getFolderPath(Context context) {
        return  context.getExternalFilesDir(null).getAbsolutePath() +
                File.separator +
                "Domos" +
                File.separator;
    }
}

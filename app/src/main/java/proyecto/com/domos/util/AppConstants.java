package proyecto.com.domos.util;

/**
 * Created by aranda on 7/02/18.
 */

public final class AppConstants {

    public interface Preferences {
        String PREFERENCES_NAME = "DOMOS";
        String KEY_EXAMPLE_STRING = "exampleString";
    }

    /**
     * Constantes para manejo de video o imagen
     */
    public interface FileUpload {
        int IMAGE_REQUEST = 1;
        int VIDEO_REQUEST = 2;
        int GALLERY_REQUEST = 3;
        int AUDIO_REQUEST = 4;
    }
}

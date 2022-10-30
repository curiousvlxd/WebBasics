package step.learning.services;

import com.google.inject.Singleton;

import javax.imageio.stream.ImageInputStream;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MimeService{
    private Map<String, String> imageTypes;
    public MimeService() {
        imageTypes = new HashMap<String, String>();
        imageTypes.put( "jpg", "image/jpeg" ) ;
        imageTypes.put( "jpeg", "image/jpeg" ) ;
        imageTypes.put( "png", "image/png" ) ;
        imageTypes.put( "gif", "image/gif" ) ;
        imageTypes.put( "bmp", "image/bmp" ) ;
        imageTypes.put( "webp", "image/webp" ) ;
    }
    public boolean isImage(String exrension) {
        return imageTypes.containsKey(exrension) ;
    }
    public String getMimeType(String extension) {
        if (imageTypes.containsKey(extension)) {
            return imageTypes.get(extension) ;
        }
        return null;
    }
}

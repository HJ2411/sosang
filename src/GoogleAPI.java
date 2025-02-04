import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;

public class GoogleAPI {
    private String apiKey="AIzaSyAfbGgO4o8NFZcJVgaDeR_ab6WjP3eczIc";
    void downloadMap(String location) {
        try {
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="+location+",CA&zoom=18&size=360x360&markers=color:red%7CDelta+"+location+"&key="+apiKey;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(location);
            byte[] b = new byte[2048];
            int length;
            while((length = is.read(b))!=-1) {
                os.write(b,0,length);
            }
            is.close();
            os.close();
        }catch (Exception e) {

        }
    }
    ImageIcon getMap(String location) {
        return new ImageIcon((new ImageIcon(location)).getImage().getScaledInstance(360, 360, java.awt.Image.SCALE_SMOOTH));
    }
    void fileDelete(String fileName) {
        File f = new File(fileName);
        f.delete();
    }
}

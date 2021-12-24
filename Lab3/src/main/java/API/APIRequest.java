package API;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class APIRequest {

    public static String request(String URL) throws IOException {
        URLConnection connection = new URL(URL).openConnection();
        InputStream is = connection.getInputStream();
        byte[] arr = new byte[1000000];
        String result = "";
        while(true){
            int i = is.read(arr);
            if(i == -1)
                break;
            result = result.concat(new String(arr, 0, i));
        }
        is.close();

        return result;
    }

}

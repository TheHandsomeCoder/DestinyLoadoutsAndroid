package thehandsomecoder.com.destinyloadouts;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by somalley on 29/04/2015.
 */
public class ConfluxCookieStore implements CookieStore {

    public ConfluxCookieStore(Context context){
        readCookiesFromFile(context);
    }

    private void readCookiesFromFile(Context context) {
        File file = getCookieDirectory(context);
        if(file == null)
        {
            return;
        }
        else
        {
            File[] files = file.listFiles();
            for( File f : files)
            {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(f));
                    String s = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        s += line;
                    }
                    Log.v("CookieFile", s);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File getCookieDirectory(Context context)
    {
        File filesDirectory = context.getFilesDir();
        File cookieDirectory = new File((new StringBuilder().append(filesDirectory.getPath()).append('/').append("cookies").append('/').toString()));
        cookieDirectory.mkdirs();
        return cookieDirectory;
    }



    @Override
    public void add(URI uri, HttpCookie cookie) {

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return null;
    }


    @Override
    public List<HttpCookie> getCookies() {
        return null;
    }

    @Override
    public List<URI> getURIs() {
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }
}

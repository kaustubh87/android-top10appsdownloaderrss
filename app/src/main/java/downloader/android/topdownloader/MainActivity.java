package downloader.android.topdownloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    TextView textview;
    Button btnParse;
    ListView listApps;
    String xmlData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = (Button)findViewById(R.id.btnparse);
        listApps = (ListView)findViewById(R.id.listApps);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseApplication parse = new parseApplication(xmlData);
                boolean operationStatus = parse.process();
                if(operationStatus)
                {
                    ArrayList<Application> allApps = parse.getApplications();
                    ArrayAdapter <Application> adapter = new ArrayAdapter<Application>(MainActivity.this,R.layout.list_item,allApps);

                    listApps.setVisibility(listApps.VISIBLE);
                    listApps.setAdapter(adapter);

                }


            }
        });




        new DownloadData().execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class DownloadData extends AsyncTask<String, Void, String> {

        String myxmlData;

        @Override
        protected String doInBackground(String... urls) {
            try {

                myxmlData = downloadXML(urls[0]);


            } catch (Exception e) {
                e.printStackTrace();


            }
            return null;
        }

        protected void onPostExecute(String result){

            Log.d("OnPostExecute",myxmlData);
            xmlData = myxmlData;

        }

        private String downloadXML(String url) throws IOException {

            int buffer_size = 2000;
            InputStream is = null;

            String xmlContent = "";
            try {

                URL weburl = new URL(url);
                HttpURLConnection con = (HttpURLConnection)weburl.openConnection();
                con.setReadTimeout(10000);
                con.setConnectTimeout(15000);
                con.setRequestMethod("GET");
                con.setDoInput(true);
                int response = con.getResponseCode();  //200 for OK
                Log.d("Download XML", "Response is " +response);
                is = con.getInputStream();

                InputStreamReader isr = new InputStreamReader(is);
                int charRead;
                char[] inputBuffer = new char[buffer_size];
                try{

                    while((charRead=isr.read(inputBuffer))>0)
                    {
                        String readString = String.copyValueOf(inputBuffer,0,charRead);
                        xmlContent += readString;
                        inputBuffer = new char[buffer_size];


                    }

                    return xmlContent;

                }

                catch(Exception e)
                {
                    e.printStackTrace();
                    return null;

                }

        } finally {

                if (is != null) {
                    is.close();

                }
            }

        }


    }


}

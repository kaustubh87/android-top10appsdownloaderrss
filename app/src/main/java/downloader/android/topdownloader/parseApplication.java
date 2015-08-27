package downloader.android.topdownloader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Kaustubh on 8/25/2015.
 */
public class parseApplication {

    private String data;
    private ArrayList<Application> applications;




    public parseApplication(String data) {
        this.data = data;
        applications = new ArrayList<Application>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public boolean process() {

        boolean operationStatus = true;

        Application currentRecord = null;
        boolean inEntry = false;
        String textValue = " ";

        try {


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(this.data));
            int eventtype = parser.getEventType();

            while (eventtype != parser.END_DOCUMENT) {
                String tagname = parser.getName();
                if (eventtype == parser.START_TAG) {
                    if (tagname.equalsIgnoreCase("entry")) {
                        inEntry = true;
                        currentRecord = new Application();

                    }


                } else if (eventtype == parser.TEXT) {

                    textValue = parser.getText();


                } else if (eventtype == parser.END_TAG) {
                    if (inEntry) {
                        if (tagname.equalsIgnoreCase("entry")) {
                            applications.add(currentRecord);
                            inEntry = false;
                        }
                        if (tagname.equalsIgnoreCase("name")) {
                            currentRecord.setName(textValue);
                        } else if (tagname.equalsIgnoreCase("artist")) {

                            currentRecord.setArtist(textValue);
                        } else if (tagname.equalsIgnoreCase("releaseDate")) {

                            currentRecord.setReleaseDate(textValue);
                        }

                    }


                }


                eventtype = parser.next();


            }


        } catch (Exception e) {
            e.printStackTrace();
            operationStatus = false;

        }

        return operationStatus;


    }
}

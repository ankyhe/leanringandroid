package com.gmail.at.gerystudio.criminalIntent.model;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 8/26/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CrimeReposSerializer {

    private Context ctx;
    private String fileName;

    private static final String LOG_TAG = CrimeReposSerializer.class.getName();

    public CrimeReposSerializer(Context aCtx, String aFileName) {
        ctx = aCtx;
        fileName = aFileName;
    }


    public List<Crime> loadCrimes() {
        List<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try {
            try {
                // Open and read the file into a StringBuilder
                InputStream in = ctx.openFileInput(fileName);
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder jsonString = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    // Line breaks are omitted and irrelevant
                    jsonString.append(line);
                }
                // Parse the JSON using JSONTokener
                JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                        .nextValue();
                // Build the array of crimes from JSONObjects
                for (int i = 0; i < array.length(); i++) {
                    crimes.add(new Crime(array.getJSONObject(i)));
                }
            } catch (FileNotFoundException e) {
                // Ignore this one; it happens when starting fresh
            } catch (JSONException e) {

            } catch (IOException e) {

            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (IOException e) {

        }
        return crimes;
    }

    public void saveCrimes(List<Crime> crimes) {
        try {
            JSONArray array = new JSONArray();
            for (Crime c : crimes) {
                array.put(c.toJSON());
            }
            Writer writer = null;
            try {
                OutputStream out = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
                writer.write(array.toString());
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to save crimes", e);
        }
    }



}

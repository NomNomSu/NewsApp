package g.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grzegorz on 2017-07-10.
 */

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static String author;

    private QueryUtils() {
    }

    public static List<News> fetchBookData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<News> newses = extractFeatureFromJson(jsonResponse);
        return newses;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> newses = new ArrayList<>();


        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject newsResponse = baseJsonResponse.getJSONObject("response");
            Log.d("ND response", String.valueOf(newsResponse));
            JSONArray newsArray = newsResponse.getJSONArray("results");
            Log.d("ND results", String.valueOf(newsArray));

            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject currentNews = newsArray.getJSONObject(i);


                //JSONObject newsTitleProperties = currentNews.getJSONObject("webTitle");
                //Log.d("ND item prop", String.valueOf(newsTitleProperties));
                String title = currentNews.getString("webTitle");
                Log.d("ND item title", String.valueOf(title));

                JSONObject newsSectionProperties = currentNews.getJSONObject("section");
                //Log.d("ND url prop", String.valueOf(newsURLProperties));
                //JSONArray authors = newsURLProperties.getJSONArray("webUrl");
                String section = "Section: " + newsSectionProperties.getString("id");

                String url = currentNews.getString("webUrl");
                Log.d("URL", currentNews.getString("webUrl"));

                if (title.contains("|")) {
                    author ="Author: " + title.substring(title.indexOf("|") + 1);
                } else {author = "Author: unknown";}
                News news = new News(title, section, author, url);
                newses.add(news);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return newses;
    }
}
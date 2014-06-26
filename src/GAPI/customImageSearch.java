package GAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import JSON.JSONArray;
import JSON.JSONException;
import JSON.JSONObject;
import Struts.ImageSearchScope;

public class customImageSearch {

	
	public String url="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Help%20me%20raise%20money%20to%20buy%20Nikola%20Tesla's%20old%20laboratory%20-%20The%20Oatmeal";
public customImageSearch(){
	
	super();
}
public String readContent(String url) {
	try {

		StringBuilder sb = new StringBuilder();
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();

		//conn.setConnectTimeout(30000);
		conn.setRequestMethod("GET");

		InputStream is = conn.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while (true) {

			String line = br.readLine();
			if (line == null) {
				break;
			}
			sb.append(line);
		}
		br.close();
		//Log.i("content", sb.toString());
		return sb.toString();
	} catch (IOException e) {
		//Log.i("QueryIOErr", e.getMessage());
		return e.getMessage();
	} catch (Exception e) {
		//Log.i("QueryErr", e.getMessage());
		return e.getMessage();
	}
}
public ArrayList<ImageSearchScope> getImageResult(String query) throws JSONException{
	
	ArrayList<ImageSearchScope> search=new ArrayList<ImageSearchScope>();
	query=query.replace(" ", "%20");
	String JsonString = readContent("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query);
	JSONObject jarr = new JSONObject(JsonString);
	if(jarr.getJSONObject("responseData").has("results")){
		 JSONArray items = jarr.getJSONObject("responseData").getJSONArray("results");
		 for(int i=0;i<items.length();i++){
			 ImageSearchScope ss=new ImageSearchScope();
			 JSONObject json = (JSONObject) items.get(i);
			 String url=json.getString("url");
			 String title=json.getString("title");
			 String content=json.getString("content");
			 String orginalURL=json.getString("originalContextUrl");
			int height=json.getInt("height");
			 int width=json.getInt("width");
			 ss.setUrl(url);
			 ss.setTitle(title);
			 ss.setContent(content);
			 ss.setHeight(height);
			 ss.setWidth(width);
			 ss.setOrginalURL(orginalURL);
			 
			 search.add(ss);
			 
		 }
	
		
		
	}
	return search;
	
}
public static void main(String[] args) throws Exception {
	String id="115333165448794487137";
	String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
	customImageSearch cis=new customImageSearch();
	cis.getImageResult("Android");
	
}
}

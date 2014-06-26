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
import Struts.customGoogleSearchResult;
import Struts.webSearchResult;

public class webGSearch {

	public String urlString="https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=How%20to%20suck%20at%20your%20religion%20-%20The%20Oatmeal";
	
public webGSearch() {
	// TODO Auto-generated constructor stub
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
public ArrayList<webSearchResult> getWebSearch(String word) throws JSONException{
	ArrayList<webSearchResult> ws=new ArrayList<webSearchResult>();
	word=word.replace(" ", "%20");
	String JsonString = readContent("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="+word);
	JSONObject jarr = new JSONObject(JsonString);
	if(jarr.getJSONObject("responseData").has("results")){
	 JSONArray items = jarr.getJSONObject("responseData").getJSONArray("results");
	 
	 for(int n=0;n<items.length();n++){
		 webSearchResult wResult=new webSearchResult();
		 JSONObject json=(JSONObject) items.get(n); 
		 String url=json.getString("url"); System.out.println(url);
		 String visibleURL=json.getString("visibleUrl"); System.out.println(visibleURL);
		 String title=json.getString("title"); System.out.println(title);
		 String content=json.getString("content"); System.out.println(content);
		 wResult.setURL(url);
		 wResult.setVisibleURL(visibleURL);
		 wResult.setTitle(title);
		 wResult.setContent(content);
		 ws.add(wResult);
	 }
	 return ws;
	}else{
		
	return null;	
		
	}
	
}
public static void main(String[] args) throws Exception {
	String id="115333165448794487137";
	String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
	webGSearch wGSearch=new webGSearch();
	wGSearch.getWebSearch("yueyue");
}
}

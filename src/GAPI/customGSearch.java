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

public class customGSearch {
public String url="https://www.googleapis.com/customsearch/v1?key=AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4&cx=010843071085095631450:wolkthf_dg4&q=How%20to%20suck%20at%20your%20religion%20-%20The%20Oatmeal";
public customGSearch(){
	
	super();
}
public String readContent(String url) {
	try {

		StringBuilder sb = new StringBuilder();
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();

		conn.setConnectTimeout(30000);
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
public ArrayList<customGoogleSearchResult> getCustomSearch(String keyword) throws JSONException{
	ArrayList<customGoogleSearchResult> cs=new ArrayList<customGoogleSearchResult>();
	System.out.print(keyword);
	keyword=keyword.replace(" ", "%20");
	String JsonString = readContent("https://www.googleapis.com/customsearch/v1?key=AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4&cx=010843071085095631450:wolkthf_dg4&q="+keyword);
	JSONObject jarr = new JSONObject(JsonString);
	if(jarr.has("items")){
	 JSONArray items = jarr.getJSONArray("items");
	 for(int i=0;i<items.length();i++){
		 customGoogleSearchResult csResult=new customGoogleSearchResult();
			JSONObject json = (JSONObject) items.get(i);
		 String title=json.getString("title");    System.out.println(title);
		 String link=json.getString("link");    System.out.println(link);
		 String snippet=json.getString("snippet");   System.out.println(snippet);
		 if(json.has("cacheId")){String id=json.getString("cacheId"); System.out.println(id);
		 csResult.setId(id);
		 }
		 csResult.setTitle(title);
		 csResult.setLink(link);
		 csResult.setSnippet(snippet);
		 cs.add(csResult);
	 }
	return cs; 
	}else{
		return null; 
		 
	 }

}

public static void main(String[] args) throws Exception {
	String id="115333165448794487137";
	String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
	customGSearch cSearch=new customGSearch();
	cSearch.getCustomSearch("yueyue");
}
}

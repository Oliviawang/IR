package GAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;





import JSON.JSONArray;
import JSON.JSONException;
import JSON.JSONObject;
import Struts.Activity;
import Struts.PeopleProfile;

public class getMyActivity {
public String urlString="https://www.googleapis.com/plus/v1/people/115333165448794487137/activities/public?alt=json&key=AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";

	public getMyActivity(){
		
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
public   ArrayList<Activity> getActivity(String id, String key) throws IOException {
	ArrayList<Activity> output = new ArrayList<Activity>();
String url="https://www.googleapis.com/plus/v1/people/"+id+"/activities/public?alt=json&key="+key;
	System.out.println(url);
	String JsonString = readContent("https://www.googleapis.com/plus/v1/people/"+id+"/activities/public?alt=json&key="+key);

	try {
		JSONObject jarr = new JSONObject(JsonString);
		 JSONArray items = jarr.getJSONArray("items");
				for (int j = 0; j < items.length(); j++) {
					JSONObject json = (JSONObject) items.get(j);
					Activity ot= new Activity();
				
					ot.setID(json.getJSONObject("object").getString("id"));
					ot.setTitle(json.getString("title"));
					//System.out.println("title:"+json.getString("title"));
					ot.setContent(json.getJSONObject("object").getString("content"));
					ot.setAction(json.getString("verb"));
					//System.out.println(json.getString("verb"));
					//System.out.println("content:"+json.getJSONObject("object").getString("content"));
					JSONObject jsonObject= (JSONObject) json.getJSONObject("object").getJSONArray("attachments").get(0);
					if(jsonObject.getString("objectType").equals("article"))
					{	ot.setType(jsonObject.getString("objectType"));
						ot.setName(jsonObject.getString("displayName"));
					//System.out.println("a1:"+jsonObject.getString("displayName"));
					ot.setText(jsonObject.getString("content"));
					//System.out.println("a2:"+jsonObject.getString("content"));
					
					}
					output.add(ot);
				
				}
			
			
	} catch (JSONException e) {
		//Log.i("QueryErr", e.getMessage());
	}
	output=removeDuplicate(output);
	System.out.println(output);
	return output;
}

public ArrayList<Activity> removeDuplicate(ArrayList<Activity> arlList)
{
 @SuppressWarnings("unchecked")
HashSet<Activity> h = new HashSet<Activity>(arlList);
 arlList.clear();
 arlList.addAll(h);
return arlList;
}
public static void main(String[] args) throws Exception {
	String id="115333165448794487137";
	String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
	getMyActivity gmp=new getMyActivity();
	gmp.getActivity(id,key);
}
}

package GAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.lucene.queryParser.ParseException;

import com.sun.xml.internal.bind.v2.model.core.ID;

import JSON.JSONArray;
import JSON.JSONException;
import JSON.JSONObject;
import Struts.Activity;
import Struts.PeopleProfile;

public class activityList {

	public String url="https://www.googleapis.com/plus/v1/people/115333165448794487137/activities/public?alt=json&key=AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
	
	
	public activityList(){
		
		
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
			
			return sb.toString();
		} catch (IOException e) {
			
			return e.getMessage();
		} catch (Exception e) {
			
			return e.getMessage();
		}
	}
	public ArrayList<Activity> getActivityList(String id, String key) throws IOException{
	
		
		listByActivity lsaActivity=new listByActivity();
	
		ArrayList<PeopleProfile> people=new ArrayList<PeopleProfile>();
	people=lsaActivity.listPeopleFromActivity(id, key);
	for(PeopleProfile pp:people){
		
		System.out.println("user_id"+pp.id);
		System.out.println("PPname"+pp.name);
		
	}
		ArrayList<Activity>  allActivities=new ArrayList<Activity>();
		ArrayList<String> ids=new ArrayList<String>();
		for(int i=0;i<people.size();i++){
			String userid=people.get(i).id;
			ids.add(userid);
		}
		ids=removeDuplicate(ids);
		for(int i=0;i<ids.size();i++){
			String userid=ids.get(i);
		String JsonString = readContent("https://www.googleapis.com/plus/v1/people/"+userid+"/activities/public?alt=json&key="+key);
		try {
			JSONObject jarr = new JSONObject(JsonString);
			 JSONArray items = jarr.getJSONArray("items");
					for (int j = 0; j < items.length(); j++) {
						JSONObject json = (JSONObject) items.get(j);
						Activity ot= new Activity();
						ot.setID(json.getString("id"));
						ot.setTitle(json.getString("title"));
						System.out.println("title:"+json.getString("title"));
						ot.setContent(json.getJSONObject("object").getString("content"));
						ot.setAction(json.getString("verb"));
						ot.setActorID(json.getJSONObject("actor").getString("id"));
						ot.setActorName(json.getJSONObject("actor").getString("displayName"));
						ot.setActorPic(json.getJSONObject("actor").getJSONObject("image").getString("url"));
						ot.setProfileLink(json.getJSONObject("actor").getString("url"));
						JSONObject jsonObject= (JSONObject) json.getJSONObject("object").getJSONArray("attachments").get(0);
						
						
							
							if(jsonObject.getString("objectType").equals("article"))
						{	ot.setType("article");
							if(jsonObject.has("displayName")){
							ot.setName(jsonObject.getString("displayName"));
						System.out.println("a1:"+jsonObject.getString("displayName"));
						
						}
						if(jsonObject.has("content")){
						ot.setText(jsonObject.getString("content"));
						System.out.println("a2:"+jsonObject.getString("content"));}
						if(jsonObject.has("url")){
							ot.setURL(jsonObject.getString("url"));
							System.out.println("articleURL"+jsonObject.getString("url"));
						}
						}else if(jsonObject.getString("objectType").equals("video")){
							ot.setType("video");
							if(jsonObject.has("displayName")){
								
								ot.setName(jsonObject.getString("displayName"));
							}
							if(jsonObject.has("content")){
								
								ot.setText(jsonObject.getString("content"));
							}
							if(jsonObject.has("url")){
								ot.setURL(jsonObject.getString("url"));
								System.out.println("videoURL"+jsonObject.getString("url"));
							}
						}else if(jsonObject.getString("objectType").equals("photo")){
							if(jsonObject.getJSONObject("fullImage").has("url")){
								System.out.println("picURL"+jsonObject.getJSONObject("fullImage").getString("url"));
								ot.setURL(jsonObject.getJSONObject("fullImage").getString("url"));
							}
						}
						System.out.println("content:"+json.getJSONObject("object").getString("content"));					//ot.setDesc(json.getString("des"));
					
						allActivities.add(ot);
						
					}
				
				
		} catch (JSONException e) {
			
		}
		}
		
		return allActivities;
		
		
		
		
	}
	
	public ArrayList<String> removeDuplicate(ArrayList<String> arlList)
	{
	 @SuppressWarnings("unchecked")
	HashSet<String> h = new HashSet<String>(arlList);
	 arlList.clear();
	 arlList.addAll(h);
	return arlList;
	}
	public static void main(String[] args) throws Exception {
		
		String id="115333165448794487137";
		String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
		activityList gmp=new activityList();
		gmp.getActivityList(id, key);
	}
}

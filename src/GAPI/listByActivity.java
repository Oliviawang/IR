package GAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import JSON.JSONArray;
import JSON.JSONException;
import JSON.JSONObject;
import Struts.Activity;
import Struts.PeopleProfile;

public class listByActivity {

	
	String urlString="https://www.googleapis.com/plus/v1/activities/z13aed5xzsiidfcfj22ec5up1uryw1u2r/people/plusoners?key=AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
	
	public listByActivity(){
		
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
	public ArrayList<PeopleProfile> listPeopleFromActivity(String id,String key) throws IOException{
	   getMyActivity gMyActivity=new getMyActivity();
	ArrayList<Activity>  activities= gMyActivity.getActivity(id,key);
	
	
	ArrayList<PeopleProfile> profiles=new ArrayList<PeopleProfile>();
	
	for(Activity activity:activities){
		String activityID=activity.id;
	System.out.println("activityID"+activityID);
		String JsonString = readContent("https://www.googleapis.com/plus/v1/activities/"+activityID+"/people/plusoners?key="+key);
		try {
			JSONObject jarr = new JSONObject(JsonString);
			 JSONArray items = jarr.getJSONArray("items");
					for (int j = 0; j < items.length(); j++) {
						JSONObject json = (JSONObject) items.get(j);
						
						PeopleProfile pp= new PeopleProfile();
						
							pp.setID(json.getString("id"));
						System.out.println(json.get("id"));
						pp.setName(json.getString("displayName"));
						System.out.println("name:"+json.getString("displayName"));
						pp.setImage(json.getJSONObject("image").getString("url"));
						System.out.println("image:"+json.getJSONObject("image").getString("url"));					//ot.setDesc(json.getString("des"));
						pp.setUrl(json.getString("url"));
						
						
						profiles.add(pp);
						//Log.i("output", ot.toString());
					}
				
		
		} catch (JSONException e) {
			//Log.i("QueryErr", e.getMessage());
		}
		
	}
	
	for(PeopleProfile p:profiles){
		
		System.out.println("lba"+p.id);
		System.out.println("lba_name"+p.name);
		
	}
	//ArrayList<PeopleProfile> profiles2=new ArrayList<PeopleProfile>();
	//profiles2=Remove(profiles);
	//System.out.println(profiles);
	return profiles;
	
	}
	public  ArrayList<PeopleProfile>  removeDuplicates(ArrayList<PeopleProfile> list) {
	    int size = list.size();
	    ArrayList<PeopleProfile> peopleProfiles=new ArrayList<PeopleProfile>();
	    
	    int out = 0;
	    {
	        final Set<PeopleProfile> encountered = new HashSet<PeopleProfile>();
	        for (int in = 0; in < size; in++) {
	            final PeopleProfile t = list.get(in);
	            final boolean first = encountered.add(t);
	            if (first) {
	            	peopleProfiles.add(list.get(in));
	                list.set(out++, t);
	            }
	        }
	    }
	    while (out < size) {
	        list.remove(--size);
	    }
		return peopleProfiles;
	}
public ArrayList<PeopleProfile> Remove(ArrayList<PeopleProfile> l1){
	
	
//	ArrayList l1 = new ArrayList();
	ArrayList<PeopleProfile> l2 = new ArrayList<PeopleProfile>();

	Iterator iterator = l1.iterator();

	        while (iterator.hasNext())
	        {
	          PeopleProfile o = (PeopleProfile) iterator.next();
	            if(l2.indexOf(o)==-1) l2.add(o);
	        }
			return l2;
	
	
}
/*	public ArrayList<PeopleProfile> removeDuplicate(ArrayList<PeopleProfile> arlList)
	{
	 @SuppressWarnings("unchecked")
	Set<PeopleProfile> h = new HashSet<PeopleProfile>(arlList);
	 arlList.clear();
	 arlList.addAll(h);
	return arlList;
	}*/

	public static void main(String[] args) throws Exception {
		String id="115333165448794487137";
		String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
		listByActivity lba=new listByActivity();
		lba.listPeopleFromActivity(id, key);
	}
	
	
}

package GAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import Struts.Activity;
import Struts.TermResult;

public class categoryGroup {
public static String id="115333165448794487137";
public static String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
public static ArrayList<TermResult> lastTermResults = new ArrayList<TermResult>();
	public categoryGroup() {
		// TODO Auto-generated constructor stub
	super();
	}
	public  ArrayList<Activity> groupByAction(String Keyword) throws IOException{
		activityList aList=new activityList();
		ArrayList<Activity> lActivities=new ArrayList<Activity>();
	 ArrayList<Activity> list=new ArrayList<Activity>();
			lActivities=aList.getActivityList(id, key);
	for(Activity a:lActivities){
		boolean flag=true;
		if(a.name!=null){
		for(Activity ac:list){
			
			if(ac.name.equals(a.name)){
				flag=false;
				break;
				
			}
			
		}
		if(a.text==null){
			
			a.text="There is no content";
		}
		if(flag){
			
			list.add(a);
		}
	}
		
	    
	}
	return list;
	
	}
	public  ArrayList<Activity> groupByRelevance(String keyword) throws IOException{
		activityList aList=new activityList();
		ArrayList<Activity> lActivities=new ArrayList<Activity>();
	 
			lActivities=aList.getActivityList(id, key);
		
		calRelevance.writeIndex(lActivities);
	//	calRelevance.SearchIndex("Android");
		ArrayList<TermResult> lastTermResults = new ArrayList<TermResult>();
		ArrayList<TermResult> termResults=new ArrayList<TermResult>();
	termResults=calRelevance.readIndex(keyword,"name");
	
		termResults.addAll(calRelevance.readIndex(keyword, "text"));// actually we need to get intersectionfor different search result, use contains to add overlapping part
		termResults.addAll(calRelevance.readIndex(keyword, "username"));
		termResults.addAll(calRelevance.readIndex(keyword, "content"));
		for(TermResult tr:termResults){/// if hashmap<Integer,termresult> actually used works
			// need to split keyword to string[], use hashset to get common doc(termresult)
			Boolean flag=true;
			for(TermResult last:lastTermResults){
			
		if(tr.word.equals(last.word)&&tr.docno.equals(last.docno)){
			flag=false;
			break;
		}
			}
			if(flag){
				TermResult lastResult=new TermResult();
				lastResult=tr;
				lastTermResults.add(lastResult);
			}
		
			
		}
/*for(TermResult tr:lastTermResults){
			System.out.println("word"+tr.word);
			System.out.println("docid"+tr.docid);
			System.out.println("docNo"+tr.docno);
			System.out.println("doctf"+tr.score);
			System.out.println("doclen"+tr.doclen);
		}*/
		 Collections.sort(lastTermResults, new Comparator<TermResult>() {

	            @Override
	            public int compare(TermResult o1, TermResult o2) {
	                if (o1.score > o2.score) {
	                    return -1;
	                } else if (o1.score < o2.score) {
	                    return 1;
	                } else {
	                    return 0;
	                }
	            }
	        

				
				});
		 System.out.println("lasttermResults"+lastTermResults);
	  termResults=BM25Process.getBM25Result(lastTermResults);
	  ArrayList<Activity> returnList=new ArrayList<Activity>();
		 
	  for(TermResult tr:termResults){
		  System.out.println("BM25");
			System.out.println("word"+tr.word);
			System.out.println("docid"+tr.docid);
			System.out.println("docNo"+tr.docno);
			System.out.println("doctf"+tr.score);
			System.out.println("doclen"+tr.doclen);
			for(Activity a:lActivities){
				if(a.id.equals(tr.docno)){
					
					returnList.add(a);
				}
				
			}
			
		}
		return returnList;
		
		
	}
	public static ArrayList<String> removeDuplicate(ArrayList<String> arlList,HttpServletRequest request)
	{
	 @SuppressWarnings("unchecked")
	HashSet<String> h = new HashSet<String>(arlList);
	 arlList.clear();
	 arlList.addAll(h);
	return arlList;
	}
	
@SuppressWarnings("static-access")
public static void main(String[] args) throws Exception {
		
		String id="115333165448794487137";
		String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
		categoryGroup  gmp=new categoryGroup();
		gmp.groupByRelevance("Google");
	}
}

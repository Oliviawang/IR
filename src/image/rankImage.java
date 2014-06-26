package image;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Struts.Image;
import Struts.ImageSearchScope;
import Struts.TermResult;

public class rankImage {

	
	public rankImage(){
		
		super();
		
	}
	public ArrayList<Image> getRankResult(String inputURL,String scopeURL) throws Exception{
		pHashImage ph=new pHashImage();
		System.out.println("inputURL"+inputURL);
		ArrayList<Image>  images=new ArrayList<Image>();
		ArrayList<ImageSearchScope> imageCollections=new ArrayList<ImageSearchScope>();
		if(scopeURL==null){
			
			scopeURL="http://www.facebook.com";
		}
		imageCollections=getPicURLs(scopeURL);
	/*	InputStream is =getInputStream("http://s3.amazonaws.com/theoatmeal-img/thumbnails/tesla_museum.png");
		InputStream is2=getInputStream("http://static1.businessinsider.com/image/502cfcc269bedda735000012-620-/tesla-museum.jpg");
	int distance=ph.distance(ph.getHash(is), ph.getHash(is));
	System.out.println("distance"+distance);*/
	for(ImageSearchScope coScope:imageCollections){
		Image image=new Image();
		int distance;
		distance=ph.distance(ph.getHash(getInputStream(inputURL)), ph.getHash(getInputStream(coScope.url)));
		image.setURL(coScope.url);
		image.setTitle(coScope.title);
		image.setScore(distance);
		images.add(image);
	}
	 Collections.sort(images, new Comparator<Image>() {

         @Override
         public int compare(Image o1, Image o2) {
             if (o1.score < o2.score) {
                 return -1;
             } else if (o1.score > o2.score) {
                 return 1;
             } else {
                 return 0;
             }
         }
     

			
			});
	 for(Image image:images){
	System.out.println(image.score);
	
	 }
	 return images;
	}
	public InputStream getInputStream(String path) throws Exception {
		URL url = new URL(path);
		return url.openStream();
	}
	public static void main(String[] args) throws Exception {
	rankImage riImage=new rankImage();
//	riImage.getRankResult("http://media-cache-ec2.pinterest.com/upload/52987733085870858_LduNVlqw_c.jpg");
	//riImage.getPicURLs("http://pinterest.com/");
	
	}
	private ArrayList<ImageSearchScope> getPicURLs(String urlScope) throws Exception {
		ArrayList<ImageSearchScope> imageSearchScopes=new ArrayList<ImageSearchScope>();
		URL u = new URL(urlScope);
		BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
		String line = "";
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		String pattern="<img[\\s]+[^>]*?((alt*?[\\s]?=[\\s\"']+(.*?)[\"']+.*?)|(src*?[\\s]?=[\\s\"']+(.*?)[\"']+.*?))((src*?[\\s]?=[\\s\"']+(.*?)[\"']+.*?>)|(alt*?[\\s]?=[\\s\"']+(.*?)[\"']+.*?>)|>)";

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(sb.toString());
		//System.out.println("m"+m);
		while (m.find()) {
			ImageSearchScope isp=new ImageSearchScope();
			String url = m.group(5);
	
			String title=m.group(10);  //alt
		
		
			if (url.length() > 1) {
				if (url.charAt(0) == '\"') {
					url = url.substring(1);
				}
				if (url.charAt(url.length() - 1) == '\"') {
					url = url.substring(0, url.length() - 1);
				}
				if(url.charAt(0)=='/'){
					
					String suffix= urlScope.substring(0,urlScope.substring(8).indexOf("/")+8);
					url=suffix+url;
				}
				 isp.setUrl(url);
				 isp.setTitle(title);
				 
				 //System.out.println("url"+url);
			}
			imageSearchScopes.add(isp);
		}
		
		return imageSearchScopes;
	}

	
	

}

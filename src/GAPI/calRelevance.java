package GAPI;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

//import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import Struts.Activity;
import Struts.TermResult;

public class calRelevance {

	
	public static String id="115333165448794487137";
	public static String key="AIzaSyDqFvU20A3qIMz6lChQIcl5EvrHRCC4AS4";
	 static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
	 static double IDF;
		public calRelevance(){
			
			
			super();
		}
	public static void writeIndex(ArrayList<Activity> activities) throws IOException{
		
		Directory ixdir = FSDirectory.open(new File("D:\\workspace\\eclipse\\IR\\WebContent\\build.index"));
		IndexWriterConfig ixconfig = new IndexWriterConfig(Version.LUCENE_35,analyzer);
		IndexWriter ixwriter = new IndexWriter(ixdir, ixconfig);
		
		  for (int j = 0; j < activities.size(); j++) {
			 String id=activities.get(j).id;   //System.out.println("rel"+id);
		    	 String uid=activities.get(j).actorID;     //System.out.println("rel"+uid);
		    	 String username=activities.get(j).actorName;
		    	// System.out.println("rel"+username);
		    	 String name=activities.get(j).name; // System.out.println("rel"+name);
		    	 String text=activities.get(j).text;  //System.out.println("rel"+text);
		    	 String content=activities.get(j).content;
		    	 Document doc = new Document();
	if(id!=null){ 		Field fdID = new Field("id", id, Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.NO);doc.add(fdID);} // This is a field for DOCNO, which will be stored in the index (so we can later get docid for each document), but this field will not be indexed (so it cannot be searched).
	if(text!=null){			Field fdText = new Field("text", text, Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS);doc.add(fdText);}
	if(name!=null){			Field fdName=new Field("name", name, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS);doc.add(fdName);}
		if(uid!=null){		Field fduid=new Field("uid", uid, Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.WITH_POSITIONS);doc.add(fduid);}
		if(username!=null){		Field fduser=new Field("username", username, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS);	doc.add(fduser);}
				if(content!=null){
					
					Field fdContent = new Field("content", content, Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS);doc.add(fdContent);
				}
		
				
		ixwriter.addDocument(doc);
				
			
				
			
				
		     }
		  ixwriter.close(); 
		
	}
	public static void SearchIndex(String query) throws ParseException, IOException{
		Directory ixdir = FSDirectory.open(new File("D:\\workspace\\eclipse\\IR\\WebContent\\build.index"));
	    Query q = new MultiFieldQueryParser(Version.LUCENE_35, new String[] {"username","name","text"}, analyzer).parse(query);
		
	    int hitsPerPage = 10;
	    @SuppressWarnings("deprecation")
		IndexSearcher searcher = new IndexSearcher(ixdir, true);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    System.out.println("Found " + hits.length + " hits.");
	    for(int i=0;i<hits.length;++i) {
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	  
	      System.out.println((i + 1) + ". " +  d.get("username")+"."+d.get("name"));
	    }
	    ixdir.close();
	    searcher.close();
	
	}
	public static  ArrayList<TermResult> readIndex(String word,String t) throws IOException{
		ArrayList<TermResult> termResults=new ArrayList<TermResult>();
		String[] words;
		
		words=word.split(" ");
         System.out.println(words[0]);
		// first step, build a lucene index reader
		Directory ixdir = FSDirectory.open(new File("D:\\workspace\\eclipse\\IR\\WebContent\\build.index"));
		IndexReader ixreader = IndexReader.open(ixdir);
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		for(String w:words){
		// get a stem of the word
		String stem = getStem(w, analyzer);
		//String stem=word;
		// now create a lucene Term object.
		// you should also specify in which document field you would like to search for the term ("CONTENT");
		Term term = new Term(t, stem);
		
		// now retrieve posting list for the term
		// TermDocs is an object for reading term doc-freq posting
		// if you need a term doc-position posting, use ixreader.termPositions(term) to get a TermPositions posting object
		TermDocs posting = ixreader.termDocs(term);
		//System.out.println("Term"+ixreader.terms());
		// retrieve basic information of the term
		System.out.print("term stem \""+stem+"\"");
		// get the document frequency DF for the term
		int DF = ixreader.docFreq(term);
		System.out.print(", DF = "+DF);
		// get the total number of document in the index
		int N = ixreader.numDocs();
		System.out.print(", N = "+N);
		if(DF>0){
			// now you can calculate IDF for the term.
			 IDF = Math.log((N)/(DF));
			System.out.printf(", IDF = %6.4f\n", IDF);
		}
		
		// now iteratively reading postings info
		//System.out.printf("%-10s  %-30s  %-6s  %-6s\n", "internalid", "docno", "TF", "doclen");
		// each time posting.next() tries to move posting list to the next document, if false, it indicates the end of the posting list
		while(posting.next()){
			
			// you can read the current document and TF by doc() and freq()
			int docid = posting.doc();
			int tf = posting.freq();
			double score=tf*IDF;
			// now you can get the DOCNO we previously stored in the index for each document.
			String docno = ixreader.document(docid).get("id");
			
			// Lucene uses VSM and does not store document length in index, so you need to calculate it by yourself.
			// ixreader.getTermFreqVector(docid, "CONTENT") will give you a document vector object, you can 
			// sum up frequency of each word to calculate the length of the document
			int doclen = 0;
			for( int freq : ixreader.getTermFreqVector(docid, t).getTermFrequencies() ){
				doclen = doclen + freq;
			}
			TermResult tr=new TermResult();
			tr.setWord(w);
			 tr.setDocID(docid);
			tr.setDocNum(docno);
			tr.setLength(doclen);
			tr.setTermFreq(score);// idf
			//System.out.printf("%-10s  %-30s  %-6d  %-6d\n", docid, docno, tf, doclen);
			termResults.add(tr);
		}
		}
		// Lucene does not store total frequency of a term in the whole index.
		// so, if you want to implement language model and smoothing, you probably need to calculate it by your self.
		
		// remeber to close it.
		ixreader.close();
		ixdir.close();
		return termResults;
		
	
		
		
		
	}
	static String getStem(String word, Analyzer analyzer) throws IOException {
		String stem = null;
		TokenStream ts = analyzer.tokenStream("name", new StringReader(word));
		CharTermAttribute attr = ts.addAttribute(CharTermAttribute.class);
		if(ts.incrementToken()){
			stem = attr.toString();
		}
		ts.close();
		return stem;
	}
}

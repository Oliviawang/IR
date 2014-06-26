package GAPI;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import java.util.Map;

import org.apache.lucene.util.Version;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import edu.pitt.sis.squirrel.dataset.TrecTextSource;

/**
 * <p>
 * This is a tutorial for building Lucene index.
 * </p>
 * <p>
 * To run the program in command line mode, parameters should be:
 * </p>
 * <ul>
 * <li>args[0]: path of the zip collection file.</li>
 * <li>args[1]: path of the index (a directory).</li>
 * <ul>
 */
public class buildIndex {
	
	public static void main(String[] args){
		try{
			
			// check parameters
		//	if(args==null||args.length!=2||args[0]==null||args[1]==null){
			//	System.out.println("Invalid parameters!");
				//System.out.println("To run the program in command line mode, parameters should be:");
				//System.out.println("\targs[0]: path of the collection file.");
				//System.out.println("\targs[1]: path of the index (a directory).");
				//System.out.println("Also, make sure that args[0] is the provided collection in this tutorial.");
				//System.exit(-1);
			//}
			
			// check the source file
			
			File pathSrc = new File("D:\\workspace\\eclipse\\IR\\WebContent\\docset1.txt");
			if(!pathSrc.exists()){
				System.out.println("The specified collection file does not exist: "+pathSrc);
			}
			
			// Now initializing the lucene index writer ....
			
			// The first step is to create a directory instance in lucene
			Directory ixdir = FSDirectory.open(new File("D:\\workspace\\eclipse\\IR\\WebContent\\build.index"));
			// Now ixdir is an abstract representation for the index directory
			
			// Now you can continue to create an index writer on this directory
			IndexWriterConfig ixconfig = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			IndexWriter ixwriter = new IndexWriter(ixdir, ixconfig);
			// new StandardAnalyzer(Version.LUCENE_35) will give you a standard lucene text analyzer, which can tokenize text units
			// we will further explain how to use other text analyzers in TutorialAnalyzer.
			
			// create a TrecTextSource to read the collection file (docset1.txt).
			InputStream instream = new FileInputStream(pathSrc);
			TrecTextSource collection = new TrecTextSource(instream);
			
			// Now enumerating the collection for each document
			Map<String,String> document = null;
			while( (document=collection.next())!=null ){
				
				String docno = document.get("DOCNO"); // get file name of each zip entry, e.g. VOA20001008.2100.1363
				String content = document.get("DOC"); // get content of the document
				System.out.println(" >> Indexing "+docno);
				
				// Now, create a lucene index Document instance for each document to be index
				Document doc = new Document();
				// This is an empty document now
				
				// Each document can contain several fields
				// A field can have a field name, and set with some content
				// You can specify whether to index and store the field
				Field fdDocid = new Field("DOCNO", docno, Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.NO); // This is a field for DOCNO, which will be stored in the index (so we can later get docid for each document), but this field will not be indexed (so it cannot be searched).
				Field fdContent = new Field("TEXT", content, Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS); // This is a field for the content of the entry, which will not be stored (so you cannot get the original content after indexing), but this field will be indexed (so that we can search for this document with some query).
				
				doc.add(fdDocid); // Now adding this field to the document
				doc.add(fdContent); // Now adding this field to the document
				
				// Now you can add the document to the index
				ixwriter.addDocument(doc);
				
			}
			
			instream.close();
			
			ixwriter.close(); // close the indexwriter
			
			ixdir.close(); // close the index directory, so that the file lock will be released
			
			// Now you have added all the files into the index.
			// Go to the index directory you have specified and have a look at what is created in that directory.
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}

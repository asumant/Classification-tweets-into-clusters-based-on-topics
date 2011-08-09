import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class preprocess{
	private File dirname;
	private String dirlocation;
	private String[] listOfFiles;
	private SAXParserDM sxparser;
	private FileWriter fstream;
	//private File csv_file;
	private BufferedWriter out;
	
	/* parsing xml, removing extra carriage returns and removing retweets */
	public preprocess(String path)
	{
		dirlocation = path;
		dirname = new File(path);
		if(dirname == null)
			System.out.println("Error in finding directory!");
		else{
			try{
				fstream = new FileWriter("tweet_test.csv");
				out = new BufferedWriter(fstream);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			sxparser = new SAXParserDM(out);
		}
	}
	
	public void process_dataset_directory()
	{		
		listOfFiles = dirname.list();
		
		for(int i=0 ; i< listOfFiles.length ; i++)
		{
			sxparser.parseDocument(dirlocation+"/"+listOfFiles[i]);
			
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		preprocess p = new preprocess("test");
		p.process_dataset_directory();
		
	}
}
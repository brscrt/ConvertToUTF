package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileOperations implements File_IO{

	@Override
	public boolean fileRead(File file) {
		boolean success=false;
		FileReader in;
		BufferedReader reader;
		try {
			in = new FileReader(file);
			reader=new BufferedReader(in);
			String buffer=reader.readLine();
			while(buffer!=null){
				changeCharacters(buffer);
				buffer=reader.readLine();
			}
			reader.close();
			in.close();
		} catch (FileNotFoundException e) {	
			
			return success;
		} catch (IOException e) {
			e.printStackTrace();
			return success;			
		}
		
		return success;
	}

	@Override
	public boolean fileWrite(String path) {
		boolean success=false;
		return success;
	}
	
	private String changeCharacters(String sentence){
		char[] charsAnsi = {'ý','Ý','þ','ð','Þ'};
		char[] charsUtf = {'ı','İ','ş','ğ','Ş'};
			for(int j=0;j<charsAnsi.length;j++){
					sentence=sentence.replace(charsAnsi[j], charsUtf[j]);					
			}
		return sentence;
	}
	public boolean convert(File source,File destination){
		//FileReader in;
		//FileWriter out;
		
		BufferedReader reader;
		PrintWriter writer;
		
		try {
			//in=new FileReader(source);
			FileInputStream inFile=new FileInputStream(source);
			InputStreamReader inStream=new InputStreamReader(inFile);				
			reader=new BufferedReader(inStream);
			//out=new FileWriter(destination);			
			
			String buffer=reader.readLine();
			ArrayList<String> lines=new ArrayList<String>();
			boolean UTF=true;
			while(buffer!=null&&UTF){
				if(buffer.contains("Å¸")||buffer.contains("Ãƒ"))
					UTF=false;
				lines.add(changeCharacters(buffer));
				buffer=reader.readLine();
			
			}
			if(!UTF){
				lines.clear();
				reader.close();	
				inStream.close();
				inFile.close();
				inFile=new FileInputStream(source);
				inStream=new InputStreamReader(inFile,"UTF-8");				
				reader=new BufferedReader(inStream);
				buffer=reader.readLine();
				while(buffer!=null){
					lines.add(changeCharacters(buffer));
					buffer=reader.readLine();				
				}
			}
			reader.close();	
			inStream.close();
			inFile.close();
			//in.close();
			
			writer=new PrintWriter(destination,"UTF-8");
			for(String line:lines){
				writer.println(line);
			}
			writer.close();
			
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


}

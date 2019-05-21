package netty.protocol;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import util.config;

public class hello {

	public static void main(String[] args) throws IOException {
		
		String Path = "C:\\Users\\Role\\Desktop\\otherResponse";
		File file = new File(Path);
		File[] files = file.listFiles();
		int sum=0;
		int needchange = 1;
		for(int i=0;i<files.length;++i) {
			File f = files[i];
			String content = "";
			FileReader fileReader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = "";
			String temp = "";
			
			while((line=bufferedReader.readLine())!=null) {
				if(line.startsWith("package netty")||line.startsWith("package  netty")) {
					needchange=0;
					break;
				}
				
				//System.out.println(line);
				if(line.startsWith("package")||line.startsWith("import")) {
					int index1 = line.indexOf("the");
					int index2 = line.indexOf("netty");
					if(index1==-1||index2==-1) {
						continue;
					}
					line = line.substring(0,index1)+line.substring(index2,line.length());
				}
				line+="\n";
				content+=line;
			}
			bufferedReader.close();
			fileReader.close();
			
			if(needchange==0) {
				needchange=1;
				continue;
			}
			FileWriter fileWriter = new FileWriter(f);
			fileWriter.write(content);
			fileWriter.close();
			++sum;
			
		}
		System.out.println(sum);
	}
	
}

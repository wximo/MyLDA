package Ximo.Lda.DataProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataPreprocess {
	static deletedWords dw=new deletedWords();
	public static void textLowercase(ArrayList<String> textsAll){
		for(int i=0;i<textsAll.size();i++){
			textsAll.set(i, textsAll.get(i).toLowerCase().replace(" ", ""));
		}
	}
	
	public static void textCut(String path) throws IOException, InterruptedException{
		 //use python to cut texts and save in the marked files.
		//there the path is e:\\123... and can not be changed,we can restored the files from path that given to e:\\123.. and when python done
		//restore those files that builded by python back
		try {
			   System.out.println("start data cut");
			 //  Process pr = Runtime.getRuntime().exec("python C:\\Users\\Administrator.Y26OC25YCRAPBLH\\workspace\\MyLDA\\data\\rawall.py");
			   Process pr=Runtime.getRuntime().exec("python /host/Users/Administrator.Y26OC25YCRAPBLH/workspace/MyLDA/data/raw.py");
			   pr.waitFor(); 
			   System.out.println("finished\n");
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
	}
	
	public static void textDeleteNoise(ArrayList<String> textsAll,ArrayList<ArrayList<String> > textsIndeped){
		//use noise marked data
		//sort and delete blocks and other noise data like punctuations
		//also a function which split string to string[] 
		
		System.out.println("start delete noise or stopwords and other punctustions");
                                                                                      
		for(int i=0;i<textsAll.size();i++){
			ArrayList<String> t=new ArrayList<String>();
			t.addAll(Arrays.asList(textsAll.get(i).split(" ")));
			textLowercase(t);
			t.removeAll(dw.del);
			for(int j=0;j<t.size();j++){
				if(t.get(j).matches("[0-9]+")){
					t.remove(j);j--;
				}
			}
			textsIndeped.add(t);
		}
		
		System.out.println("finished\n");
		
	}
	
	//may not be used,python had done this
	public static void deleteLFwords(ArrayList<ArrayList<String> > textsIndeped){
		System.out.println("deleting LFwords\n");
		Map<String,Integer> m=new HashMap<String,Integer>();
		
		for(int i=0;i<textsIndeped.size();i++){
			for(int j=0;j<textsIndeped.get(i).size();j++){
				if(m.containsKey(textsIndeped.get(i).get(j))){
					m.put(textsIndeped.get(i).get(j), m.get(textsIndeped.get(i).get(j))+1);
				}else{
					m.put(textsIndeped.get(i).get(j), 1);
				}
			}
		}
		for(int i=0;i<textsIndeped.size();i++){
			for(int j=0;j<textsIndeped.get(i).size();j++){
				if(m.get(textsIndeped.get(i).get(j))<10){
					textsIndeped.get(i).remove(j);
					j--;
				}
			}
		}
		System.out.println("finished\n");
	}
	
	
}

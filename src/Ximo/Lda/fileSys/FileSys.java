package Ximo.Lda.fileSys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import Ximo.Lda.AlgoPart.LdaData;



public class FileSys {
	public static void readFiles(String path, ArrayList<String> lines,ArrayList<String> fileName) {
		System.out.println("start read files");
		BufferedReader reader = null;
		File fo=new File(path);
		if(fo.isDirectory()){		
			String[] filelist=fo.list();
			for(int i=0;i<filelist.length;i++){
				if(filelist[i].contains("txt")){
					String file=path+"/"+filelist[i];
					//String file=path+"\\"+filelist[i];
					fileName.add(filelist[i]);
				try {
					//reader = new BufferedReader(new FileReader(new File(file)));
					reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
					String line = null;
					String lin="";
					while ((line = reader.readLine()) != null) {
						line=line.toLowerCase();	
						lin=lin+line;
					}
					lines.add(lin);
					} catch (FileNotFoundException e) {
							e.printStackTrace();
					} catch (IOException e) {
							e.printStackTrace();
					} finally {
							if (reader != null) {
									try {
											reader.close();
										} catch (IOException e) {
												e.printStackTrace();
										}
												}
								}
			
			}}
			}
		System.out.println("finished\n");
	}
	
	public static void deleteLines(String path) {
		System.out.println("--------------------------start delete files--------------------------");
		System.out.println("---------------------------------ing----------------------------------");
		//BufferedReader reader = null;
		File fo=new File(path);
		if(fo.isDirectory()){		
			String[] filelist=fo.list();
			for(int i=0;i<filelist.length;i++){
				if(filelist[i].contains("after")){
					String file=path+"/"+filelist[i];
					//String file=path+"\\"+filelist[i];
					File de=new File(file);
					if(de.isFile()){
						de.delete();
					}
				}
			}
		System.out.println("-----------------------files deleting finished------------------------\n");
		}
}
	
	public static void saveLda(String path,LdaData ld){
		
		String p1=path+"-phi-topic-word-All.txt";
		String p2=path+"-theta-doc-topic-All.txt";
		//String p3=path+"-parameters.txt";
		
		//some others have to be saved

		//phi K*V	
		try {
			File f=new File(p1);
			if(!f.exists()){
				f.createNewFile();
			}
			OutputStreamWriter write=new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			BufferedWriter writer=new BufferedWriter(write);
			//TreeMap<Double,Integer> tmphi=new TreeMap<Double,Integer>(new descendComparator());//�ظ���key�޷����
			TreeMap<Double,ArrayList<Integer> >tmphi=new TreeMap<Double,ArrayList<Integer> >(new descendComparator());
			for(int i=0;i<ld.getK();i++){
				for(int j=0;j<ld.getV();j++){
					if(tmphi.containsKey(ld.getPhi()[i][j])){
						ArrayList<Integer> a=new ArrayList<Integer>();
						a=tmphi.get(ld.getPhi()[i][j]);
						a.add(j);
						tmphi.put(ld.getPhi()[i][j], a);
					}else{
						ArrayList<Integer> a=new ArrayList<Integer>();
						//a=tmphii.get(ld.phi[i][j]);
						a.add(j);
						tmphi.put(ld.getPhi()[i][j], a);
					}
					//tmphi.put(ld.phi[i][j],j);
				}
				Set<Double> keys = tmphi.keySet();
				Iterator<Double> it = keys.iterator();
				writer.write("topic "+String.valueOf(i)+" : "+"\r\n");
				while(it.hasNext()){
					double a = (double) it.next();
					for(int j=0;j<tmphi.get(a).size();j++){
						writer.write(" "+a+":"+ld.getWordsAll().get(tmphi.get(a).get(j))+"\r\n");
					}
					//writer.write(" "+a+":"+ld.wordsAll.get(tmphi.get(a))+"\r\n");
				}
				tmphi.clear();
				writer.write("\r\n");
			}
			writer.close();} catch (Exception e) {
							e.printStackTrace();
					}

	//theta M*K
			try {
				File f=new File(p2);
				if(!f.exists()){
					f.createNewFile();
				}
				OutputStreamWriter write=new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
				BufferedWriter writer=new BufferedWriter(write);
				//TreeMap<Double,Integer> tmtheta=new TreeMap<Double,Integer>(new descendComparator());
				TreeMap<Double,ArrayList<Integer> >tmtheta=new TreeMap<Double,ArrayList<Integer> >(new descendComparator());
				for(int i=0;i<ld.getM();i++){
					for(int j=0;j<ld.getK();j++){
						if(tmtheta.containsKey(ld.getTheta()[i][j])){
							ArrayList<Integer> a=new ArrayList<Integer>();
							a=tmtheta.get(ld.getTheta()[i][j]);
							a.add(j);
							tmtheta.put(ld.getTheta()[i][j], a);
						}else{
							ArrayList<Integer> a=new ArrayList<Integer>();
							//a=tmphii.get(ld.phi[i][j]);
							a.add(j);
							tmtheta.put(ld.getTheta()[i][j], a);
						
						//tmtheta.put(ld.theta[i][j],j);
					}}
					Set<Double> keys = tmtheta.keySet();
					Iterator<Double> it = keys.iterator();
					writer.write("doc "+ld.getFileName().get(i)+" : "+"\r\n");
					while(it.hasNext()){
						 double a = (double) it.next();
						 for(int j=0;j<tmtheta.get(a).size();j++){
								writer.write(" "+a+":"+tmtheta.get(a).get(j)+"\r\n");
							}						 
						// writer.write(" "+a+":"+tmtheta.get(a)+"\r\n");					      
					}
					tmtheta.clear();
					writer.write("\r\n");
				}
				writer.close();} catch (Exception e) {
								e.printStackTrace();
						}		
		}
	
	
	
	
	}
	
	class descendComparator implements Comparator<Object>
	{
	    public int compare(Object o1,Object o2)
	    {
	        Double i1=(Double)o1;
	        Double i2=(Double)o2;
	        return -i1.compareTo(i2);
	    }
	}
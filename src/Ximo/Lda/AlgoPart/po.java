package Ximo.Lda.AlgoPart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class po{
	public ArrayList<String> c=new ArrayList<String>();
	public ArrayList<Double> cc=new ArrayList<Double>();
	public ArrayList<Integer> ccc=new ArrayList<Integer>();
	public TreeMap<Integer,Double> d=new TreeMap<Integer,Double>(new descendComparator1());
	public void sortt(int k){
		for(int i=0;i<k;i++)
			d.put(i, 0.0);
		for(int i=0;i<ccc.size();i++)
			d.put(ccc.get(i), cc.get(i));
	}
	public void out(){
		for(int i=0;i<c.size();i++){
			System.out.println(c.get(i)+" "+cc.get(i));
		}
		System.out.println(ccc);
	}
}
class descendComparator1 implements Comparator<Object>
{
    public int compare(Object o1,Object o2)
    {
        Integer i1=(Integer)o1;
        Integer i2=(Integer)o2;
        return -i1.compareTo(i2);
    }
}
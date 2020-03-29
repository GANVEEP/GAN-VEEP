package makevehicletrace;

import java.util.TreeMap;

public class timestatistic {
    
	double time;
	TreeMap<Integer, vehiclelane> vehiclelanelist=new TreeMap<Integer, vehiclelane>();
	
	public timestatistic(){
		
		for(int i=0;i<getlaneinformation.n;i++){
			vehiclelanelist.put(i, new vehiclelane(i));
			
		}
	}

}

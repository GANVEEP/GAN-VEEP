package makevehicletrace;

import java.util.TreeMap;

public class density {

	public static void main(String[] args) throws Exception{
		String realpath="D:\\0104mobility.tcl";
		TreeMap<Integer, timestatistic> realtreeMap=getlaneinformation.getinfor("D:\\vehiclerange.txt", realpath,"D:\\12point.txt");

		
	}

}

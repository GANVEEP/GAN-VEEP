package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;

public class tracetotcl {

	public static void main(String[] args) throws Exception{
		String line;
		String[] d=new String[100];
		String path="C:\\SUMO\\bin\\test\\newh\\0104trace.xml";
		String newpath="C:\\SUMO\\bin\\test\\newh\\0104mobility.tcl";
		String time="",id,x,y,speed;
		String prints="";
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			if(d.length==6){
				time=d[5];
			}
			if(d.length==18){
				id=d[9];
				x=d[10];
				y=d[11];
				speed=d[14];
//				System.out.print(time);
//				System.out.print(id);
//				System.out.print(x);
//				System.out.print(y);
//				System.out.println(speed);
				prints="$ns_ at "+time.split("\"")[1]+" \"$node_("+id.split("\"")[1]+") setdest "+x.split("\"")[1]+" "+y.split("\"")[1]+" "+speed.split("\"")[1]+"\"";
				System.out.println(prints);
				printdata.method(newpath, prints);
			}
			
//            System.out.print(line);
//            System.out.println("  "+d.length);
		}

	}

}

package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeMap;

import makevehicletrace.getinfobylane;
import makevehicletrace.getlaneinformation;
import makevehicletrace.timestatistic;
import makevehicletrace.vehicle;

public class everyroad {

	public static void everyroadvolume(String numpath,String speedpath,timestatistic ti)throws Exception{
//		System.out.println(numpath);
		for(int i=0;i<ti.vehiclelanelist.size();i++){
			double totalspeed=0;
			String s="";
			s=i+" "+ ti.vehiclelanelist.get(i).vehiclelist.size();//输出每条道路上的车辆数量
//			System.out.println("数量为："+s);
			printdata.method(numpath, s);
//			System.out.println(numpath+"  "+s);
			for (Integer m : ti.vehiclelanelist.get(i).vehiclelist.keySet()) {
				vehicle v=ti.vehiclelanelist.get(i).vehiclelist.get(m);
				totalspeed=totalspeed+v.speed;
//				System.out.println(v.id+"  "+v.speed);
			}
			totalspeed=totalspeed/ ti.vehiclelanelist.get(i).vehiclelist.size();
			if(ti.vehiclelanelist.get(i).vehiclelist.size()==0){
				totalspeed=0;
			}
			s=i+" "+String.format("%.2f", totalspeed);
			printdata.method(speedpath, s);
		}
	}
public static void everyroadvolume(String numpath,String speedpath,timestatistic oriti,timestatistic endti)throws Exception{
		//自适应方法
	    double orinum,endnum;
		for(int i=0;i<oriti.vehiclelanelist.size();i++){
			double realtotalspeed=0,pretotalspeed=0;
			String s="";
//			s=i+" "+ oriti.vehiclelanelist.get(i).vehiclelist.size();//输出每条道路上的车辆数量
			s=i+" "+(oriti.vehiclelanelist.get(i).vehiclelist.size()+endti.vehiclelanelist.get(i).vehiclelist.size()*0.3)/2;
			System.out.println("ori："+oriti.vehiclelanelist.get(i).vehiclelist.size());
			System.out.println("end："+endti.vehiclelanelist.get(i).vehiclelist.size());
			System.out.println("total："+(oriti.vehiclelanelist.get(i).vehiclelist.size()+endti.vehiclelanelist.get(i).vehiclelist.size()*0.8)/2);
			printdata.method(numpath, s);
			for (Integer m : oriti.vehiclelanelist.get(i).vehiclelist.keySet()) {
				vehicle endv=endti.vehiclelanelist.get(i).vehiclelist.get(m);
				vehicle v=oriti.vehiclelanelist.get(i).vehiclelist.get(m);
					realtotalspeed=realtotalspeed+v.speed;			
			}
			for (Integer m : endti.vehiclelanelist.get(i).vehiclelist.keySet()) {
				vehicle endv=endti.vehiclelanelist.get(i).vehiclelist.get(m);
				vehicle v=oriti.vehiclelanelist.get(i).vehiclelist.get(m);
					pretotalspeed=pretotalspeed+endv.speed;			
			}
			realtotalspeed=realtotalspeed/oriti.vehiclelanelist.get(i).vehiclelist.size();
			pretotalspeed=pretotalspeed/ endti.vehiclelanelist.get(i).vehiclelist.size();
			
			pretotalspeed=0.5*(realtotalspeed+pretotalspeed*0.5);
			if(oriti.vehiclelanelist.get(i).vehiclelist.size()==0){
				pretotalspeed=0;
			}
			s=i+" "+String.format("%.2f", pretotalspeed);
			printdata.method(speedpath, s);
		}
	}
	public static void getvehiclespeed(String realpath,String prepath,String newpath)throws Exception{
		FileReader realreader = new FileReader(realpath);
		BufferedReader realbr = new BufferedReader(realreader);
		FileReader prereader = new FileReader(prepath);
		BufferedReader prebr = new BufferedReader(prereader);
		String realline,preline;
		String[] realb,preb;
		String s;
		while ((realline = realbr.readLine()) != null) {
			preline = prebr.readLine();
			realb=realline.split(" ");
			preb=preline.split(" ");
			s=realb[0]+" "+realb[1]+" "+preb[1];
			printdata.method(newpath, s);
		}
		realbr.close();
		prebr.close();
	}
	public static void getvehicleinfo(String numpath,String speedpath,String newpath)throws Exception{
		FileReader numreader = new FileReader(numpath);
		BufferedReader numbr = new BufferedReader(numreader);
		FileReader speedreader = new FileReader(speedpath);
		BufferedReader speedbr = new BufferedReader(speedreader);
		String numline,speedline;
		String[] numb,speedb;
		String s;
		while ((numline = numbr.readLine()) != null) {
			speedline = speedbr.readLine();
			numb=numline.split(" ");
//			System.out.println(speedline);
			speedb=speedline.split(" ");
			s=numb[0]+" "+numb[1]+" "+speedb[1];
			printdata.method(newpath, s);
		}
		numbr.close();
		speedbr.close();
	}
	public static void gettrajectory(String realpath,String newpath,int number)throws Exception{
		FileReader realreader = new FileReader(realpath);
		BufferedReader realbr = new BufferedReader(realreader);
		String realline,preline;
		String[] realb,preb;
		String s;
		int time;
		String vehicle;
		int num=46;
		while ((realline=realbr.readLine()) != null) {
			realb=realline.split(" ");
			time=(int)Double.parseDouble(realb[2]);
			vehicle="\"$node_("+number+")";
			if(time==num&&realb[3].equals(vehicle)){
				printdata.method(newpath, realb[5]+" "+realb[6]+" "+realb[7]);
				num++;
			}
		
		}
	}
	public static void main(String[] args) throws Exception{
		String prepath="D:\\vehiclelane1\\formaldata\\65s开始\\mobility.tcl";
		String realpath="D:\\0104mobility.tcl";
		String prevehiclenum="D:\\vehiclelane1\\evaluation\\prevehiclenum.txt";
		String realvehiclenum="D:\\vehiclelane1\\evaluation\\realvehiclenum.txt";
		String prevehiclespeed="D:\\vehiclelane1\\evaluation\\prevehiclespeed.txt";
		String realvehiclespeed="D:\\vehiclelane1\\evaluation\\realvehiclespeed.txt";
		
		String vehiclenum="D:\\vehiclelane1\\evaluation\\vehiclenum.txt";
		String vehiclespeed="D:\\vehiclelane1\\evaluation\\vehiclespeed.txt";
TreeMap<Integer, timestatistic> pretreeMap=getlaneinformation.getinfor("D:\\vehiclerange.txt", prepath,"D:\\12point.txt");

       
timestatistic prti=pretreeMap.get(95); 
//timestatistic oripreti=realtreeMap.get(65); 
everyroad.everyroadvolume(prevehiclenum,  prevehiclespeed, prti);
//System.out.println(prti.vehiclelanelist.get(0).vehiclelist.get(50).speed);
//everyroad.everyroadvolume(prevehiclenum, prevehiclespeed, oripreti,preti);
TreeMap<Integer, timestatistic> realtreeMap=getlaneinformation.getinfor("D:\\vehiclerange.txt", realpath,"D:\\12point.txt");

        timestatistic realti=realtreeMap.get(95);
		everyroad.everyroadvolume(realvehiclenum,  realvehiclespeed, realti);
        everyroad.getvehicleinfo(realvehiclenum, prevehiclenum, vehiclenum);
        everyroad.getvehiclespeed(realvehiclespeed, prevehiclespeed, vehiclespeed);//宏观评价指标
//        String realnewpath,prenewpath;
//        for(int i=0;i<300;i++){
//        	System.out.println(i+"begin");
//        	realnewpath="D:\\vehiclelane1\\evaluation\\realeveryvehicle\\"+i+".txt";
//        	prenewpath="D:\\vehiclelane1\\evaluation\\preeveryvehicle\\"+i+".txt";
//            everyroad.gettrajectory(realpath,realnewpath, i);
//            everyroad.gettrajectory(prepath, prenewpath, i);
//        }

	}

}

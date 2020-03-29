package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import jdk.internal.dynalink.beans.StaticClass;



public class getlaneinformation {
	
	//static  List<TreeMap<Double, timestatistic>> treeMapArray;
	static TreeMap<Integer, timestatistic> treeMapArray=new TreeMap<Integer, timestatistic>();
	static int n=24;//车道数量
	static int snum=400;
	static boolean vehiclelanenumbertrue=false;//此变量为真时代表车辆道路编号数组启用
	static int[] vehiclelanenumber=new int[1000];//车辆道路编号数组
	public static TreeMap<Integer, timestatistic> getinfor(String pointpath,String tracepath,String point12path)throws Exception{

		TreeMap<Integer, vehiclelane> maintasklist=new TreeMap<Integer, vehiclelane>();//这里放的是24条道路的标点坐标
		double[][] data=new double[200][200];
		String[] d=new String[20]; 
		String[] d2=new String[20]; 
		String d1;
		FileReader reader = new FileReader(pointpath);
		BufferedReader br = new BufferedReader(reader);//车道四个点的文件
		FileReader reader2 = new FileReader(point12path);
		BufferedReader br2 = new BufferedReader(reader2);//车道起点和终点的文件
		String line;
		String line1;
		vehiclelane v1;
		int a=0;
		while ((line = br.readLine()) != null) {
		 	v1=new vehiclelane(0); 
            d=line.split(" ");
            line1=br2.readLine();
            d2=line1.split(" ");
            //System.out.println(line);
//            System.out.println(d[0]);
            v1.vehicleposition[0]=Float.parseFloat(d[0]);
            v1.vehicleposition[1]=Float.parseFloat(d[1]);
            v1.vehicleposition[2]=Float.parseFloat(d[2]);
            v1.vehicleposition[3]=Float.parseFloat(d[3]);
            v1.vehicleposition[4]=Float.parseFloat(d[4]);
            v1.vehicleposition[5]=Float.parseFloat(d[5]);
            v1.vehicleposition[6]=Float.parseFloat(d[6]);
            v1.vehicleposition[7]=Float.parseFloat(d[7]); 
            
			v1.oriPoint.x=Double.parseDouble(d2[0]);
			v1.oriPoint.y=Double.parseDouble(d2[1]);
			v1.endPoint.x=Double.parseDouble(d2[2]);
			v1.endPoint.y=Double.parseDouble(d2[3]);
           // System.out.println(v1.vehicleposition[7]);
            maintasklist.put(a, v1);//把这几条路的标点坐标放在列表中
            a=a+1;
//            id=Integer.parseInt(d1);
//            x=Double.parseDouble(d[5]);
//            y=Double.parseDouble(d[6]);
//            time=Double.parseDouble(d[2]);
        }
		br2.close();
		br.close();
		for(int i=0;i<maintasklist.size();i=i +1){
			 
//			System.out.println(maintasklist.get(i).vehicleposition[0]);
		}
//		System.out.println("读取坐标完成");
		//上面是读取车道信息的，下面是读取车辆轨迹信息，并且生成车道上车辆的数量文件
		
		for(int i=0;i<snum;i=i+1){//初始化了一个400秒的列表
			treeMapArray.put(i, new timestatistic());  
		} 
		String[] ss=new String[20];
		FileReader reader1 = new FileReader(tracepath);
		BufferedReader br1 = new BufferedReader(reader1);

		point[] point24=new point[30];//里面包含了这24条道路的终点坐标，用于计算每辆车离路口的距离
		int pointnum=0;
		for(int i=0;i<point24.length;i++){//自定义类型数组初始化
			point24[i]=new point();
		}
		FileReader reader3 = new FileReader(point12path);
		BufferedReader br3 = new BufferedReader(reader3);
		while ((line = br3.readLine()) != null) {
			ss=line.split(" ");
//			System.out.println(ss[0]+" "+ss[1]+" @@@");
//			System.out.println(point24.length);
			point24[pointnum].x=Double.parseDouble(ss[2]);
			point24[pointnum].y=Double.parseDouble(ss[3]);
			pointnum=pointnum+1;
		}
		br3.close();
		double time1=0.0;
		double clock;
		double x;
		double y;
		double speed;
		int roadid;
		double rid;
		int id;
		while ((line = br1.readLine()) != null) {
			ss=line.split(" ");
//			System.out.println(line);
			if(ss[0].equals("$ns_")){
				clock=Double.parseDouble(ss[2]);//时间
				id=Integer.parseInt(ss[3].split("\\(")[1].split("\\)")[0]);
				x=Double.parseDouble(ss[5]);
				y=Double.parseDouble(ss[6]);
				speed=Double.parseDouble(ss[7].split("\\\"")[0]);
				vehicle avehicle=new vehicle(x,y,speed,id);
				avehicle.time=clock;
				for(int i=0;i<snum;i++){//此循环单位是秒
					if(clock-i==0){
						if(getlaneinformation.vehiclelanenumbertrue){	
//							System.out.println(line);
							rid=Double.parseDouble(ss[8]);
							roadid=(int)rid;
							float[] v=maintasklist.get(roadid).vehicleposition;
								double tolukou=changeXY.returndistance(new point(avehicle.x,avehicle.y), point24[roadid]);
								avehicle.tolukou=tolukou;
								String printpathString;
								String printpathString1;
								printpathString="D:\\vehiclelane\\"+roadid+"vehiclelane.txt";
								printpathString1="D:\\vehiclelane\\everyvehicle\\"+avehicle.id+"vehiclelane.txt";
								avehicle.v=v;
								avehicle.lannumber=roadid;
								double distance=getlaneinformation.getpointtoline(maintasklist.get(roadid).oriPoint, maintasklist.get(roadid).endPoint, new point(avehicle.x,avehicle.y));
								if(distance>0&&distance<3.25){
									avehicle.laneseq=1;
								}
								else if(distance>3.25&&distance<6.5){
									avehicle.laneseq=2;
								}
								else{
									
								}
								treeMapArray.get(i).vehiclelanelist.get(roadid).vehiclelist.put(avehicle.id, avehicle);  //把车辆信息加入列表 

//								System.out.println(treeMapArray.get(i).vehiclelanelist.get(roadid).vehiclelist.size());
						}
						else{
						for(int j=0;j<maintasklist.size();j++){//此循环单位是车道 
							float[] v=maintasklist.get(j).vehicleposition;
							if(isinside.isInside(v[0], v[1], v[2], v[3], v[6], v[7], v[4], v[5], x, y)){
								double tolukou=changeXY.returndistance(new point(avehicle.x,avehicle.y), point24[j]);
								avehicle.tolukou=tolukou;
								String printpathString;
								String printpathString1;
								printpathString="D:\\vehiclelane\\"+j+"vehiclelane.txt";
								printpathString1="D:\\vehiclelane\\everyvehicle\\"+avehicle.id+"vehiclelane.txt";
								avehicle.v=v;
								avehicle.lannumber=j;
								double distance=getlaneinformation.getpointtoline(maintasklist.get(j).oriPoint, maintasklist.get(j).endPoint, new point(avehicle.x,avehicle.y));
								if(distance>0&&distance<3.25){
									avehicle.laneseq=1;
								}
								else if(distance>3.25&&distance<6.5){
									avehicle.laneseq=2;
								}
								else{
									
								}
								treeMapArray.get(i).vehiclelanelist.get(j).vehiclelist.put(avehicle.id, avehicle);  //把车辆信息加入列表 

								break; 
								
							}

							
						}
						}
					}
				}
			}
			else{
				continue;
			}
		}
		
//		for(int i=0;i<22;i++){
//			//System.out.println(treeMapArray.get(0).vehiclelanelist.get(0).vehiclelist.get(i).id);
//		}
		getlaneinformation.getnum(treeMapArray);
		getlaneinformation.getdistancefromcar(treeMapArray);
		
		for(int i=0;i<getlaneinformation.snum;i++){//每秒钟
			timestatistic ti=treeMapArray.get(i);
//			if(i==30){
//				System.out.println(ti.vehiclelanelist.get(10).vehiclelist.get(10).speed);
//				break;
//			}
			for(int j=0;j<ti.vehiclelanelist.size();j++){//每条道路
				
				for (Integer m : ti.vehiclelanelist.get(j).vehiclelist.keySet()) {//每辆车
					//System.out.println(ti.vehiclelanelist.get(j).vehiclelist.get(m));
						vehicle avehicle=ti.vehiclelanelist.get(j).vehiclelist.get(m);
						String printpathString="D:\\vehiclelane1\\"+j+"vehiclelane.txt";
						//System.out.println("  @@@ "+avehicle.speed);
						String printpathString1="D:\\vehiclelane1\\everyvehicle\\"+avehicle.id+"vehiclelane.txt";
						getinfobylane.pritinfobylane(printpathString, avehicle);
						getinfobylane.pritinfobyvehicle(printpathString1, avehicle);
					
				}
				
			}
		}
		return(treeMapArray);
	}
	static public void getdistancefromcar(TreeMap<Integer, timestatistic> treemap)throws Exception{
		DecimalFormat df=new DecimalFormat("#0.00");
		double distancefromcar=999;
		double frontcarspeed=0;//前车速
		double havelastcar=0;//是否在当前道路前方有行驶车辆
		for(int i=0;i<getlaneinformation.snum;i++){//每秒钟
			timestatistic ti=treemap.get(i);
			for(int j=0;j<ti.vehiclelanelist.size();j++){//每条道路
				for (Integer m : ti.vehiclelanelist.get(j).vehiclelist.keySet()) {//每辆车
					havelastcar=0;
					frontcarspeed=0;
					distancefromcar=999;
//				for(int m=0;m<ti.vehiclelanelist.size();m++){
					for (Integer n : ti.vehiclelanelist.get(j).vehiclelist.keySet()) {//对比每辆车
//					for(int n=0;n<ti.vehiclelanelist.size();n++){
						if(ti.vehiclelanelist.get(j).vehiclelist.get(m).id!=ti.vehiclelanelist.get(j).vehiclelist.get(n).id &&
								ti.vehiclelanelist.get(j).vehiclelist.get(m).tolukou>ti.vehiclelanelist.get(j).vehiclelist.get(n).tolukou &&
								ti.vehiclelanelist.get(j).vehiclelist.get(m).tolukou-ti.vehiclelanelist.get(j).vehiclelist.get(n).tolukou<distancefromcar &&
								ti.vehiclelanelist.get(j).vehiclelist.get(m).laneseq==ti.vehiclelanelist.get(j).vehiclelist.get(n).laneseq){
							//判断辆车ID不同并且m车路口距要大于N车路口距							
							distancefromcar=ti.vehiclelanelist.get(j).vehiclelist.get(m).tolukou-ti.vehiclelanelist.get(j).vehiclelist.get(n).tolukou;
							havelastcar=1;//代表前方有车
							frontcarspeed=ti.vehiclelanelist.get(j).vehiclelist.get(n).speed;//代表前方车辆的速度
						}
//						if(havelastcar!=0){
//							ti.vehiclelanelist.get(j).vehiclelist.get(m).tolastcar=999;//如果前边没有车，初始化为999
//							havelastcar=0;
//							frontcarspeed=0;
//						}
						
					}
					
					ti.vehiclelanelist.get(j).vehiclelist.get(m).tolastcar=Double.parseDouble(df.format(distancefromcar));
					ti.vehiclelanelist.get(j).vehiclelist.get(m).havelastcar=havelastcar;
					ti.vehiclelanelist.get(j).vehiclelist.get(m).frontcarspeed=frontcarspeed;
				}
				
//				distancefromcar=999;
			}
		}
		
		

	}
	//返回点到直线的距离
	public static double getpointtoline(point ori,point end,point v){//车道的起点，终点和车辆的坐标
		double distance=0;
		double a,b,c;
		a=end.y-ori.y;
		b=-(end.x-ori.x);
		c=ori.y*(end.x-ori.x)-ori.x*(end.y-ori.y);
		distance=(a*v.x+b*v.y+c)/(Math.sqrt(a*a+b*b));
		if(distance<0)
			distance=distance*(-1);
		return(distance);
	}
	static public void getnum(TreeMap<Integer, timestatistic> treemap)throws Exception{
		FileWriter fw = null;
		fw = new FileWriter("D:\\vehiclenum.txt", true);
		fw.write("时间	1车道	2车道	3车道	4车道	5车道	6车道	7车道	8车道	9车道	10车道	11车道	12车道	13车道	14车道	15车道	16车道	17车道	18车道	19车道	20车道	21车道	22车道	23车道	24车道	总数量");//这里向文件中输入结果123
        fw.write("\n");
		fw.flush();
		int[] num=new int[getlaneinformation.n];
		String ss=null;
		int lanenum=0;
		for(int i=0;i<treemap.size();i++){
			timestatistic t=treemap.get(i);   
			//System.out.println(t.vehiclelanelist.size());
			for(int j=0;j<t.vehiclelanelist.size();j++){
				vehiclelane vn=t.vehiclelanelist.get(j);
				num[j]=vn.vehiclelist.size();
				lanenum=lanenum+vn.vehiclelist.size();
			}
			ss=i+"		";
			for(int m=0;m<t.vehiclelanelist.size();m++){
				ss=ss+num[m]+"		";
			}
			ss=ss+lanenum;
			fw.write(ss);
			fw.write("\n");
			fw.flush();
			lanenum=0;
		}
		fw.close();
	}
	public static void main(String[] args)throws Exception{
		getlaneinformation.getinfor("D:\\vehiclerange.txt", "D:\\0104mobility.tcl","D:\\12point.txt");
		
//		double distance=getlaneinformation.getpointtoline(new point(0,0), new point(1,1), new point(0,1));
//		System.out.print(distance);//点到直线的距离测试
	}
}

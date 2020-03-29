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
	static int n=24;//��������
	static int snum=400;
	static boolean vehiclelanenumbertrue=false;//�˱���Ϊ��ʱ��������·�����������
	static int[] vehiclelanenumber=new int[1000];//������·�������
	public static TreeMap<Integer, timestatistic> getinfor(String pointpath,String tracepath,String point12path)throws Exception{

		TreeMap<Integer, vehiclelane> maintasklist=new TreeMap<Integer, vehiclelane>();//����ŵ���24����·�ı������
		double[][] data=new double[200][200];
		String[] d=new String[20]; 
		String[] d2=new String[20]; 
		String d1;
		FileReader reader = new FileReader(pointpath);
		BufferedReader br = new BufferedReader(reader);//�����ĸ�����ļ�
		FileReader reader2 = new FileReader(point12path);
		BufferedReader br2 = new BufferedReader(reader2);//���������յ���ļ�
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
            maintasklist.put(a, v1);//���⼸��·�ı����������б���
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
//		System.out.println("��ȡ�������");
		//�����Ƕ�ȡ������Ϣ�ģ������Ƕ�ȡ�����켣��Ϣ���������ɳ����ϳ����������ļ�
		
		for(int i=0;i<snum;i=i+1){//��ʼ����һ��400����б�
			treeMapArray.put(i, new timestatistic());  
		} 
		String[] ss=new String[20];
		FileReader reader1 = new FileReader(tracepath);
		BufferedReader br1 = new BufferedReader(reader1);

		point[] point24=new point[30];//�����������24����·���յ����꣬���ڼ���ÿ������·�ڵľ���
		int pointnum=0;
		for(int i=0;i<point24.length;i++){//�Զ������������ʼ��
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
				clock=Double.parseDouble(ss[2]);//ʱ��
				id=Integer.parseInt(ss[3].split("\\(")[1].split("\\)")[0]);
				x=Double.parseDouble(ss[5]);
				y=Double.parseDouble(ss[6]);
				speed=Double.parseDouble(ss[7].split("\\\"")[0]);
				vehicle avehicle=new vehicle(x,y,speed,id);
				avehicle.time=clock;
				for(int i=0;i<snum;i++){//��ѭ����λ����
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
								treeMapArray.get(i).vehiclelanelist.get(roadid).vehiclelist.put(avehicle.id, avehicle);  //�ѳ�����Ϣ�����б� 

//								System.out.println(treeMapArray.get(i).vehiclelanelist.get(roadid).vehiclelist.size());
						}
						else{
						for(int j=0;j<maintasklist.size();j++){//��ѭ����λ�ǳ��� 
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
								treeMapArray.get(i).vehiclelanelist.get(j).vehiclelist.put(avehicle.id, avehicle);  //�ѳ�����Ϣ�����б� 

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
		
		for(int i=0;i<getlaneinformation.snum;i++){//ÿ����
			timestatistic ti=treeMapArray.get(i);
//			if(i==30){
//				System.out.println(ti.vehiclelanelist.get(10).vehiclelist.get(10).speed);
//				break;
//			}
			for(int j=0;j<ti.vehiclelanelist.size();j++){//ÿ����·
				
				for (Integer m : ti.vehiclelanelist.get(j).vehiclelist.keySet()) {//ÿ����
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
		double frontcarspeed=0;//ǰ����
		double havelastcar=0;//�Ƿ��ڵ�ǰ��·ǰ������ʻ����
		for(int i=0;i<getlaneinformation.snum;i++){//ÿ����
			timestatistic ti=treemap.get(i);
			for(int j=0;j<ti.vehiclelanelist.size();j++){//ÿ����·
				for (Integer m : ti.vehiclelanelist.get(j).vehiclelist.keySet()) {//ÿ����
					havelastcar=0;
					frontcarspeed=0;
					distancefromcar=999;
//				for(int m=0;m<ti.vehiclelanelist.size();m++){
					for (Integer n : ti.vehiclelanelist.get(j).vehiclelist.keySet()) {//�Ա�ÿ����
//					for(int n=0;n<ti.vehiclelanelist.size();n++){
						if(ti.vehiclelanelist.get(j).vehiclelist.get(m).id!=ti.vehiclelanelist.get(j).vehiclelist.get(n).id &&
								ti.vehiclelanelist.get(j).vehiclelist.get(m).tolukou>ti.vehiclelanelist.get(j).vehiclelist.get(n).tolukou &&
								ti.vehiclelanelist.get(j).vehiclelist.get(m).tolukou-ti.vehiclelanelist.get(j).vehiclelist.get(n).tolukou<distancefromcar &&
								ti.vehiclelanelist.get(j).vehiclelist.get(m).laneseq==ti.vehiclelanelist.get(j).vehiclelist.get(n).laneseq){
							//�ж�����ID��ͬ����m��·�ھ�Ҫ����N��·�ھ�							
							distancefromcar=ti.vehiclelanelist.get(j).vehiclelist.get(m).tolukou-ti.vehiclelanelist.get(j).vehiclelist.get(n).tolukou;
							havelastcar=1;//����ǰ���г�
							frontcarspeed=ti.vehiclelanelist.get(j).vehiclelist.get(n).speed;//����ǰ���������ٶ�
						}
//						if(havelastcar!=0){
//							ti.vehiclelanelist.get(j).vehiclelist.get(m).tolastcar=999;//���ǰ��û�г�����ʼ��Ϊ999
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
	//���ص㵽ֱ�ߵľ���
	public static double getpointtoline(point ori,point end,point v){//��������㣬�յ�ͳ���������
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
		fw.write("ʱ��	1����	2����	3����	4����	5����	6����	7����	8����	9����	10����	11����	12����	13����	14����	15����	16����	17����	18����	19����	20����	21����	22����	23����	24����	������");//�������ļ���������123
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
//		System.out.print(distance);//�㵽ֱ�ߵľ������
	}
}

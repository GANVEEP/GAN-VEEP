package makevehicletrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.TreeMap;

import com.sun.media.sound.RIFFInvalidDataException;

public class practice {

	public static void deleteFile(File file) {
		if(file.exists()) {//�ж�·���Ƿ����
			if(file.isFile()){//boolean isFile():���Դ˳���·������ʾ���ļ��Ƿ���һ����׼�ļ��� 
				file.delete();
			}else{//�����ļ��������ļ��еĲ���
				//���� ·��D:/1/�½��ļ���2  �µ����е��ļ����ļ��е�listFiles������
				File[] listFiles = file.listFiles();//listFiles����������file·���������ļ����ļ��еľ���·��
				for (File file2 : listFiles) {
					/*
					 * �ݹ����ã����⵽����һ��һ��ɾ��������ļ� �ٴ����ڲ� ������ɾ���ļ���
					 *    ע�⣺��ʱ���ļ�������һ���Ĳ���֮��������ļ�������ȫ��ɾ��
					 *         ����ÿһ����ļ��ж��ǿյ�  ==�����Ϳ���ֱ��ɾ����
					 */
					deleteFile(file2);
				}
			}
			file.delete();
		}else {
			System.out.println("��file·�������ڣ���");
		}
	}

	public static void main(String[] args)throws Exception{
//		rectangle[] r1=generattraindata.laneinfo();//��·�ĸ�������꣬�͵�·�����
//		vehiclelane[] alllane=directionindex.directionindex("D:\\12point.txt");//��·��������
//		//practice.rechange(r1, "D:\\vehiclelane1\\formaldata\\predata.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl");
//		//TreeMap<Integer, timestatistic> treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl","D:\\12point.txt");
//		practice.crossroad(alllane, "D:\\vehiclelane1\\formaldata\\predata.txt", "D:\\vehiclelane1\\formaldata\\predata1.txt");//pythonԤ����������ݣ�ת���ж��������
//		File file3 = new File("D:\\vehiclelane1\\formaldata\\111.txt");
//		deleteFile(file3);
//        changenext.readroadindex("C:\\SUMO\\bin\\test\\tian\\tian.edg.xml");
//        changenext.readvehicleroute("C:\\SUMO\\bin\\test\\newh\\lovekq.rou.xml");
		rectangle[] r1=generattraindata.laneinfo();
		point p=new point(489.45,500);
		point p1=changeXY.judge(r1[22], p);
		System.out.println(p1.x+" "+p1.y);
	}
	//����pythonԤ����������ݣ��ж�ÿһ�����Ƿ���ʻ����һ����·���ǵĻ����������ж�ģ��
	public static void crossroad(vehiclelane[] alllane,String path,String newpath)throws Exception{
		Random rand = new Random();
		vehiclelane[] v=directionindex.directionindex("D:\\12point.txt");//ÿ����������һ����������
		DecimalFormat df=new DecimalFormat("#0");
		FileReader reader = new FileReader(path);//��ȡ����
		BufferedReader br = new BufferedReader(reader);
		double xzuobiao,yzuobiao,speed,disfromcar,disfromlukou,vid,time;//x���� �ٶ� ǰ���� ·�ھ� ��ID ��ID ʱ��
        double roid;
		int roadid,nextroadid,nextdirection;
		String printinfo;
    
		String line;
		String[] d=new String[20]; 
		String[] d1=new String[20]; 
		printdata.method(newpath, "����ID x���� y���� �ٶ� ʱ�� ��·id ǰ���� ·�ھ�");
		line = br.readLine();//���Ե�һ����ͷ
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			xzuobiao=Double.parseDouble(d[1]);
			roid=Double.parseDouble(d[5]);
			roadid=(int)roid;
			if(xzuobiao>(alllane[roadid].length-7.5)){//��ʱ�����жϳ����Ѿ�ʻ���ˡ���ǰ��·������Ҫ�жϸó�����һ�׶ε�ת�䷽��
				while(true){//ѡȡһ������ĵ�·���ó�����ʻ
					nextdirection=rand.nextInt(4);
					if(alllane[roadid].directionroad[nextdirection].lanenumber!=999){
						//nextroadid=alllane[roadid].directionroad[nextroadid].lanenumber;
						break;
					}
				}
				xzuobiao=changenext.rechangenext(xzuobiao, 0, nextdirection);//�õ���
				nextroadid=alllane[roadid].directionroad[nextdirection].lanenumber;
				printinfo=d[0]+" "+xzuobiao+" "+d[2]+" "+d[3]+" "+d[4]+" "+nextroadid+" "+999+" "+999;
				printdata.method(newpath, printinfo);
			}
			else{//��ʱ������û��ʻ����ǰ���귶Χ������ֱ�Ӱ���Ϣ��ӡ��ȥ����
				printdata.method(newpath, line);
			}
		}
	}
	public static void rechange(rectangle[] r1,String path,String newpath)throws Exception{
		DecimalFormat df=new DecimalFormat("#0");
		FileReader reader = new FileReader(path);//��ȡ����
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] d=new String[20]; 
		String[] d1=new String[20]; 
		double xzuobiao,yzuobiao,speed,disfromcar,disfromlukou,vid,time;//x���� �ٶ� ǰ���� ·�ھ� ��ID ��ID ʱ��
        double roid;
		int roadid;
		String s;
        point p=new point();
		line=br.readLine();//��ӡ��һ����ͷ
		//printdata.method(newpath, "����ID x���� y���� �ٶ� ʱ�� ��·id ǰ���� ·�ھ�");
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			vid=Double.parseDouble(d[0]);
			xzuobiao=Double.parseDouble(d[1]);
			yzuobiao=Double.parseDouble(d[2]);
			roid=Double.parseDouble(d[5]);
			speed=Double.parseDouble(d[3]);
			time=Double.parseDouble(d[4]);
			roadid=(int)roid;
			p.x=xzuobiao;
			p.y=yzuobiao;
			p=changeXY.njudge(r1[roadid], p);
			System.out.println("�������꣺"+roadid);
			System.out.println("ת��ǰ��xy����"+xzuobiao+" "+yzuobiao);
			System.out.println("ת����xy����"+p.x+" "+p.y);
			s="$ns_ at "+time+" \"$node_("+df.format(vid)+") setdest "+xzuobiao+" "+yzuobiao+" "+speed+"\"";
//			System.out.println(path);
//			System.out.println(newpath);
			printdata.method( newpath,s);
		}
	}
	public static void mm(TreeMap<Integer, timestatistic> treeMapArray){
		timestatistic timestatistic=treeMapArray.get(1);
		vehicle v=timestatistic.vehiclelanelist.get(1).vehiclelist.get(11);
		v.x=999;
	}
}

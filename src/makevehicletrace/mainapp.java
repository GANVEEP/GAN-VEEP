package makevehicletrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.TreeMap;

import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

public class mainapp {

	static DecimalFormat df=new DecimalFormat("#0.00");
	public static void main(String[] args)throws Exception {//����һ��Ԥ���Ӧ�ã������Ѿ����ɺõ�ģ�ͣ�
		DecimalFormat df=new DecimalFormat("#0.00");
		//����ȡ��·ģ��
		vehiclelane[] alllane=directionindex.directionindex("D:\\12point.txt");//��·��������
		rectangle[] r1=generattraindata.laneinfo();//��·���򣬺͵�·��Χ��Ϣ�洢����
        changenext.readroadindex("C:\\SUMO\\bin\\test\\tian\\tian.edg.xml");
        changenext.readvehicleroute("C:\\SUMO\\bin\\test\\newh\\lovekq.rou.xml");
		String oldp="D:\\0104mobility.tcl";
		String oldp1="D:\\vehiclelane1\\formaldata\\mobility.tcl";
		//��ȡns2mobility.tcl�����ݣ��õ��Ȱ���ʱ����࣬���ճ�������ĳ������ݼ���
//		TreeMap<Integer, timestatistic> treeMap=getlaneinformation.getinfor("D:\\vehiclerange.txt", oldp1,"D:\\12point.txt");
        int num=45;
        boolean b=false;
        TreeMap<Integer, timestatistic> treeMap1;
        double[] zs=mainapp.zs("D:\\zaoyin\\g0.4.txt");  
        boolean zaosheng=true;
		while(num<76){
			if(b==false){   
				treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", oldp,"D:\\12point.txt");
				b=true;
			}
			else {
				treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", oldp1,"D:\\12point.txt");
			}
		timestatistic ti=treeMap1.get(num);
		
		num++;

		getlaneinformation.vehiclelanenumbertrue=true;
		System.out.println("2020-01-15 ʱ�䣺"+num);
		for(int i=0;i<ti.vehiclelanelist.size();i++){
			for (Integer m : ti.vehiclelanelist.get(i).vehiclelist.keySet()) {
				vehicle v=ti.vehiclelanelist.get(i).vehiclelist.get(m);
//				System.out.println(i+"********************");
				getinfobylane.pritinfobytime("D:\\vehiclelane1\\formaldata\\data.txt", v);
			}
		}
		if(zaosheng==true){
			zaosheng=false;
			//��ԭʼ������Ϣת��Ϊ��׼��·������Ϣ��oldpath��newpath
			generattraindata.gettraindata1(r1,"D:\\vehiclelane1\\formaldata\\data.txt","D:\\vehiclelane1\\formaldata\\newdata.txt",zs);
		}
		else{
			generattraindata.gettraindata1(r1,"D:\\vehiclelane1\\formaldata\\data.txt","D:\\vehiclelane1\\formaldata\\newdata.txt");
		}
		//��ԭʼ������Ϣת��Ϊ��׼��·������Ϣ��oldpath��newpath
//		generattraindata.gettraindata1(r1,"D:\\vehiclelane1\\formaldata\\data.txt","D:\\vehiclelane1\\formaldata\\newdata.txt",zs);
		
		
		//Ԥ�⳵���켣15.121657 2.9491897(x���꣬�ٶȣ���������ʽ)	
		//��������ת��������ݣ�����pythonģ��Ԥ�⳵����һʱ��λ�ã�Ȼ�������Ԥ����������ݴ洢��predata.txt�ļ���
		mainapp.traindata("D:\\vehiclelane1\\formaldata\\newdata.txt", "D:\\vehiclelane1\\formaldata\\predata.txt");
		
		//�ж�Ԥ������������Ƿ���·��������·����ת��Ϊ��һ��·������
		changenext.crossroad(alllane, "D:\\vehiclelane1\\formaldata\\predata.txt", "D:\\vehiclelane1\\formaldata\\predata1.txt");//pythonԤ����������ݣ�ת���ж��������
		
		//��pythonԤ������ĳ���λ��ת��Ϊ��ʵ��·��λ�ã��洢���ļ���
		mainapp.rechange(r1, "D:\\vehiclelane1\\formaldata\\predata1.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl");
//		if(num==47)
//			break;
		deleteFile("D:\\vehiclelane1\\formaldata\\data.txt"); 
		deleteFile("D:\\vehiclelane1\\formaldata\\newdata.txt");
		deleteFile("D:\\vehiclelane1\\formaldata\\predata.txt");
		deleteFile("D:\\vehiclelane1\\formaldata\\predata1.txt");

        }
		//TreeMap<Integer, timestatistic> treeMap1=getlaneinformation.getinfor("D:\\vehiclerange.txt", "D:\\vehiclelane1\\formaldata\\mobility.tcl","D:\\12point.txt");
	}
	public static double[] zs(String path)throws Exception{
	      double[] zhaos=new double[300];
			DecimalFormat df=new DecimalFormat("#0");
			FileReader reader = new FileReader(path);//��ȡ����
			BufferedReader br = new BufferedReader(reader);
			String line;
			int i=0;
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				if(!line.equals("")){
					zhaos[i]=Double.parseDouble(line);
					System.out.println(zhaos[i]);
					i++;
				}

			}
	      return zhaos;
	}
	public static void deleteFile(String path){	
		File file3 = new File(path);
		deleteFile(file3);
	}
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
	//����ת���㣬��Ԥ����������ݣ�ͨ��������ת���㣬ת��Ϊ������ʵ��·��λ������
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
			getlaneinformation.vehiclelanenumber[(int)vid]=roadid;
			p=changeXY.njudge(r1[roadid], p);
//			System.out.println("�������꣺"+roadid);
//			System.out.println("ת��ǰ��xy����"+xzuobiao+" "+yzuobiao);
//			System.out.println("ת����xy����"+p.x+" "+p.y);
			s="$ns_ at "+time+" \"$node_("+df.format(vid)+") setdest "+p.x+" "+p.y+" "+speed+"\" "+roadid;
//			System.out.println(path);
//			System.out.println(newpath);
			printdata.method( newpath,s);
		}
		br.close();
	}
	//Ԥ�⳵����λ�ú�����Ԥ����ٰ�Ԥ��ֵͳһ�����predata�ļ���
	public static void traindata(String path,String newpath  )throws Exception{//��
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] d=new String[20]; 
		String[] d1=new String[20]; 
		int a=0;
		String data="";
		double xzuobiao,yzuobiao,speed,disfromcar,disfromlukou,vid,roadid,time;//x���� �ٶ� ǰ���� ·�ھ� ��ID ��ID ʱ��
		double havefront,frontcarv,trafficlight;
		String[] ss=new String[400];  
		int numberof=0;
		line=br.readLine();//��ӡ��һ����ͷ
		printdata.method(newpath, "����ID x���� y���� �ٶ� ʱ�� ��·id ǰ���� ·�ھ�");
		while ((line = br.readLine()) != null) {
			
			ss[numberof]=line;
			numberof++;
			d=line.split(" ");
//			System.out.println(line);
			xzuobiao=Double.parseDouble(d[0]);
			yzuobiao=Double.parseDouble(d[1]);
			speed=Double.parseDouble(d[2]);
			disfromlukou=Double.parseDouble(d[3]);
			havefront=Double.parseDouble(d[4]);
			disfromcar=Double.parseDouble(d[5]);
			frontcarv=Double.parseDouble(d[6]);
			vid=Double.parseDouble(d[7]);
			roadid=Double.parseDouble(d[8]);
			time=Double.parseDouble(d[9]);
			trafficlight=Double.parseDouble(d[10]);
			// x���� �ٶ� ·�ھ� ��ǰ�� ǰ���� ǰ���� ��ͨ��
			data=data+d[0]+" "+d[2]+" "+d[3]+" "+d[4]+" "+d[5]+" "+d[6]+" "+d[10]+"-";//X���꣬�ٶȣ�ǰ���࣬·�ھ࣬������
			
		}
		br.close();
//		System.out.println(data+"����");
		String s=usepython.print(data);
		String[] news=s.split("-");
		for(int i=0;i<news.length-1;i++){
//			d1=s.split("-");//�õ���Ԥ�⳵����X���꣬�ٶȣ�
			d1=news[i].split(" ");
			xzuobiao=Double.parseDouble(d1[0]);
			speed=Double.parseDouble(d1[1]);
			d=ss[i].split(" ");
			double x=Double.parseDouble(d[0]);//ǰһʱ�̵�x����
			yzuobiao=Double.parseDouble(d[1]);
			disfromlukou=Double.parseDouble(d[3]);
			havefront=Double.parseDouble(d[4]);
			disfromcar=Double.parseDouble(d[5]);
			frontcarv=Double.parseDouble(d[6]);
			vid=Double.parseDouble(d[7]);
			roadid=Double.parseDouble(d[8]);
			time=Double.parseDouble(d[9]);
			trafficlight=Double.parseDouble(d[10]);
			time=time+1;
			if(havefront==1){//��ǰ����ôǰ����������Լ��
				double max=disfromcar+frontcarv-7;
				if(max<(xzuobiao-x)){
					 xzuobiao=x+max;
					 speed=max;
					 if(speed<0){
						 speed=0;
					 }
				}
			}
			else{//û��ǰ������ô·�ھ�������Լ��
				if(trafficlight==0){
				if(xzuobiao>491.9){
					xzuobiao=491.9;
					speed=0; 
				}
			}
			}
			data=vid+" "+df.format(xzuobiao)+" "+d[1]+" "+df.format(speed)+" "+time+" "+roadid+" "+999+" "+999;
			printdata.method(newpath, data);
		}		
	}
}

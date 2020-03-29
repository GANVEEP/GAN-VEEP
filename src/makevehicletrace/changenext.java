package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;

public class changenext {

	static DecimalFormat df=new DecimalFormat("#0.00");
	public static double returnaround(double x,double speed){//��ͷ,����ʵ������ת��Ϊ��ٵ�����
		double key=(double) 488.64;//x����ǰһʱ�̵�X���꣬speed�����һʱ�̵��ٶ�
		//System.out.println(key);
		double position=speed+x;
		position=Double.parseDouble(df.format(position));
		return(position);
	}
	public static double returnright(double x,double speed){//��ת
		double key=(double) 489.07;
		//System.out.println(key);
		double position=speed+x;
		position=Double.parseDouble(df.format(position));
		return(position);
	}
	public static double returnstraight(double x,double speed){//��ת
		double key=(double) 500.13;
		//System.out.println(key);
		double position=speed+x;
		position=Double.parseDouble(df.format(position));
		return(position);
	}
	public static double returnleft(double x,double speed){//ֱ��
		double key=(double) 500;
		//System.out.println(key);
		double position=speed+x;
		position=Double.parseDouble(df.format(position));
		return(position);
	}
	//*******************************
	public static double turnaround(double x,double speed){//��ͷ,����ٵ�����ת��Ϊ��ʵ����
		double key=(double) 488.64;
		//System.out.println(key);
		double position=key-x;
		position=Double.parseDouble(df.format(position));
		return(Math.abs(position));
	}
	public static double turnright(double x,double speed){//��ת
		double key=(double) 489.07;
		//System.out.println(key);
		double position=key-x;
		position=Double.parseDouble(df.format(position));
		return(Math.abs(position));
	}
	public static double turnstraight(double x,double speed){//��ת
		double key=(double) 500.13;
		//System.out.println(key);
		double position=key-x;
		position=Double.parseDouble(df.format(position));
		return(Math.abs(position));
	}
	public static double turnleft(double x,double speed){//ֱ��
		double key=(double) 500;
		//System.out.println(key);
		double position=key-x;
		position=Double.parseDouble(df.format(position));
		return(Math.abs(position));
	}
	public static double rechangenext(double x,double speed,int direction)throws Exception{
		double x1=9999;
		switch(direction){
	    case 0 : x1=changenext.turnaround(x, speed);//��ͷ
	     
	       break; 
	    case 1 :x1=changenext.turnleft(x, speed);//��ת
		     
		       break; 
	    case 2:x1=changenext.turnstraight(x, speed);//ֱ��
		     
		       break; 
	    case 3 :x1=changenext.turnright(x, speed);//��ת
		     
		       break; 

	    default : 
	       System.out.println("���������9999");
	}
		return(x1);
	}
	public static void crossroad(vehiclelane[] alllane,String path,String newpath)throws Exception{
		Random rand = new Random();
		vehiclelane[] v=directionindex.directionindex("D:\\12point.txt");//ÿ����������һ����������
		DecimalFormat df=new DecimalFormat("#0");
		FileReader reader = new FileReader(path);//��ȡ����
		BufferedReader br = new BufferedReader(reader);
		double xzuobiao,yzuobiao,speed,disfromcar,disfromlukou,vid,time;//x���� �ٶ� ǰ���� ·�ھ� ��ID ��ID ʱ��
        double roid,nextroid;
		int roadid,nextroadid=888,nextdirection=888;
		String printinfo;
    
		String line;
		String[] d=new String[20]; 
		String[] d1=new String[20]; 
		printdata.method(newpath, "����ID x���� y���� �ٶ� ʱ�� ��·id ǰ���� ·�ھ�");
		line = br.readLine();//���Ե�һ����ͷ
		int veid=0;
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			veid=(int)Double.parseDouble(d[0]);
//			roadid=(int)Double.parseDouble();
			xzuobiao=Double.parseDouble(d[1]);
			roid=Double.parseDouble(d[5]);
			roadid=(int)roid;
			if(xzuobiao>(alllane[roadid].length-7.5)){//��ʱ�����жϳ����Ѿ�ʻ���˵�ǰ��·������Ҫ�жϸó�����һ�׶ε�ת�䷽��
//				
				double y=999;
				if(rand.nextInt(2)==1){
					y=1.65;
				}
				else{
					y=4.95;
				}
				
				for(int m=0;m<vroute[veid].num;m++){
					if(m+1<vroute[veid].num){
					if(vroute[veid].edg[m]==roadid){
						nextroadid=vroute[veid].edg[m+1];
						break;
					}
					}
					else{
						nextroadid=888;
						break;
					}
				}
				
//				nextdirection=changenext.getnext(alllane[roadid]);

//				System.out.println("��ǰ����id��"+roadid+"  ת��������"+alllane[roadid].directionroad[0].lanenumber+" "+alllane[roadid].directionroad[1].lanenumber+" "+alllane[roadid].directionroad[2].lanenumber+" "+alllane[roadid].directionroad[3].lanenumber);

				for(int m=0;m<4;m++){
					nextdirection=888;
					if(alllane[roadid].directionroad[m].lanenumber==nextroadid){
						nextdirection=m;
						break;
					}
				}
				if(nextdirection!=888){
					xzuobiao=changenext.rechangenext(xzuobiao, 0, nextdirection);//�õ���
					printinfo=d[0]+" "+xzuobiao+" "+y+" "+d[3]+" "+d[4]+" "+nextroadid+" "+999+" "+999;
					printdata.method(newpath, printinfo);
					System.out.println("����"+veid+" �����г�"+"    ��ǰ��·id��"+roadid+"��һ��·IDΪ��"+nextroadid);

				}
				else{
					System.out.println("����"+veid+"  �����г�"+"    ��ǰ��·id��"+roadid+"��һ��·IDΪ��"+nextroadid);
				}
				
//				nextroadid=alllane[roadid].directionroad[nextdirection].lanenumber;

			}
			else{//��ʱ������û��ʻ����ǰ���귶Χ������ֱ�Ӱ���Ϣ��ӡ��ȥ����
				printdata.method(newpath, line);
			}
		}
		br.close();
	}
	public static int getnext(vehiclelane lane){//���ݸ��ʵõ���������һ������
		Random rand = new Random();
		int nextid=999;
		int ran;
		int num=0;
		
		for(int i=0;i<4;i++){
			
			if(lane.directionroad[i].lanenumber!=999){
//				System.out.println("�����ţ�"+i+"  "+lane.directionroad[i].lanenumber);
//				System.out.println("����ͳ�ƣ�"+i+"  "+lane.count[i]);
				num=num+lane.count[i];
			}
		}
//		System.out.println("����Ϊ��"+num);
		ran=rand.nextInt(num+1);//�����������
		System.out.println("�����Ϊ��"+ran);
		for(int i=0;i<4;i++){
			if(lane.directionroad[i].lanenumber!=999){
				ran=ran-lane.count[i];
				if(ran<=0){
					return(i);
				}
			}
		}
		System.out.println("changenext ����������⣬151");
		return(999);
	}
	public static void changenext(String path,String newpath)throws Exception{//�ѿ��·�ĳ������꣬ת��Ϊ��һ��·�����귶Χ
		FileWriter fw = null;
		fw = new FileWriter(newpath, true);
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		vehiclelane[] alllane=directionindex.directionindex("D:\\12point.txt");//����ת�����������
		String line;
		String[] allinfo=new String[20];
		String[] info=new String[20];
		String[] nextinfo=new String[20];
		br.readLine();
		int direction=100;
		trafficlight.settrafficlight("D:\\12point.txt");
		while ((line = br.readLine()) != null) {
			allinfo=line.split("-");
			if(allinfo.length>1){
			info=allinfo[0].split(" ");
//			System.out.println(path);
//			System.out.println(line);
			nextinfo=allinfo[1].split(" ");
//			System.out.println(allinfo.length);
//			System.out.print(allinfo[0]+"-");
//			System.out.println(allinfo[1]+"+");
//			System.out.println(allinfo[allinfo.length-1]);
			if(!info[7].equals(nextinfo[7])){
				double beforex=Double.parseDouble(info[0]);
				double nextv=Double.parseDouble(info[1]);
				double time=Double.parseDouble(info[8]);
				double beforelannum=Double.parseDouble(info[7]);
				double nextlannum=Double.parseDouble(nextinfo[7]);
				System.out.println("ǰ"+info[7]+"��"+nextinfo[7]);
				for(int i=0;i<4;i++){//�ж���һ��������ʲô�ط���ģ���ͷ����ת��ֱ�У���ת
					if((int) nextlannum==alllane[(int) beforelannum].directionroad[i].lanenumber){
						direction=i;
						System.out.println("��ǰ�����ţ�"+beforelannum+"  ��һ�����ţ�"+nextlannum+"  ת���־��"+i);
					}
				}
				switch(direction){
			    case 0 : nextinfo[0]=""+changenext.returnaround(beforex, nextv);//��ͷ
			     
			       break; 
			    case 1 :nextinfo[0]=""+changenext.returnleft(beforex, nextv);//��ת
				     
				       break; 
			    case 2:nextinfo[0]=""+changenext.returnstraight(beforex, nextv);//ֱ��
				     
				       break; 
			    case 3 :nextinfo[0]=""+changenext.returnright(beforex, nextv);//��ת
				     
				       break; 

			    default : 
			       System.out.println("���������");
			}
				
				String s=info[0]+" "+info[1]+" "+info[2]+" "+info[3]+" "+info[4]+" "+info[5]+" "+info[6]+" "+info[7]+" "+info[8]+" "+trafficlight.tafficlight[(int)beforelannum][(int)time]+"-"+nextinfo[0]+" "+nextinfo[1]+" "+nextinfo[2]+" "+nextinfo[3]+" "+nextinfo[4]+" "+nextinfo[5]+" "+nextinfo[6]+" "+nextinfo[7]+" "+nextinfo[8];
				fw.write(s);//�������ļ���������
		        fw.write("\r\n");
				fw.flush();
						
			}
			else{
				double beforex=Double.parseDouble(info[0]);
				double nextv=Double.parseDouble(info[1]);
				double time=Double.parseDouble(info[8]);
				double beforelannum=Double.parseDouble(info[7]);
				double nextlannum=Double.parseDouble(nextinfo[7]);
				String s=info[0]+" "+info[1]+" "+info[2]+" "+info[3]+" "+info[4]+" "+info[5]+" "+info[6]+" "+info[7]+" "+info[8]+" "+trafficlight.tafficlight[(int)beforelannum][(int)time]+"-"+nextinfo[0]+" "+nextinfo[1]+" "+nextinfo[2]+" "+nextinfo[3]+" "+nextinfo[4]+" "+nextinfo[5]+" "+nextinfo[6]+" "+nextinfo[7]+" "+nextinfo[8];
				fw.write(s);//�������ļ���������123
		        fw.write("\r\n");
				fw.flush();
			}
			
		}
	}
		fw.close();
	}
	static String[] roadstring=new String[50];//��·���ַ�����index��ӳ��
	static vehicleroute[] vroute=new vehicleroute[500];//�����±��ǳ���ID�������ǳ���·����Ϣ
	public static void readvehicleroute(String path)throws Exception{
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] d=new String[100];
		int n=0;
		int id=0;
		String[] r;
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			if(d.length>5){
//				System.out.println("5Ϊ��"+d[5]);
			if(d[4].equals("<vehicle")){
				id=Integer.parseInt(d[5].split("=")[1].split("\"")[1]);
				vroute[id]=new vehicleroute();
			}
			}
			if(d.length>8){
//				System.out.println("8Ϊ��"+d[8]);
			if(d[8].equals("<route")){
				r=line.split("\"")[1].split(" ");
				for(int i=0;i<r.length;i++){
					for(int j=0;j<roadstring.length;j++){
						if(roadstring[j].equals(r[i])){
							vroute[id].edg[vroute[id].num]=j;
							vroute[id].num++;
							break;
						}
					}
				}
				System.out.print("����IDΪ��"+id);
				System.out.print("  ·��Ϊ��"+line.split("\"")[1]);
				System.out.print("����Ϊ��");
				for(int i=0;i<vroute[id].num;i++){
					System.out.print(vroute[id].edg[i]+" ");
				}
				System.out.println();
			}
		}
		}
	}
	public static void readroadindex(String path)throws Exception{
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] d=new String[100];
		int n=0;
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			if(d[0].equals("<edge")){
				changenext.roadstring[n]=d[1].split("=")[1].split("\"")[1];
				System.out.println(changenext.roadstring[n]);
				n++;
			}
		}
		br.close();
	}
	public static void main(String[] args) throws Exception{
		String path="D:\\vehiclelane1\\traindata\\";
		String again;
		String newpath="D:\\vehiclelane1\\finish\\okvehicle.txt";
		//changenext.changenext("D:\\vehiclelane1\\traindata\\0vehicle.txt","D:\\vehiclelane1\\finish\\okvehicle.txt");
		for(int i=0;i<299;i++){//���Ǵ��������ݵĴ���
			again=path+i+"vehicle.txt";
			System.out.println(again);
			changenext.changenext(again, newpath);
		}
//		changenext.changenext(path+"2vehicle.txt", newpath);
//		vehiclelane v=new vehiclelane(1);
//		v.count[0]=1;
//		v.count[1]=2;
//		v.count[2]=2;
//		v.count[3]=1;
//		v.directionroad[0].lanenumber=2;
//		v.directionroad[1].lanenumber=999;
//		v.directionroad[2].lanenumber=4;
//		v.directionroad[3].lanenumber=5;
////		changenext.getnext(v);
//		for(int i=0;i<100;i++){
//			System.out.println(changenext.getnext(v));
//		}
//		System.out.println(changenext.turnaround(499.33, 6.98));
//		System.out.println(changenext.turnright(511.88, 23.0));
//		System.out.println(changenext.turnleft(504.79, 18.84));
//		System.out.println(changenext.turnstraight(505.44, 18.88));
//		System.out.println("++++++++++");
//		System.out.println(changenext.returnaround(492.35, 6.98));
//		System.out.println(changenext.returnright(485.92, 23.0));
//		System.out.println(changenext.returnleft(488.88, 18.84));
//		System.out.println(changenext.returnstraight(486.56, 18.88));
	}

}

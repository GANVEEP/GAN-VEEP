package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;

public class directionindex {

	public static boolean twopoint(point p1,point p2){//�ж��������Ƿ���ͬ
		if(p1.x==p2.x&&p1.y==p2.y){
			return(true);//��ͬΪ�棬��ͬΪ��
		}
		else{
			return(false);
		}
	}
	public static vehiclelane[] directionindex(String path)throws Exception{//��12point�������24����·���������ȡ��������������
		
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		String[] allinfo=new String[20];
		String[] allinfo1=new String[20];
		String[] allinfo2=new String[20];
		int j=0;
		int lanenum=24;
		vehiclelane[] alllane=new vehiclelane[lanenum];
		for(int i=0;i<lanenum;i++){//��12����·�����ʼ��
			alllane[i]=new vehiclelane(i);
		}
		while ((line = br.readLine()) != null) {
			allinfo=line.split(" ");
			//System.out.println(allinfo[0]);
			alllane[j].oriPoint.x=Float.parseFloat(allinfo[0]);
			alllane[j].oriPoint.y=Float.parseFloat(allinfo[1]);
			alllane[j].endPoint.x=Float.parseFloat(allinfo[2]);
			alllane[j].endPoint.y=Float.parseFloat(allinfo[3]);
			alllane[j].length=directionindex.getlength(alllane[j].oriPoint, alllane[j].endPoint);
			j++;
		}
		for(int i=0;i<lanenum;i++){
			for(int m=0;m<lanenum;m++){
				if(directionindex.twopoint(alllane[i].oriPoint, alllane[m].endPoint)&&
						directionindex.twopoint(alllane[i].endPoint, alllane[m].oriPoint)){//����жϵ�ͷ,������յ���ͬ���յ��������ͬ
					alllane[i].directionroad[0].lanenumber=alllane[m].lanenumber;
				}
				if(alllane[i].endPoint.x==alllane[m].oriPoint.x&&//�յ��������ͬ��������յ㲻ͬ����ֱ�ߡ���Ϊ0
						alllane[i].endPoint.y==alllane[m].oriPoint.y&&
						!directionindex.twopoint(alllane[i].oriPoint, alllane[m].endPoint)&&
						0==directionindex.getchac(alllane[i].oriPoint, alllane[i].endPoint, alllane[m].oriPoint, alllane[m].endPoint)){
					alllane[i].directionroad[2].lanenumber=alllane[m].lanenumber;//����ж�ֱ��
				}
				if(alllane[i].endPoint.x==alllane[m].oriPoint.x&&//�յ��������ͬ��������յ㲻ͬ����ֱ�ߡ��˴���0
						alllane[i].endPoint.y==alllane[m].oriPoint.y&&
						0<directionindex.getchac(alllane[i].oriPoint, alllane[i].endPoint, alllane[m].oriPoint, alllane[m].endPoint)){
					    //��˴���0��˵������ʱ�뷽�򣬼���ת
					alllane[i].directionroad[1].lanenumber=alllane[m].lanenumber;
				}
				if(alllane[i].endPoint.x==alllane[m].oriPoint.x&&//�յ��������ͬ��������յ㲻ͬ����ֱ�ߡ���С��0
						alllane[i].endPoint.y==alllane[m].oriPoint.y&&
						0>directionindex.getchac(alllane[i].oriPoint, alllane[i].endPoint, alllane[m].oriPoint, alllane[m].endPoint)){
					    //���С��0��˵����˳ʱ�뷽�򣬼���ת
					alllane[i].directionroad[3].lanenumber=alllane[m].lanenumber;
				}
				
			}
		}
		FileReader reader1 = new FileReader("D:\\vehiclelane1\\finish\\okvehicle.txt");//�˴��ĵ�ַ��ѵ�����ݵĵ�ַ
		BufferedReader br1 = new BufferedReader(reader1);

		double previousid,nextid;
		int previousidint,nextidint;
		while ((line = br1.readLine()) != null) {
//			System.out.println(line);
			allinfo=line.split("-");
			if(allinfo.length>1){
			allinfo1=allinfo[0].split(" ");
			allinfo2=allinfo[1].split(" ");
			previousid=Double.parseDouble(allinfo1[7]);
			nextid=Double.parseDouble(allinfo2[7]);
			previousidint=(int)previousid;
			nextidint=(int)nextid;//��ǰ����id�ͺ󳵵�IDת��Ϊint��
			if(!allinfo1[7].equals(allinfo2[7])){
				
				for(int m=0;m<4;m++){
//					System.out.println(previousidint);
//					System.out.println(m);
					if(alllane[previousidint].directionroad[m].lanenumber==nextid){
						
						alllane[previousidint].count[m]++;
						//System.out.println("ǰ�����ţ�"+previousidint+"  �󳵵��ţ�"+nextidint);
						//System.out.println("�󳵵��ţ�"+alllane[previousidint].directionroad[m].lanenumber+"  �󳵵��ţ�"+nextidint);
					}
				}
				
			}
		}
	}
		
//		for(int i=0;i<lanenum;i++){
//
//				System.out.println("�����ţ�"+alllane[i].lanenumber+"  ��ͷ��"+alllane[i].directionroad[0].lanenumber+"  ��ת��"+alllane[i].directionroad[1].lanenumber+"  ֱ�У�"+alllane[i].directionroad[2].lanenumber+"  ��ת��"+alllane[i].directionroad[3].lanenumber);
//
//			    System.out.println("����ת��ͳ�ƣ�"+alllane[i].lanenumber+"  ��ͷ��"+alllane[i].count[0]+"  ��ת��"+alllane[i].count[1]+"  ֱ�У�"+alllane[i].count[2]+"  ��ת��"+alllane[i].count[3]);
//		}
		return(alllane);
	}
	public static double getlength(point p1,point p2){//����������ľ���
		double length;
		length=Math.sqrt((p2.y-p1.y)*(p2.y-p1.y)+(p2.x-p1.x)*(p2.x-p1.x));
		return(length);
	}
	public static float getdianc(point p1,point p2,point p3,point p4){//�õ����������ļн�
		//�����ĵ����x1*x2+y1*y2�������ġ���������������|c|=|a|*|b|sin(a,b),��x1*y2-x2*y1
		float x1=(float)(p2.x-p1.x);
		float y1=(float)(p2.y-p1.y);
		float x2=(float)(p4.x-p3.x);
		float y2=(float)(p4.y-p3.y);
		float cosab,ab,a,b;
		ab=x1*x2+y1*y2;
		a=(float) Math.sqrt(x1*x1+y1*y1);
		b=(float) Math.sqrt(x2*x2+y2*y2);
		cosab=ab/(a*b);
		return(cosab);
	}
	public static float getchac(point p1,point p2,point p3,point p4){//�õ����������ļн�
		//�����ĵ����x1*x2+y1*y2�������ġ���������������|c|=|a|*|b|sin(a,b),��x1*y2-x2*y1
		float x1=(float)(p2.x-p1.x);
		float y1=(float)(p2.y-p1.y);
		float x2=(float)(p4.x-p3.x);
		float y2=(float)(p4.y-p3.y);
		float cosab,ab,a,b;
		cosab=x1*y2-x2*y1;
		return(cosab);//���ء���
	}
	
	public static void main(String[] args) throws Exception{
		point p1=new point(0,1000);
		point p2=new point(0,500);
		point p3=new point(0,500);
		point p4=new point(0,0);
        System.out.println(directionindex.getchac(p1, p2, p3, p4));
//		directionindex.directionindex("D:\\12point.txt");
//		point p1=new point(1,1);
//		point p2=new point(2,2);
//		point p3=new point();
//		point p4=new point();
//		System.out.println(directionindex.getchac(p1, p2, p3, p4));
//		System.out.println(directionindex.getdianc(p1, p2, p3, p4));

	}

}

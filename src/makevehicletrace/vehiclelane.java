package makevehicletrace;

import java.util.List;
import java.util.TreeMap;

public class vehiclelane {

	double length;//��·�ĳ���
	float[] vehicleposition=new float[8];//�������ĸ�������
	vehiclelane[] directionroad=new vehiclelane[4];//0�����ͷ��1������ת��2����ֱ�У�3������ת��999��������
	int[] count=new int[4];//��¼�ӵ�ǰ����ת���⼸�������ĳ�����������
	int lanenumber;//����ID
	TreeMap<Integer, vehicle> vehiclelist=new TreeMap<Integer, vehicle>();//����ID�ͳ�����Ϣ
	point oriPoint=new point();
	point endPoint=new point();
	public vehiclelane(int id){
		this.lanenumber=id;
		for(int i=0;i<directionroad.length;i++){
			directionroad[i]=new vehiclelane();
			directionroad[i].lanenumber=999;
		}
	}
	public vehiclelane(){

	}
	
}

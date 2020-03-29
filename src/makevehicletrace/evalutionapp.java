package makevehicletrace;

import java.io.File;
import java.util.TreeMap;

public class evalutionapp {

	public static void main(String[] args) throws Exception{
		int ini=45;
		String prepath="D:\\vehiclelane1\\formaldata\\"+"����\\g2.tcl";
		String realpath="D:\\0104mobility.tcl";
		String prevehiclenum="D:\\vehiclelane1\\evaluation\\prevehiclenum.txt";
		String realvehiclenum="D:\\vehiclelane1\\evaluation\\realvehiclenum.txt";
		String prevehiclespeed="D:\\vehiclelane1\\evaluation\\prevehiclespeed.txt";
		String realvehiclespeed="D:\\vehiclelane1\\evaluation\\realvehiclespeed.txt";
		
		String vehiclenum="D:\\vehiclelane1\\evaluation\\vehiclenum.txt";
		String vehiclespeed="D:\\vehiclelane1\\evaluation\\vehiclespeed.txt";
		
		int num=ini+5;
		for(int ii=0;ii<6;ii++){
			num=ini+5*(ii+1);
        TreeMap<Integer, timestatistic> pretreeMap=getlaneinformation.getinfor("D:\\vehiclerange.txt", prepath,"D:\\12point.txt");
        timestatistic prti=pretreeMap.get(num); 
        everyroad.everyroadvolume(prevehiclenum,  prevehiclespeed, prti);//*******
//System.out.println(prti.vehiclelanelist.get(0).vehiclelist.get(50).speed);
//everyroad.everyroadvolume(prevehiclenum, prevehiclespeed, oripreti,prti);
//        timestatistic ha=realtreeMap.get(ini); 
//        everyroad.everyroadvolume(prevehiclenum,  prevehiclespeed, ha);
        TreeMap<Integer, timestatistic> realtreeMap=getlaneinformation.getinfor("D:\\vehiclerange.txt", realpath,"D:\\12point.txt");

        timestatistic realti=realtreeMap.get(num);
		everyroad.everyroadvolume(realvehiclenum,  realvehiclespeed, realti);
		
        everyroad.getvehicleinfo(realvehiclenum, prevehiclenum, vehiclenum);
        everyroad.getvehiclespeed(realvehiclespeed, prevehiclespeed, vehiclespeed);//�������ָ��
		String vehiclenum1="D:\\vehiclelane1\\evaluation\\vehiclenum.txt";
		String vehiclespeed1="D:\\vehiclelane1\\evaluation\\vehiclespeed.txt";
		getevaluationMetrics.getmacro(vehiclenum1, vehiclespeed1);
        deleteFile(prevehiclenum); 
        deleteFile(prevehiclespeed); 
        deleteFile(realvehiclenum); 
        deleteFile(realvehiclespeed); 
        deleteFile(vehiclenum); 
        deleteFile(vehiclespeed); 
		
//        break;
	}
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
}

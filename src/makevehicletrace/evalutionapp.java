package makevehicletrace;

import java.io.File;
import java.util.TreeMap;

public class evalutionapp {

	public static void main(String[] args) throws Exception{
		int ini=45;
		String prepath="D:\\vehiclelane1\\formaldata\\"+"噪声\\g2.tcl";
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
        everyroad.getvehiclespeed(realvehiclespeed, prevehiclespeed, vehiclespeed);//宏观评价指标
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
		if(file.exists()) {//判断路径是否存在
			if(file.isFile()){//boolean isFile():测试此抽象路径名表示的文件是否是一个标准文件。 
				file.delete();
			}else{//不是文件，对于文件夹的操作
				//保存 路径D:/1/新建文件夹2  下的所有的文件和文件夹到listFiles数组中
				File[] listFiles = file.listFiles();//listFiles方法：返回file路径下所有文件和文件夹的绝对路径
				for (File file2 : listFiles) {
					/*
					 * 递归作用：由外到内先一层一层删除里面的文件 再从最内层 反过来删除文件夹
					 *    注意：此时的文件夹在上一步的操作之后，里面的文件内容已全部删除
					 *         所以每一层的文件夹都是空的  ==》最后就可以直接删除了
					 */
					deleteFile(file2);
				}
			}
			file.delete();
		}else {
			System.out.println("该file路径不存在！！");
		}
	}
}

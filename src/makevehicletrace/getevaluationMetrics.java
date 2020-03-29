package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;

public class getevaluationMetrics {

//	public static double getRR(){
//		
//	}
	public static double getvehicleAA(String prepath,String realpath)throws Exception{//�����ж�΢��ָ��
		double aa=0;
		String preline,realline;
		String[] press=new String[20];
		String[] realss=new String[20];
		int num=0;
		FileReader reader = new FileReader(prepath);
		BufferedReader prebr = new BufferedReader(reader);//�����ĸ�����ļ�
		FileReader reader1 = new FileReader(realpath);
		BufferedReader realbr = new BufferedReader(reader1);//�����ĸ�����ļ�
		while ((preline = prebr.readLine()) != null) {
			realline=realbr.readLine();
			press=preline.split(" ");
			realss=realline.split(" ");
			
		}
		realbr.close();
		prebr.close();
		return(aa);
	}
	public static double getmacro(String numpath,String speedpath)throws Exception{//�����ж�΢��ָ��
		double aa=0;
		double prenum,realnum,prespeed,realspeed;
		String numline,speedline;
		String[] numss=new String[20];
		String[] speedss=new String[20];
		
		FileReader reader = new FileReader(numpath);
		BufferedReader numbr = new BufferedReader(reader);//�����ĸ�����ļ�
		FileReader reader1 = new FileReader(speedpath);
		BufferedReader speedbr = new BufferedReader(reader1);//�����ĸ�����ļ�
		double totalspeed=0,totalnum=0;
		double speedaa,numaa;
		double speedmae=0,nummae=0;
		double speedrmse=0,numrmse=0;
		int num=0;
		while ((numline = numbr.readLine()) != null) {
			speedline=speedbr.readLine();
//			System.out.println("�ٶȣ�"+speedline);
//			System.out.println("������"+numline);
			numss=numline.split(" ");
			speedss=speedline.split(" ");
			realnum=Double.parseDouble(numss[1]);
			prenum=Double.parseDouble(numss[2]);
			realspeed=Double.parseDouble(speedss[1]);
			prespeed=Double.parseDouble(speedss[2]);
			if(realspeed<3||realnum<3){
				
			}
			else{
				totalnum=totalnum+getevaluationMetrics.returna(realnum, prenum);
				totalspeed=totalspeed+getevaluationMetrics.returna(realspeed, prespeed);	
				speedmae=speedmae+getevaluationMetrics.returnm(realspeed, prespeed);
				
				nummae=nummae+getevaluationMetrics.returnm(realnum, prenum);
				speedrmse=speedrmse+getevaluationMetrics.returnm(realspeed, prespeed)*getevaluationMetrics.returnm(realspeed, prespeed);
				numrmse=numrmse+getevaluationMetrics.returnm(realnum, prenum)*getevaluationMetrics.returnm(realnum,prenum);
//				System.out.println("RMSE��"+speedrmse);
				num++;
			}
			
		}
		numbr.close();
		speedbr.close();
		speedaa=totalspeed/num;//����
		numaa=totalnum/num;
		double speedMAE=speedmae/num;
		double numMAE=nummae/num;
		double speedRMSE=Math.sqrt(speedrmse/num);
		double numRMSE=Math.sqrt(numrmse/num);
		DecimalFormat df = new DecimalFormat("0.000");
		String numpath1="D:\\vehiclelane1\\evaluation\\nummetrics.txt";
		String speedpath1="D:\\vehiclelane1\\evaluation\\speedmetrics.txt";
		String numinfo=df.format(numRMSE)+"\r\n"+df.format(numMAE)+"\r\n"+df.format(numaa);
		String speedinfo=df.format(speedRMSE)+"\r\n"+df.format(speedMAE)+"\r\n"+df.format(speedaa);
		printdata.method(numpath1, numinfo);
		printdata.method(speedpath1, speedinfo);
		System.out.println(df.format(speedRMSE));//����RMSE
		System.out.println(df.format(speedMAE));//����MAE
		System.out.println(df.format(speedaa));//�ٶ�aa
		System.out.println(df.format(numRMSE));   //����RMSE
		System.out.println(df.format(numMAE));//����MAE
		System.out.println(df.format(numaa));//����aa	
		
		return(numaa);
	}
	public static double returnm(double a,double b){
		return(Math.abs(a-b));
	}
	public static double returna(double a,double b){//a������ʵ����
		return(1-Math.abs(a-b)/(30));
	}
	public static void main(String[] args) throws Exception{
//		double a=getevaluationMetrics.returna(20, 21);
//		System.out.print(a);
		String speedpath;
		String numpath;
		String vehiclenum="D:\\vehiclelane1\\evaluation\\vehiclenum.txt";
		String vehiclespeed="D:\\vehiclelane1\\evaluation\\vehiclespeed.txt";
		getevaluationMetrics.getmacro(vehiclenum, vehiclespeed);
		
		
//		String printinfo=1+"\r\n"+1+"\r\n"+1+"\r\n"+1+"\r\n"+1;
//		printdata.method("D:\\vehiclelane1\\evaluation\\111.txt", printinfo);
	}

}

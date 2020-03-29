package makevehicletrace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;

public class zs {

	public static double[] zs(String path)throws Exception{
	      double[] zhaos=new double[300];
			DecimalFormat df=new DecimalFormat("#0");
			FileReader reader = new FileReader(path);//¶ÁÈ¡Êý¾Ý
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
	public static void main(String[] args) throws Exception{
		zs.zs("D:\\zaoyin\\b0.2.txt");

	}

}

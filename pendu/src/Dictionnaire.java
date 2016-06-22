import java.util.*;
import java.io.*;


public class Dictionnaire {

	public ArrayList<String> list;

	public Dictionnaire()
	{
		list=new ArrayList<String>();
	}
	/********************************* Charger la liste de Dictionnaire ***************************************/	
	public void charger(String path)
	{
		if(list.isEmpty()==false)
			list.clear();

		try
		{
			BufferedReader br=new BufferedReader(new FileReader(path));
			String str=null;

			while((str=br.readLine())!=null)
				list.add(str);

			br.close();
		}
		catch(IOException e)
		{
			System.out.println("Erreur de lecture!!!");
		}
	}
}
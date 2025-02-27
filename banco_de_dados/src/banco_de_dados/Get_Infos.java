package banco_de_dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Get_Infos {
	public String get_bdroot() {
		String arquivo_path = "src\\banco_de_dados\\bd_root\\bd_info";

		try {
			Scanner leitor = new Scanner(new File(arquivo_path));
			String data = leitor.nextLine();
						leitor.close();
			return data;
		}
		catch(FileNotFoundException e){
			System.out.print("error");
			return "FileNotFound";
		}
	}
}

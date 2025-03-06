package banco_de_dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Get_Infos {
	public String[] get_bdroot() throws IOException {
		String arquivo_path = "src\\banco_de_dados\\bd_root\\bd_info";

		try {
			File aq = new File(arquivo_path); // Abre o arquivo
			Scanner leitor = new Scanner(aq); // Escaneia o arquivo
			String quant_databases = leitor.nextLine(); // Lê a primeira linha
			
			if(quant_databases.equals("0")) { // Caso o numero lido for "0" ele retorna pois não tem Databases
				leitor.close();
				return new String[]{"-2"}; // NoDatabaseAvailable
			}
			
			int	quant_lines = (int) Files.lines(Paths.get("src", "banco_de_dados", "bd_root", "bd_info")).count(); // Puxa a quantidade de linhas
			
			String data[] = new String[quant_lines]; // Cria o array 
			data[0] = quant_databases;
			
			// Lê as linhas restantes e salva no array
			int i = 1;
			while(leitor.hasNext()) {
				data[i] = leitor.nextLine();
				i++;
			}

			leitor.close();			
			return data; // Retorna o array
		}
		
		catch(FileNotFoundException e){
			return new String[]{"-1"}; // FileNotFound;
		}
	}
}

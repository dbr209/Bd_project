package banco_de_dados;

import java.io.File;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Infos_Config {
	public String[] get_bdroot() throws Exception {
		String arquivo_path = "src\\banco_de_dados\\bd_root\\bd_info";

		File aq = new File(arquivo_path); // Abre o arquivo
		
		if(!aq.exists()) {
			System.out.println("Error ao Inicializar.");
			return new String[] {"-1"};
		}
		
		Scanner leitor = new Scanner(aq); // Escaneia o arquivo
		String quant_databases = leitor.nextLine(); // Lê a primeira linha
			
		if(quant_databases.equals("0")) { // Caso o numero lido for "0" ele retorna pois não tem Databases
			leitor.close();
			return new String[]{"0"}; // NoDatabaseAvailable
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
	
	public String[] get_tabela_info(String nomeDB) throws Exception{
		String arquivo_path = "src\\banco_de_dados\\bd_root\\" + nomeDB + "\\tabela_info.txt";
		File aqTBL = new File(arquivo_path); // Abre o arquivo
		
		if(!aqTBL.exists()) {
			System.out.println("Error ao Inicializar.");
			return new String[] {"-1"};
		}
		
		Scanner leitor = new Scanner(aqTBL); // Escaneia o arquivo
		String quant_databases = leitor.nextLine(); // Lê a primeira linha
			
		if(quant_databases.equals("0")) { // Caso o numero lido for "0" ele retorna pois não tem Databases
			leitor.close();
			return new String[]{"0"}; // NoTableAvailable
		}
		
		int	quant_lines = (int) Files.lines(Paths.get(arquivo_path)).count(); // Puxa a quantidade de linhas
		
		String data[] = new String[quant_lines]; // Cria o array 
		data[0] = quant_databases;
			
		// Lê as linhas restantes e salva no array
		int i = 1;
		while(leitor.hasNext()) {
			data[i] = leitor.nextLine();
			i++;
		}

		leitor.close();			
		return data; // Retorna o array}
	}
}

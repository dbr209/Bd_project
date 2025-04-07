package banco_de_dados;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.FileWriter;
import java.util.Scanner;

public class Tabela{

	public Scanner sc;
	public Infos_Config get = new Infos_Config();
	
	String nameDB = "";
	public String tabela_info[];
	String arquivo_path = "src\\banco_de_dados\\bd_root\\";
	
	public void inicializar(String novo_nameDB, Scanner novo_sc) throws Exception{
			
		nameDB = novo_nameDB;
		sc = novo_sc;
		tabela_info = get.get_tabela_info(nameDB);
		
		if(tabela_info[0].equals("-1")) {
			return;
		}
	}
	
	public void createTable() throws Exception {
		String path = arquivo_path + "\\" + nameDB; // Caminho contcatenado com o local da database
		String nameTabela; // Nome da tabela
		
		System.out.println("Digite o nome da tabela: ");
		nameTabela = sc.nextLine(); // Nome da tabela sera escaneado do teclado
		
		path = arquivo_path + nameDB + "\\" + nameTabela + ".txt"; // Caminho contcatenado com o local da database e o nome da tabela
		
		File file = new File(path); // Objeto do tipo File criado com o path
		
		// Se não existir nenhuma tabela no endereço passado para o objeto file, a tabela é criada com sucesso
		if(file.exists()==false) {
			file.createNewFile();
		}
		
		// Se o arquivo exisxir, pede ao usuario um novo nome para a tabela e enquanto o usuario digitar um nome de uma tabela ja existente um novo nome será solicitado
		else if(file.exists()) {
			
			while(file.exists()) {
				System.out.println("A tabela ja existe");
				
				System.out.println("Digite o nome da tabela: ");
				nameTabela = sc.nextLine();
				
				path = arquivo_path + nameDB + "\\" + nameTabela + ".txt";
				file = new File(path);
			}
			
			// Apos o usuario digitar o nome da tabela corretamente, o arquivo será criado
			file.createNewFile();
		}
		
		System.out.println("Tabela criada com sucesso.\n");
		
		String newTBLInfo[] = new String[tabela_info.length + 1];
        Path pathTabelaInfo = Path.of(arquivo_path + "\\" + nameDB + "\\" + "tabela_info.txt");
        
        for(int j = 0;j < tabela_info.length; j++) {
    		newTBLInfo[j] = tabela_info[j];
        }
    
        newTBLInfo[tabela_info.length] = nameTabela;
        
        newTBLInfo[0] = "" + (Integer.parseInt(newTBLInfo[0]) + 1);
        tabela_info = newTBLInfo;

        for(int j = 0; j < tabela_info.length; j++) {
        	if(j == 0) {
        		Files.writeString(pathTabelaInfo, newTBLInfo[j] + "\n");
        	} else {
        		Files.writeString(pathTabelaInfo, newTBLInfo[j] + "\n", StandardOpenOption.APPEND);
        	}
        }
	}
	
	public void excluirTable() throws Exception{
		System.out.println("Informe o nome da tabela que deseja excluir: ");
		String nomeTabela = sc.nextLine(); 
		
		// Cria um objeto File que representa a tabela dentro do banco de dados
		File tabela = new File(arquivo_path + "\\"  + nameDB + "\\" + nomeTabela + ".txt");
		
		if (tabela.exists()) { // Verifica se a tabela existe
			if (tabela.delete()) { // Tenta excluir a tabela
				System.out.println("Tabela removida com sucesso."); 
		            
		        String newTBLInfo[] = new String[tabela_info.length - 1];
		        Path path = Path.of(arquivo_path + "\\" + nameDB + "\\" + "tabela_info.txt");
		            
		        int aux = 0;
		        for(int i = 0;i < tabela_info.length; i++) {
		            if(!tabela_info[i].equals(nomeTabela)) {
		            	newTBLInfo[aux] = tabela_info[i];
		            	aux += 1;
		            }
		        }
		            
		        newTBLInfo[0] = "" + (Integer.parseInt(newTBLInfo[0]) - 1);
		        tabela_info = newTBLInfo;

		        for(int j = 0; j < tabela_info.length; j++) {
		            if(j == 0) {
		            	Files.writeString(path, newTBLInfo[j] + "\n");
		            } else {
		            	Files.writeString(path, newTBLInfo[j] + "\n", StandardOpenOption.APPEND);
		            }
		        }
		            
		    } else {
		    	System.out.println("Erro ao tentar excluir a tabela."); 
		    }
		} else {
			System.out.println("Tabela não encontrada neste banco de dados.");
		}
	}
	
	public void acessarTable() throws Exception {
		System.out.println("\n========== TABELAS ==========");
		listarTables();
		System.out.printf("Qual Tabela deseja acessar: ");
		String op = sc.nextLine();
		
		int numTBL = -1;
		for(int i = 1; i < tabela_info.length; i++) {
			if(op.equals(tabela_info[i])) {
				numTBL = i;
				break;
			}
		}
		
		if(numTBL != -1) {
			interfaceEditRemoveLine(tabela_info[numTBL]);
		} else {
			System.out.println("Tabela Inválida.\n");
		}
	}
	
	public void listarTables() {
		System.out.println("");
		
		if(tabela_info[0].equals("0")) {
			System.out.println("Sem Tabelas disponivels.\n");
		} else {
			for(int i = 1; i < tabela_info.length; i++) {
				System.out.println(tabela_info[i]);
			}
			System.out.println("");
		}
	}
	
	public void interfaceEditRemoveLine(String nameTBL) throws Exception{
		boolean sair = false;
		do {
			System.out.println("========== " + nameTBL + " ==========");
			lerTable(nameTBL);
			System.out.print("\n1. Adicionar Linha\n2. Remover Linha\n0. Sair\n\n-> ");
			String op = sc.nextLine();
			
			switch(op) {
				case "1":
					createNewLine(nameTBL);
					break;
				case "2":
					excluirLinha(nameTBL);
					break;
				case "0":
					sair = true;
					break;
				default:
					System.out.println("Opcao Invalida");
			}
		} while(sair == false);
	}
	
	public void lerTable(String nameTBL) throws Exception {
		String arquivoTablePath = arquivo_path + "//" + nameDB + "\\" + nameTBL + ".txt";
		
		File aq = new File(arquivoTablePath); // Abre o arquivo
		
		if(!aq.exists()) {
			System.out.println("Error ao ler tabela.");
			return;
		}
		Scanner leitor = new Scanner(aq); // Escaneia o arquivo
			
		int i = 0;
		while(leitor.hasNextLine()) {
			System.out.println(i + ". " + leitor.nextLine());
			i++;
		}
			
		leitor.close();
	}
	
	public void createNewLine(String nameTabela) throws Exception{
		String path = "src\\banco_de_dados\\bd_root\\" + nameDB + "\\" + nameTabela + ".txt"; // Caminho da tabela que será escrita
		
		
		BufferedWriter escrever = new BufferedWriter(new FileWriter(path,true)); // Classe usada para escrita na tabela

		// Variavel que será lida e passada para o metodo para ser escrita na tabela
		String conteudo;
		
		System.out.println("Digite abaixo dessa linha: ");
		conteudo = sc.nextLine();
		
		
		escrever.write(conteudo + "\n"); // Espaço que será incrementado na linha escrita para melhor organização da tabela
		System.out.println("Terminado");
		
		escrever.close();
	}
	
	public void excluirLinha(String nomeTabela) throws Exception {

	    // Define o caminho do arquivo da tabela dentro do banco de dados
	    Path pathTabela = Paths.get("src", "banco_de_dados", "bd_root", nameDB, nomeTabela + ".txt");
	
	    System.out.println("Informe o conteúdo da linha que deseja excluir: "); //Se existir...
	    String linhaExcluir = sc.nextLine(); // Lê o conteúdo exato da linha que será removida
	    
	    // Conta quantas linhas existem na tabela e armazena esse número
	    int quantLinhas = (int) Files.lines(pathTabela).count();
	    String[] linhas = new String[quantLinhas];
	
	    // Abre o arquivo e armazena cada linha no array "linhas"
	    Scanner scanner = new Scanner(pathTabela);
	    int auxiliar = 0; 
	    while (scanner.hasNextLine()) { // Enquanto houver linhas no arquivo para serem lidas...
	    	linhas[auxiliar++] = scanner.nextLine(); // Armazena a linha no array
	    }
	   
	    String[] novaTabela = new String[quantLinhas - 1]; 
	    int aux = 0; 
	      
	    for (int i = 0; i < linhas.length; i++) {
	    	if (!linhas[i].equals(linhaExcluir)) { // Se a linha NÃO for a que deve ser excluída, copia no array "novaTabela"
	    		novaTabela[aux++] = linhas[i]; 
	        }
	    }
	
	    if (aux == quantLinhas) {
	    	System.out.println("Linha não encontrada na tabela.");
	    	scanner.close();
	        return;
	    }
	    
	    if(quantLinhas == 1) {
	    	Files.writeString(pathTabela, "");
	    	scanner.close();
	    	return;
	    }
	    
	     // Escreve o novo conteúdo no arquivo, sobrescrevendo as linhas antigas
	    for (int k = 0; k < aux; k++) {
	    	if (k == 0) {
	    		Files.writeString(pathTabela, novaTabela[k] + "\n");
	        } else {
	        	// Adiciona as demais linhas ao arquivo
	        	Files.writeString(pathTabela, novaTabela[k] + "\n", StandardOpenOption.APPEND);
	        }
	    }
	    
	    System.out.println("Linha removida com sucesso."); // Mensagem de sucesso
	    scanner.close();
	}
}

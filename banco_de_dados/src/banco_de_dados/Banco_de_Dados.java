package banco_de_dados;

import java.io.IOException;
import java.lang.Integer;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Banco_de_Dados {
	
	public String bd_info[];
	String arquivo_path = "src\\banco_de_dados\\bd_root\\";
	Infos_Config get = new Infos_Config();
	public static Scanner sc = new Scanner(System.in);
	
	public void inicializar() throws IOException{
		
		try {
			bd_info = get.get_bdroot();
			if(bd_info[0].equals("-1")) {
				System.out.println("ERROR");
			}
		}
		catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
	public void listar_databases() {
		System.out.println("");
		
		if(bd_info[0].equals("-2")) {
			System.out.println("No Databases Available");
		}
		else {
			int bd_total = (Integer.parseInt(bd_info[0]) * 2);
			for(int i = 1; i <= bd_total; i = i + 2) {
				System.out.println(bd_info[i]);
			}
		System.out.println("");
		}
	}
	
	public void excluir_database() {	
        System.out.println("Qual Banco de Dados você deseja excluir: "); //Pergunta ao usuário qual arquivo deseja excluir
        String bdExcluir = sc.nextLine(); //Lê o arquivo

        File arquivo = new File(arquivo_path + bdExcluir); //Instanciona um objeto do arquivo lido
        
        try {
            if (!arquivo.exists()) { // Verifica se o arquivo não existe
                System.out.println("Não existe nenhum arquivo com esse nome.");
                return;
            }
            System.out.println("Informe a senha do arquivo: "); //Se o arquivo existir, pergunta a senha para exclusão
            String senha = sc.nextLine();
            
            int i = 0;
            while(!bd_info[i].equals(bdExcluir)) { //Atualiza o índice do bd_info até que seja o que deseja excluir
            	i += 1;
            }
            
            String password = bd_info[i+1];
            if (senha.equals(password)) { // Verifica se a senha é igual a informada
                if (arquivo.delete()) { // Tenta excluir o arquivo
                    System.out.println("Arquivo excluído com sucesso.");
                } else {
                    System.out.println("Erro ao excluir o arquivo."); // Se "arquivo.delete()" der false imprime isso
                }
            } else {
                System.out.println("Senha incorreta. O arquivo não foi excluído."); //Se a senha inserida for incorreta imprime isso
            }
            
            // Alterar o bd_info
            
            String newBdInfo[] = new String[bd_info.length - 2];
            Path path = Path.of(arquivo_path + "bd_info");
            
            int aux = 0;
            for(int j = 0;j < bd_info.length; j++) {
            	if(j != i && j != i + 1) {
            		newBdInfo[aux] = bd_info[j];
            		aux += 1;
            	}
            }
            
            newBdInfo[0] = "" + (Integer.parseInt(newBdInfo[0]) - 1);
            bd_info = newBdInfo;

            for(int k = 0; k < bd_info.length; k++) {
            	if(k == 0) {
            		Files.writeString(path, newBdInfo[k] + "\n");
            	} else {
            		Files.writeString(path, newBdInfo[k] + "\n", StandardOpenOption.APPEND);
            	}
            }
            
        } catch (Exception e) {
            System.out.println(-1); // Trata exceções
            
       	 }
}
	public void excluirTabela(Scanner sc) {
	    System.out.println("Informe o banco de dados para excluir a tabela: ");
	    String nomeBD = sc.nextLine(); //Pede o nome do banco pra verifcar se existe a tabela
	
	    BancoDeDados banco = new BancoDeDados(nomeBD); // Cria um objeto que representa o banco de dados
	
	    if (!banco.existe()) { //Verifica se o banco de dados NÃO existe
	        System.out.println("Banco de dados não encontrado."); 
	        return; 
	    }

	    System.out.println("Informe o nome da tabela que deseja excluir: ");
	    String nomeTabela = sc.nextLine(); 
	
	    // Cria um objeto File que representa a tabela dentro do banco de dados
	    File tabela = new File(banco.getPath() + "\\" + nomeTabela + ".txt");
	
	    if (tabela.exists()) { // Verifica se a tabela existe
	        if (tabela.delete()) { // Tenta excluir a tabela
	            System.out.println("Tabela removida com sucesso."); 
	        } else {
	            System.out.println("Erro ao tentar excluir a tabela."); 
	        }
	    } else {
	        System.out.println("Tabela não encontrada neste banco de dados.");
	    }
	}

	public void excluirLinha(Scanner sc) {
	    System.out.println("Informe o nome da tabela: ");
	    String nomeTabela = sc.nextLine(); 
	
	    // Define o caminho do arquivo da tabela dentro do banco de dados
	    Path pathTabela = Paths.get("src", "banco_de_dados", "bd_root", nomeTabela + ".txt");
	
	    if (!Files.exists(pathTabela)) { // Verifica se a tabela NÃO existe
	        System.out.println("Tabela não encontrada."); 
	        return; 
	    }
	
	    System.out.println("Informe o conteúdo da linha que deseja excluir: "); //Se existir...
	    String linhaExcluir = sc.nextLine(); // Lê o conteúdo exato da linha que será removida
	
	    try {
	        // Conta quantas linhas existem na tabela e armazena esse número
	        int quantLinhas = (int) Files.lines(pathTabela).count();
	        String[] linhas = new String[quantLinhas];
	
	        // Abre o arquivo e armazena cada linha no array "linhas"
	        try (Scanner scanner = new Scanner(pathTabela)) {
	            int i = 0; 
	            while (scanner.hasNextLine()) { // Enquanto houver linhas no arquivo para serem lidas...
	                linhas[i++] = scanner.nextLine(); // Armazena a linha no array
	            }
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
	
	    } catch (Exception e) {
	        System.out.println("Erro ao atualizar a tabela."); // Exibe erro caso algo falhe
	    }
	}

	public void createDB() throws Exception {
		
		String nameDB; // Nome database
		String password; // Senha
		String pathDB; // Caminho do database
		String pathInfo; // Caminho do arquivo txt info da pasta das databases
		
		System.out.println("Digite o nome do Database: ");
		nameDB = sc.nextLine(); // Pede o nome da database
		
		int count = 0;
		for(int j = 1; j < bd_info.length; j += 2) {
			if(bd_info[j].equals(nameDB)) {
				count += 1;
			}
		}
		
		while(count != 0) {
				
			System.out.println("Database ja existente.");
				
			System.out.println("Digite o nome do Database: ");
			nameDB = sc.nextLine();
			
			count = 0;
			for(int j = 0; j < bd_info.length; j++) {
				if(bd_info[j].equals(nameDB)) {
					count += 1;
				}
			}
		} 
		
		pathDB = arquivo_path + nameDB + "\\";
		pathInfo = pathDB + "\\" + "tabela_info.txt";
		
		File database = new File(pathDB); // Objeto do tipo File para trabalhar com a database
			
		// Caso não exista um database com nome identico ao que foi digitado, pede a senha do database ao usuario e cria a database
		System.out.print("Digite sua senha: ");
		password = sc.nextLine();
		database.mkdir();
		System.out.println("Database criado com sucesso.");

		File info = new File(pathInfo); // Objeto do tipo File para trabalhar com o arquivo info da pasta das databasess
		
		//Cria um arquivo info para a pasta das databases
		try {
			info.createNewFile();
		}
		catch (Exception e) {
			System.out.println("Erro ao criar o arquivo.");
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(pathInfo)); // Objeto do tipo BufferedWriter que sera usado para escrever no arquivo info
		
		// Escreve a quantidade de databases no arquivo info
		try {
			writer.write("0\n");
			writer.close();
		}
		catch (Exception e) {
			System.out.println("Erro ao escrever no arquivo arquivo.");
		}
		
		String newBdInfo[] = new String[bd_info.length + 2];
        Path path = Path.of(arquivo_path + "bd_info");
        
        for(int j = 0;j < bd_info.length; j++) {
        		newBdInfo[j] = bd_info[j];
        }
        
        newBdInfo[bd_info.length] = nameDB;
        newBdInfo[bd_info.length + 1] = password;
        
        newBdInfo[0] = "" + (Integer.parseInt(newBdInfo[0]) + 1);
        bd_info = newBdInfo;

        for(int k = 0; k < bd_info.length; k++) {
        	if(k == 0) {
        		Files.writeString(path, newBdInfo[k] + "\n");
        	} else {
        		Files.writeString(path, newBdInfo[k] + "\n", StandardOpenOption.APPEND);
        	}
        }	
	}
}

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
            while(!bd_info[i].equals(bdExcluir)) {
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

	public void escreverTabela(String nameDB, String nameTabela) throws Exception {
		
		String path = "src\\banco_de_dados\\bd_root\\" + nameDB + "\\" + nameTabela + ".txt"; // Caminho da tabela que será escrita
		BufferedWriter escrever = new BufferedWriter(new FileWriter(path,true)); // Classe usada para escrita na tabela

		// Variavel que será lida e passada para o metodo para ser escrita na tabela
		Scanner sc = new Scanner(System.in);
		String conteudo;
		
		System.out.println("Digite abaixo dessa linha: ");
		conteudo = sc.next();
		
		escrever.write(conteudo + " "); // Espaço que será incrementado na linha escrita para melhor organização da tabela
		System.out.println("Terminado");
		
		escrever.close();
		sc.close();
	}

	public void createFile(String dataBase) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		String path = "C:\\Users\\Élcia de Fátima\\OneDrive\\Área de Trabalho\\arquivostxt\\" + dataBase; // Caminho contcatenado com o local da database
		String nameTabela; // Nome da tabela
		
		System.out.println("Digite o nome da tabela: ");
		nameTabela = sc.nextLine(); // Nome da tabela sera escaneado do teclado
		
		path = "C:\\Users\\Élcia de Fátima\\OneDrive\\Área de Trabalho\\arquivostxt\\" + dataBase + "\\" + nameTabela + ".txt"; // Caminho contcatenado com o local da database e o nome da tabela
		
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
				
				path = "C:\\Users\\Élcia de Fátima\\OneDrive\\Área de Trabalho\\arquivostxt\\" + dataBase + "\\" + nameTabela + ".txt";
				file = new File(path);
			}
			// Apos o usuario digitar o nome da tabela corretamente, o arquivo será criado
			file.createNewFile();
		}
		
		System.out.println("Tabela criada com sucesso");
		sc.close();
	}
}

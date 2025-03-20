package banco_de_dados;

import java.io.IOException;
import java.io.File;
import java.lang.Integer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class Banco_de_Dados {
	
	public static String bd_info[];
	
	public void inicializar() throws IOException{
		Get_Infos get = new Get_Infos();
		
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
	public void excluir_database(Scanner sc) {
        System.out.println("Qual arquivo você deseja excluir: "); //Pergunta ao usuário qual arquivo deseja excluir
        String caminho = sc.nextLine(); //Lê o arquivo

        File arquivo = new File(caminho); //Instanciona um objeto do arquivo lido

        try {
            if (!arquivo.exists()) { // Verifica se o arquivo não existe
                System.out.println("Não existe nenhum arquivo com esse nome.");
                return;
            }
            System.out.println("Informe a senha do arquivo: "); //Se o arquivo existir, pergunta a senha para exclusão
            String senha = sc.nextLine();

            if (senha.equals(password)) { // Verifica se a senha é igual a informada
                if (arquivo.delete()) { // Tenta excluir o arquivo
                    System.out.println("Arquivo excluído com sucesso.");
                } else {
                    System.out.println("Erro ao excluir o arquivo."); // Se "arquivo.delete()" der false imprime isso
                }
            } else {
                System.out.println("Senha incorreta. O arquivo não foi excluído."); //Se a senha inserida for incorreta imprime isso
            }
        } catch (Exception e) {
            System.out.println(-1); // Trata exceções
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
}

	public void createDB() throws Exception {
		
		Scanner sc = new Scanner(System.in);
		String nameDB; // Nome database
		String password; // Senha
		String nameFile; // Nome do arquivo
		String path; // Caminho da pasta onda os databases estão
		String pathDB; // Caminho do database
		String pathInfo; // Caminho do arquivo txt info da pasta das databases
		String pathInfoDB; // Caminho do arquivo txt info do database especifica
		int quantity; // Quantidade de arquivos que serão criados em cada database
		int i;
		
		System.out.println("Digite o nome do Database: ");
		nameDB = sc.nextLine(); // Pede o nome da database
		
		path = "banco_de_dados\\src\\banco_de_dados\\bd_root";
		pathDB = "banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + nameDB;
		pathInfo = "banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + "bd_info.txt";
		pathInfoDB = "banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + nameDB + "\\" + "tabela_info.txt";
		
		File j = new File(path); // Objeto do tipo File para chamar o metodo listFiles()
		File database = new File(pathDB); // Objeto do tipo File para trabalhar com a database
		File info = new File(pathInfo); // Objeto do tipo File para trabalhar com o arquivo info da pasta das databases
		File infoDB = new File(pathInfoDB); // Objeto do tipo File para trabalhar com o arquivo info da database especifica
		BufferedWriter writer = new BufferedWriter(new FileWriter(pathInfo)); // Objeto do tipo BufferedWriter que sera usado para escrever no arquivo info
		
		//Cria um arquivo info para a pasta das databases
		try {
			info.createNewFile();
		}
		catch (Exception e) {
			System.out.println("Erro ao criar o arquivo.");
		}
		
		// Caso exista um database com nome identico ao que foi digitado, pede ao usuario um outro nome para o database
		if(database.exists()) { 
			
			while(database.exists()) {
				
				System.out.println("Database ja existente.");
				
				System.out.println("Digite o nome do Database: ");
				nameDB = sc.nextLine();
				
				pathDB = "banco_de_dados\\src\\banco_de_dados\\bd_root" + nameDB;
				pathInfo = "banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + "bd_info.txt";
				pathInfoDB = "banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + nameDB + "\\" + "tabela_info.txt";
				
				database = new File(pathDB);
				info = new File(pathInfo);
				infoDB = new File(pathInfoDB);
			}
		}
			
		// Caso não exista um database com nome identico ao que foi digitado, pede a senha do database ao usuario e cria a database
		System.out.print("Digite sua senha: ");
		password = sc.nextLine();
		database.mkdir();
		System.out.println("Database criado com sucesso.");
		
		// Diz a quantidade de database que existe na pasta das databases
		i = j.listFiles().length;
		i--;
		
		// Escreve a quantidade de databases no arquivo info
		try {
			writer.write(Integer.toString(i));
			writer.close();
		}
		catch (Exception e) {
			System.out.println("Erro ao escrever no arquivo arquivo.");
		}
		
		// Pede o numero de arquivos que serão criados em cada database
		System.out.println("Digite o numero de tabelas: ");
		quantity = sc.nextInt();
		
		sc.nextLine();
		
		// Cria a quantidade de arquivos que foi solicitado
		for(i=0;i<quantity;i++) {
			File file = new File("banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + nameDB);
			
			System.out.println("Digite o nome do arquivo: ");
			nameFile = sc.nextLine();
			
			pathDB = "banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + nameDB + "\\" + nameFile + ".txt";
			file = new File(pathDB);
			
			try {
				infoDB.createNewFile();
			}
			catch (Exception e) {
				System.out.println("Erro ao criar o arquivo.");
			}
			
			// Caso seja criado o arquivo é exibido uma mensagem de sucesso
			try {
				
				if(file.createNewFile()) {
					System.out.println("Arquivo criado com sucesso.");
				}
				
				// Caso ja exista um arquivo com o mesmo nome, pede outro nome de arquivo para o usuario
				else {
					while(file.createNewFile()==false) {
						System.out.println("Arquivo ja existente.");
						
						System.out.println("Digite o nome do arquivo: ");
						nameFile = sc.nextLine();
						
						pathDB = "banco_de_dados\\src\\banco_de_dados\\bd_root" + "\\" + nameDB + "\\" + nameFile + ".txt";
						file = new File(pathDB);
					}
					
					System.out.println("Arquivo criado com sucesso.");
				}
			}
			catch(Exception e) {
				System.out.println("Erro ao criar o arquivo.");
			}
		}
	sc.close();
	}

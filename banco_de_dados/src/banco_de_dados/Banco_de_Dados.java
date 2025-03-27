package banco_de_dados;

import java.io.IOException;
import java.lang.Integer;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Banco_de_Dados {
	
	public String bd_info[];
	String arquivo_path = "src\\banco_de_dados\\bd_root\\";
	Get_Infos get = new Get_Infos();
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
}

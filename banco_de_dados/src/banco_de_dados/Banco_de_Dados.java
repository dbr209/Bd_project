package banco_de_dados;

import java.io.IOException;
import java.lang.Integer;

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
	    public static void excluir_database(Scanner leitor) {
        System.out.println("Qual arquivo você deseja excluir: "); //Pergunta ao usuário qual arquivo deseja excluir
        String caminho = leitor.nextLine(); //Lê o arquivo

        File arquivo = new File(caminho); //Instanciona um objeto do arquivo lido

        try {
            if (!arquivo.exists()) { // Verifica se o arquivo não existe
                System.out.println("Não existe nenhum arquivo com esse nome.");
                return;
            }
            System.out.println("Informe a senha do arquivo: "); //Se o arquivo existir, pergunta a senha para exclusão
            String senha = leitor.nextLine();

            if (senha.equals("teste1234!")) { // Verifica se a senha é igual a informada
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

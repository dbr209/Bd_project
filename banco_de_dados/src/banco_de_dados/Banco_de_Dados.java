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
	public void excluir_database(Scanner leitor){
		System.out.println("Informe qual arquivo você deseja excluir: ");
		String caminho = leitor.nextLine();

		File arquivo = new File(caminho); //Instanciona um novo objeto file que busca o caminho do arquivo
		try {
			if(!arquivo.exists()){ //Verifca se o arquivo não existe, pois está com "!" de negação
				System.out.println("Não existe arquivo com esse nome.");
				return;
			}
			System.out.println("Você deseja realmente excluir esse arquivo?(sim/não): ");
			String confirmacao = leitor.nextLine();

			if(confirmacao.equalsIgnoreCase("sim")){ //Verifica se o usuário digitou "sim" e ignora se foi com letra maiúscula ou minúscula
				System.out.println("Insira a senha para remover: ");
				String senha = leitor.nextLine();

				if(senha.equals("teste1234!")){ //Verifica se o usuário digitou "teste1234!", se sim ele deleta o arquivo e informa ao usuário
					arquivo.delete();
					System.out.println("Arquivo excluído.");
				}else{
					System.out.println("Senha incorreta, não foi excluído.");
				}
			}
			else{
			System.out.println("Exclusão cancelada.");
			}

		}catch (Exception e) { //Trata de possíveis excessões no código
			System.out.println("-1");
		}
		leitor.close();
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

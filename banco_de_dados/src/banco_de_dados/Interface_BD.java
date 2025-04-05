package banco_de_dados;

import java.io.IOException;
import java.util.Scanner;

public class Interface_BD {
	public void interfacebd() throws IOException, Exception {
		
		Scanner leitor = new Scanner(System.in);
		
		Banco_de_Dados bd = new Banco_de_Dados();
		bd.inicializar();
		
		boolean sair = false;
		do {
			System.out.println("========== Banco de Dados ==========");
			
			bd.listar_databases();
			
			System.out.print("1. Criar Database\n2. Excluir Database\n3. Acessar Database\n0. Sair\n\n-> ");
			String op = leitor.nextLine();
			
			switch(op) {
				case "1":
					bd.createDB();
					break;
				case "2":
					bd.excluir_database();
					break;
				case "3":
					bd.acessarDB();
					break;
				case "0":
					sair = true;
					break;
				default:
					System.out.println("Opcao Invalida");
			}
		} while(sair == false);
		
		leitor.close();
	}
}

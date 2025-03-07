package banco_de_dados;

import java.io.IOException;
import java.util.Scanner;

public class Interface_BD {
	public static void main(String[] args) throws IOException{
		
		System.out.println("===== Banco de Dados =====");
		
		Scanner leitor = new Scanner(System.in);
		
		Banco_de_Dados bd = new Banco_de_Dados();
		bd.inicializar();
		
		boolean sair = false;
		do {
			System.out.print("1. Criar Database\n2. Excluir Database\n3. Acessar Database\n4. Listar Databases\n0. Sair\n\n-> ");
			int op = leitor.nextInt();
			
			switch(op) {
				case 1:
					System.out.print(false);
					break;
				case 2:
					bd.excluir_database();
					break;
				case 3:
					System.out.print(false);
					break;
				case 4:
					bd.listar_databases();
					break;
				case 0:
					sair = true;
					break;
				default:
					leitor.close();
			}
		} while(sair == false);
		
		leitor.close();
	}
}

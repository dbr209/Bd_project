package banco_de_dados;

import java.util.Scanner;

public class Interface_BD {
	public static void main() {
		
		System.out.println("===== Banco de Dados =====");
		System.out.print("1. Criar Database\n2.Excluir Database\n3.Acessar Database\n4. Listar Databases\n0. Sair\n\n-> ");
		
		Scanner leitor = new Scanner(System.in);
		int op = leitor.nextInt();
		
		switch(op) {
			case 1:
				
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				leitor.close();
				main();
		
		}
		
		
		leitor.close();
	}
}

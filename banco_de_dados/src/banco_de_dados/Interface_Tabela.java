package banco_de_dados;

import java.io.IOException;
import java.util.Scanner;

public class Interface_Tabela {
	
	public void interfaceTBL(String nameDB, Scanner sc) throws IOException {
		
		Tabela tbl = new Tabela();
		tbl.inicializar(nameDB, sc);
		
		boolean sair = false;
		do {
			System.out.println("========== TABELAS ==========");
			tbl.listarTables();
			System.out.print("1. Criar Tabela\n2. Excluir Tabela\n3. Acessar Tabela\n0. Sair\n\n-> ");
			String op = sc.nextLine();
			
			switch(op) {
				case "1":
					tbl.createTable();
					break;
				case "2":
					tbl.excluirTable();
					break;
				case "3":
					tbl.acessarTable();
					break;
				case "0":
					sair = true;
					break;
				default:
					System.out.println("Opcao Invalida");
			}
		} while(sair == false);
	}
}

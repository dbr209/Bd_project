package banco_de_dados;

public class Banco_de_Dados {
	public static void main(String[] args) {
		Get_Infos get = new Get_Infos();
		String dt = get.get_bdroot();
		System.out.print(dt);
	}
}

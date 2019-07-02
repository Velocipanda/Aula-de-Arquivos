import java.io.RandomAccessFile;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws Exception { 
        BTree<String, String> st = new BTree<String, String>();
        
		String linha, nis, achado;
		String colunas[];
		RandomAccessFile f = new RandomAccessFile("bolsa.csv", "r");
		f.readLine();
		while(f.getFilePointer() < f.length()){
			linha = f.readLine();
			colunas = linha.split("\t");
			nis = colunas[7];
			st.put(nis, linha);
		}
		
		System.out.println("Digite o NIS para pesquisa: ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		final long startTime = System.nanoTime();
		st.get(input);
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		achado = st.get(input);
		System.out.println(achado);
		achado = st.get(input);
		System.out.println("Tempo em nanosegundos: "+totalTime);
		f.close();
		scan.close();

    }
}


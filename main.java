import java.io.RandomAccessFile;
import java.util.Scanner;
 
public class main {
 
    public static void main(String[] args) throws Exception 
    {
    	int count = 0;
    	long atual, primeiro, ultimo, meio;
    	boolean achou = false;
        RandomAccessFile f = new RandomAccessFile("cep_ordenado.dat", "r");
        Endere�o e = new Endere�o();
        Scanner scan = new Scanner(System.in);
        f.seek(f.length());
        atual = f.getFilePointer();
        System.out.println("Tamanho do Arquivo: "+atual);
        System.out.println("Tamanho do Registro: "+300);
        System.out.println("Tamanho do Arquivo em Registros: "+atual/300);
        primeiro = 0;
        ultimo = (atual/300)-1;
        System.out.println("Digite o cep a ser pesquisado, ele dever� ter 8 n�meros.");
        String cepentra = scan.nextLine();
    	while(primeiro<=ultimo && achou == false){
    		count++;
    		meio=(primeiro+ultimo)/2;
    		f.seek(meio*300);
    		System.out.println("Posicao Atual: "+ f.getFilePointer()/300);
    		e.leEndereco(f);
    		long cepentranum = Long.parseLong(cepentra);
    		long cepcompnum = Long.parseLong(e.getCep());
    		if(cepentranum<cepcompnum)
    			ultimo = meio-1;
    			else if (cepentranum>cepcompnum)
    				primeiro = meio +1 ;
    				else if (cepentranum==cepcompnum)
    					achou = true;
    	}
    	System.out.println("N�mero de procuras realizadas; "+count);
    	if(achou) {
    		System.out.println("O cep pesquisado foi encontrado:");
    		System.out.println(f.getFilePointer()); 
    		e.leEndereco(f);
    		System.out.println(e.getLogradouro());
    		System.out.println(e.getBairro());
    		System.out.println(e.getCidade());
    		System.out.println(e.getEstado());
    		System.out.println(e.getSigla());
    		System.out.println(e.getCep());
    	}
    		else {
    			System.out.println("O cep pesquisado n�o foi encontrado.");
    		}
    	scan.close();
    }
}
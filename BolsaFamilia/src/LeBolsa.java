import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


class ComparaInd implements Comparator<ElementoIndice>
{
    public int compare(ElementoIndice i1, ElementoIndice i2)
    {
        return i1.getNis().compareTo(i2.getNis());
    }
}

public class LeBolsa
{
	public static void main(String args[]) throws Exception
	{
		String linha, nis;
		String colunas[];
		long posicao;
		int ind = 0;
		RandomAccessFile f = new RandomAccessFile("bolsa.csv", "r");
		RandomAccessFile indice = new RandomAccessFile("indice.ind", "rw");
		List<ElementoIndice> lista = new ArrayList<>();
		f.readLine();
		ElementoIndice indprocu = new ElementoIndice();
		while(f.getFilePointer() < f.length()){
			posicao = f.getFilePointer();
			linha = f.readLine();
			colunas = linha.split("\t");
			nis = colunas[7];
			System.out.println("NIS => " + nis + " esta na posicao " + posicao);
			lista.add(new ElementoIndice());
			lista.get(ind).setNis(nis);
			lista.get(ind).setPosicao(posicao);
			ind++;
		}
		Collections.sort(lista, new ComparaInd());
		for(ElementoIndice i: lista)
        {
            i.escreve(indice);
        }
		
		//Busca
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Qual o item a ser pesquisado?");
		long procurando = scan.nextLong();
    	long atual, primeiro = 0, ultimo, meio;
    	boolean achou = false;
    	int count = 0;
    	indice.seek(indice.length());
    	atual = indice.getFilePointer();
    	ultimo = (atual/24)-1;
		while(primeiro<=ultimo && achou == false){
    		count++;
    		meio=(primeiro+ultimo)/2;
    		indprocu = new ElementoIndice();
    		indice.seek(meio*24);
    		indprocu.le(indice);
    		long niscmp = Long.parseLong(indprocu.getNis());
    		if(procurando < niscmp)
    			ultimo = meio-1;
    			else if (procurando>niscmp)
    				primeiro = meio +1 ;
    				else if (procurando==niscmp) {
    					achou = true;
    					f.seek(indprocu.getPosicao());
    					linha = f.readLine();
    					colunas = linha.split("\t");
    					System.out.println("O item foi encontrado:");
    					for(int i = 0; i<colunas.length; i++) {
    						System.out.println(colunas[i]);
    					}
    				}
    	}
		if(!achou) {
			System.out.println("O item nao foi encontrado.");
		}
		
		indice.close();
		f.close();
		scan.close();
	}

}
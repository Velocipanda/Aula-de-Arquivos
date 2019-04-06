import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class ComparaCEP implements Comparator<Endereco>
{
	public int compare(Endereco e1, Endereco e2)
	{
		return e1.getCep().compareTo(e2.getCep());
	}
}

public class MainPrincipal {
	public static void main(String[] args) throws Exception 
	{
		//Variáveis
		RandomAccessFile f = new RandomAccessFile("cep.dat", "r");
		ComparaCEP comp = new ComparaCEP();
		int contador = 0;

		ArrayList<File> listafile = new ArrayList<>();
		ArrayList<RandomAccessFile> lista = new ArrayList<>();
		int k = 0;
		int quantoscep = (int)(f.length())/(8*300);
		
		//Criando os 8 arquivos
		for(int i=0;i<8;i++) {
			listafile.add(new File("cep"+i+".dat"));
			lista.add(new RandomAccessFile(listafile.get(i), "rw"));
			ArrayList<Endereco> listaend = new ArrayList<>();
			for(int j=0;j<quantoscep && f.getFilePointer()<f.length();j++) {
				Endereco e3 = new Endereco();
				f.seek(k*300);
				k++;
				e3.leEndereco(f);
				listaend.add(e3);
				if(j==5) {
					quantoscep++;
				}
			}
			Collections.sort(listaend, new ComparaCEP());
			for(Endereco e: listaend)
			{
			e.escreveEndereco(lista.get(i));
			}
		}
		f.close();
		
		//Criando os novos arquivos para ordenar
		for(int i = 0; i<7; i++) {
			int procura1 = 0;
			int procura2 = 0;
			listafile.add(new File("cep"+(i+8)+".dat"));
			lista.add(new RandomAccessFile(listafile.get(i+8), "rw"));
			lista.get(contador).seek(0);
			lista.get(contador+1).seek(0);
			//Intercalando os CEP's
			while( lista.get(contador).getFilePointer() < lista.get(contador).length() && lista.get(contador+1).getFilePointer() < lista.get(contador+1).length() ){ 
				lista.get(contador).seek(procura1*300);
				lista.get(contador+1).seek(procura2*300);
				Endereco e1 = new Endereco();
				Endereco e2 = new Endereco();
				e1.leEndereco(lista.get(contador));
				e2.leEndereco(lista.get(contador+1));
				if(comp.compare(e1, e2)<0) {
					e1.escreveEndereco(lista.get(i+8));
					procura1++;
				}
				else if(comp.compare(e1, e2)>0) {
					e2.escreveEndereco(lista.get(i+8));
					procura2++;			
				}
			}
			if (lista.get(contador).getFilePointer() >= lista.get(contador).length()){
				while(lista.get(contador+1).getFilePointer() < lista.get(contador+1).length()) {
					Endereco e2 = new Endereco();
					lista.get(contador+1).seek(procura2*300);
					e2.leEndereco(lista.get(contador+1));
					e2.escreveEndereco(lista.get(i+8));
					procura2++;
				}
			}
			else if(lista.get(contador+1).getFilePointer() >= lista.get(contador+1).length() ) {
				while(lista.get(contador).getFilePointer() < lista.get(contador).length()) {
					Endereco e1 = new Endereco();
					lista.get(contador).seek(procura1*300);
					e1.leEndereco(lista.get(contador));
					e1.escreveEndereco(lista.get(i+8));
					procura1++;
				}
			}
			lista.get(contador).close();
			lista.get(contador+1).close();
			listafile.get(contador).delete();
			listafile.get(contador+1).delete();
			contador+=2;
		}
	} 
}
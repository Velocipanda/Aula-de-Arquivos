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
		int procura1 = 0;
		int procura2 = 0;
		ArrayList<RandomAccessFile> lista = new ArrayList<>();
		int k = 0;
		int quantoscep = (int)(f.length())/(8*300);
		
		/*//Criando o arquivo com 80(TESTE)
		f = new RandomAccessFile("cep80.dat","rw");
		for(int i=0;i<80;i++) {
			Endereco e = new Endereco();
			f.seek(i*300);
			e.leEndereco(f);
			e.escreveEndereco(saida);
		} */
		//f = new RandomAccessFile("cep.dat", "r");
		
		//Criando os 8 arquivos
		for(int i=0;i<8;i++) {
			lista.add(new RandomAccessFile("cep"+i+".dat", "rw"));
			ArrayList<Endereco> listaend = new ArrayList<>();
			for(int j=0;j<quantoscep && f.getFilePointer()<f.length();j++) {
				Endereco e3 = new Endereco();
				f.seek(k*300);
				k++;
				e3.leEndereco(f);
				listaend.add(e3);
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
			File fprim = new File("cep"+contador+".dat");
			File fsegun = new File("cep"+(contador+1)+".dat");
			RandomAccessFile prim = new RandomAccessFile(fprim, "rw");
			RandomAccessFile segun = new RandomAccessFile(fsegun, "rw");
			lista.add(new RandomAccessFile("cep"+(i+8)+".dat", "rw"));
			prim.seek(0);
			segun.seek(0);
			//Intercalando os CEP's
			while( prim.getFilePointer() < prim.length() && segun.getFilePointer() < segun.length() ){ 
				prim.seek(procura1*300);
				segun.seek(procura2*300);
				Endereco e1 = new Endereco();
				Endereco e2 = new Endereco();
				e1.leEndereco(prim);
				e2.leEndereco(segun);
				if(comp.compare(e1, e2)<0) {
					e1.escreveEndereco(lista.get(i+8));
					procura1++;
				}
				else if(comp.compare(e1, e2)>0) {
					e2.escreveEndereco(lista.get(i+8));
					procura2++;			
				}
			}
			if (prim.getFilePointer() >= prim.length()){
				while(segun.getFilePointer() < segun.length()) {
					Endereco e2 = new Endereco();
					segun.seek(procura2*300);
					e2.leEndereco(segun);
					e2.escreveEndereco(lista.get(i+8));
					procura2++;
				}
			}
			else if(segun.getFilePointer() >= segun.length() ) {
				while(prim.getFilePointer() < prim.length()) {
					Endereco e1 = new Endereco();
					prim.seek(procura1*300);
					e1.leEndereco(prim);
					e1.escreveEndereco(lista.get(i+8));
					procura1++;
				}
			}
			procura1= 0;
			procura2= 0;
			prim.close();
			segun.close();
			fprim.delete();
			fsegun.delete();
			contador+=2;
		}
	} 
}

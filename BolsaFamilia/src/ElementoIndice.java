import java.io.DataInput;
import java.io.DataOutput;

class ElementoIndice {
  private String nis;
  private long posicao;
  
  ElementoIndice() {}
  
  void escreve(DataOutput paramDataOutput) throws java.io.IOException {
    paramDataOutput.writeUTF(nis);
    paramDataOutput.writeLong(posicao);
  }
  
  void le(DataInput paramDataInput) throws java.io.IOException {
	  this.nis = paramDataInput.readUTF();
	  this.posicao = paramDataInput.readLong();
  }

public String getNis() {
	return nis;
}

public void setNis(String nis) {
	this.nis = nis;
}

public long getPosicao() {
	return posicao;
}

public void setPosicao(long posicao) {
	this.posicao = posicao;
}
}
import java.io.*;
import java.util.*;

public class k7 extends Support {

	private int idK7;

	public k7(){}

	public k7(int idBlueRay, int idSupport, Film film, boolean disponible){
		super(idSupport, film, disponible);
		this.idK7 = idK7;
	}

	public int getIdK7(){ return this.idK7; }
	public void setIdK7(int idK7){ this.idK7 = idK7; }
}
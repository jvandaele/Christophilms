import java.io.*;
import java.util.*;

public class BlueRay extends Support {

	private int idBlueRay;

	public BlueRay(){}

	public BlueRay(int idBlueRay, int idSupport, Film film, boolean disponible){
		super(idSupport, film, disponible);
		this.idBlueRay = idBlueRay;
	}

	public int getIdBlueRay(){ return this.idBlueRay; }
	public void setIdBlueRay(int idBlueRay){ this.idBlueRay = idBlueRay; }
}
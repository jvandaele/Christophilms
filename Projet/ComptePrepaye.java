import java.io.*;
import java.util.*;

public class ComptePrepaye extends MoyenDePaiement {

	private double solde;

	public ComptePrepaye(){}

	public ComptePrepaye(double montant, String libelle){
		super(montant, libelle);
	}

	public double getSolde(){ return this.solde; }
	public void setSolde(double solde){ this.solde = solde; }
}
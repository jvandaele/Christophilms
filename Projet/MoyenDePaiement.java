import java.io.*;
import java.util.*;

public class MoyenDePaiement {

	private double montant;
	private String libelle;

	public MoyenDePaiement(){}

	public MoyenDePaiement(double montant, String libelle){
		this.montant = montant;
		this.libelle = libelle;
	}

	public double getMontant(){ return this.montant; }
	public void setMontant(double montant){ this.montant = montant; }
	public String getLibelle(){ return this.libelle; }
	public void setLibelle(String libelle){ this.libelle = libelle; }
}
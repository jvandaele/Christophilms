import java.io.*;
import java.util.*;
import java.text.*;
import java.util.concurrent.TimeUnit;

public class Agence {

	private Scanner input = new Scanner(System.in);

	private String nomAgence;
	private String[] moyensDePaiementDisponibles;
	private ArrayList<Genre> genres = new ArrayList<Genre>();
	private ArrayList<Categorie> categories = new ArrayList<Categorie>();
	private ArrayList<Film> films = new ArrayList<Film>();
	private ArrayList<Client> clients = new ArrayList<Client>();
	private ArrayList<Location> locations = new ArrayList<Location>();
	private HashMap<Client, ComptePrepaye> comptes = new HashMap<Client,ComptePrepaye>();

	public Agence(){}

	public Agence(
		String nomAgence,
		String[] moyensDePaiementDisponibles,
		ArrayList<Genre> genres,
		ArrayList<Categorie> categories,
		ArrayList<Film> films,
		ArrayList<Client> clients
		)
	{
		this.nomAgence = nomAgence;
		this.moyensDePaiementDisponibles = moyensDePaiementDisponibles;
		this.genres = genres;
		this.categories = categories;
		this.films = films;
		this.clients = clients;
	}


	public void demo(){
		boolean demoOn = true;
		System.out.println("Bienvenue chez '" + this.nomAgence + "' !");
		do {
			System.out.println("Selectionner l'opération que vous souhaitez effectuer");
			System.out.println("0 - Simuler un Client\n1 - Consulter des statistiques\n2 - Afficher toutes les locations\n3 - Afficher tous les clients enregistrés");
			int choixStatCli = input.nextInt();
			input.nextLine();
			switch(choixStatCli){
				case 0:
					Client client = choixDuClient();
					System.out.println("----------------------------------------------------");
					System.out.println(client.getPrenom() + " " + client.getNom() + " va effectuer une action :");
					System.out.println("0 - Louer\n1 - Rendre\n2 - Gèrer mon compte prépayé\n3 - Gagner de l'argent");
					int choixlouerRendre = input.nextInt();
					input.nextLine();
					switch(choixlouerRendre){
						case 0:
							ArrayList<Film> filmsVoulus = selectionFilmsClient();
							ArrayList<Integer> formatsVoulus = selectionFormats(filmsVoulus);
							ArrayList<Support> supportsDisponibles = verificationDisponibilite(filmsVoulus, formatsVoulus);
							int confirmation = confirmation(supportsDisponibles);
							if(confirmation == 0){
								System.out.println("Voulez-vous prendre ces films maintenant ou les réserver pour une date antérieure ?");
								System.out.println("0 - Prendre\n1 - Reserver");
								int choixReserve = input.nextInt();
								input.nextLine();
								Date dateDebut = new Date();
								if(choixReserve == 1){
									System.out.println("Veuillez selectionner le nombre de jours avant votre réservation");
									int nbJours = input.nextInt();
									input.nextLine();
									dateDebut = addDays(dateDebut, nbJours);
								}
								ArrayList<Integer> dureesVoulues = dureesVoulues(supportsDisponibles);
								this.locations.add(client.louer(dateDebut, supportsDisponibles, dureesVoulues));
								this.locations.get(this.locations.size()-1).setIdLocation(this.locations.size()-1);
								genererFacture(this.locations.get(this.locations.size()-1));
								System.out.println("Etablissement de la location terminée !");
								printLocation(this.locations.get(this.locations.size()-1));
								printSupportsClient(client);
							}
							break;

						case 1:
							Location location = selectionRenduLocation(client);
							if(location != null){
								ArrayList<Support> supportsDispos = selectionRenduSupports(client, location);
								boolean enCours = client.rendre(new Date(), location, supportsDispos);
								System.out.println("Rendu terminé !");
								if(!enCours){
									System.out.println("La location est terminé, veuillez selectionner le moyen de paiement :");
									System.out.println("0 - Espèces\n1 - Carte Banquaire\n2 - Chèque\n3 - Compte prépayé");
									int choixMDP = input.nextInt();
									input.nextLine();
									MoyenDePaiement mdp = new MoyenDePaiement();
									double montant = location.getFacture().getPrix();
									switch(choixMDP){
										case 0:
											mdp = new Especes(montant, "Espèces");
											location.getFacture().setMoyenDePaiement(mdp);
											break;

										case 1:
											mdp = new CarteBanquaire(montant, "Carte banquaire");
											location.getFacture().setMoyenDePaiement(mdp);
											break;

										case 2:
											mdp = new Cheque(montant, "Chèque");
											location.getFacture().setMoyenDePaiement(mdp);
											break;

										case 3:
											System.out.println("Avec le compte, si cette location se termine en avance, vous bénéficiez d'une réduction !");
											double reduction = (montant/calculDateHeures(location.getDateDebutLocation(), location.getDateRenduPrevueDernierSupport())/12) * calculDateHeures(new Date(), location.getDateRenduPrevueDernierSupport());
											System.out.println(reduction);
											if(reduction <= 0){ System.out.println("Malheureusment ce n'est pas le cas."); }
											else {
												System.out.println("En l'occurence, une déduction de " + reduction + "€ !");
												montant = montant - reduction;
												System.out.println("Au final, vous payez " + montant + "€ !");
											}
											mdp = new MoyenDePaiement(montant, "Compte prépayé");
											location.getFacture().setMoyenDePaiement(mdp);
											break;
									}
									client.setSolde(client.getSolde() - montant);
									location.getFacture().setPrix(montant);
								}
								printLocation(location);
								printSupportsClient(client);
							}
							else { System.out.println("Ce client n'a pas de location en cours"); }
							break;
						
						case 2:
							if(this.comptes.get(client) == null){
								System.out.println("Vous n'avez pas de compte prépayé, voulez-vous en créer un ?");
								System.out.println("0 - Oui\n1 - Non");
								int choixCPP = input.nextInt();
								input.nextLine();
								if(choixCPP == 0){
									this.comptes.put(client, new ComptePrepaye());
									System.out.println("Votre compte prépayé à bien été créé ! Voulez-vous y ajouter du solde ?");
									System.out.println("0 - Oui\n1 - Non");
									int choixADUS = input.nextInt();
									input.nextLine();
									if(choixADUS == 0){ ajouterDuSoldePrepaye(client); }
								}
							}
							else{
								ajouterDuSoldePrepaye(client);
							}
							break;

						case 3:
							System.out.println("Saisissez le montant que vous souhaitez gagner :");
							double montant = input.nextDouble();
							input.nextLine();
							System.out.println("Et hop ! " + montant + "€ apparaissent dans votre main ! (Totalement légal)");
							client.setSolde(client.getSolde() + montant);
							System.out.println("Votre solde actuel : " + client.getSolde() + "€");
							break;
					}
					break;

				case 1:
					statistiques();
					break;

				case 2:
					printAllLocations();
					break;

				case 3:
					printAllClients();
					break;
			}
			System.out.println("----------------------------------------------------");
			System.out.println("Voulez-vous effectuer effectuer une nouvelle opération ?");
			System.out.println("0 - Oui\n1 - Non");
			int choix = input.nextInt();
			input.nextLine();
			if(choix == 1) { demoOn = false; }
		} while(demoOn);

		System.out.println("Au revoir et à bientôt chez '" + this.nomAgence + "' !");
	}

	public Client choixDuClient(){
		System.out.println("----------------------------------------------------");
		System.out.println("Veuillez selectionner (avec le numéro correspondant) le client que vous souhaitez traiter :");
		for(int i=0;i<this.clients.size();i++){
			System.out.println(i + " - " + this.clients.get(i).getPrenom() + " " + this.clients.get(i).getNom());
		}
		int choix = input.nextInt();
		return this.clients.get(choix);
	}

	public ArrayList<Film> selectionFilmsClient(){
		System.out.println("----------------------------------------------------");
		ArrayList<Film> selectionFilms = new ArrayList<Film>();
		System.out.println("Selection des films voulus par le client.");
		boolean continuer = true;
		do {
			System.out.println("Veuillez selectionner un film parmi ceux-ci (-1 pour terminer la selection) : ");
			for(int i=0;i<this.films.size();i++){
				System.out.println(i + " - " + this.films.get(i).getTitre());
			}
			int choix = input.nextInt();
			input.nextLine();
			if(choix == -1) { continuer = false; }
			else { selectionFilms.add(this.films.get(choix)); }
		} while(continuer);
		return selectionFilms;
	}

	public ArrayList<Integer> selectionFormats(ArrayList<Film> filmsVoulus){
		System.out.println("----------------------------------------------------");
		System.out.println("Selection des formats voulus par le client.");
		System.out.println("Pour chaque film, entrez le numéro correspondant pour obtenir le format voulu :");
		System.out.println("0 - Peu importe\n1 - DVD\n2 - BlueRay\n3 - K7");
		ArrayList<Integer> formatsVoulus = new ArrayList<Integer>();
		for(int i=0;i<filmsVoulus.size();i++){
			System.out.println("Format désiré pour le film : " + filmsVoulus.get(i).getTitre() + " :");
			int choix = input.nextInt();
			input.nextLine();
			formatsVoulus.add(choix);
		}
		return formatsVoulus;
	}

	public ArrayList<Support> verificationDisponibilite(ArrayList<Film> filmsVoulus, ArrayList<Integer> formatsVoulus){
		System.out.println("----------------------------------------------------");
		ArrayList<Support> supportsDisponibles = new ArrayList<Support>();
		for(int i=0;i<filmsVoulus.size();i++){
			if(filmsVoulus.get(i).estDisponible(formatsVoulus.get(i)) != null){
				supportsDisponibles.add(filmsVoulus.get(i).estDisponible(formatsVoulus.get(i)));
			}
		}
		return supportsDisponibles;
	}

	public int confirmation(ArrayList<Support> supportsDisponibles){
		System.out.println("----------------------------------------------------");
		System.out.println("Après tris des supports disponibles, voici un récapitulatifs des films qui seront loués :");
		for(int i=0;i<supportsDisponibles.size();i++){
			System.out.println("-" + supportsDisponibles.get(i).getFilm().getTitre());
		}
		System.out.println("Voulez-vous effectuer une locations avec ces films ? :");
		System.out.println("0 - Oui\n1 - Renoncer");
		int choix = input.nextInt();
		input.nextLine();
		return choix;
	}

	public ArrayList<Integer> dureesVoulues(ArrayList<Support> supportsDisponibles){
		System.out.println("----------------------------------------------------");
		System.out.println("Veuillez saisir le nombre d'heures d'emprunt pour chaque film : ");
		ArrayList<Integer> dureesVoulues = new ArrayList<Integer>();
		for(int i=0;i<supportsDisponibles.size();i++){
			System.out.println(supportsDisponibles.get(i).getFilm().getTitre() + " :");
			dureesVoulues.add(input.nextInt());
			input.nextLine();
		}
		return dureesVoulues;
	}

	public void genererFacture(Location location){
		System.out.println("----------------------------------------------------");
		System.out.println("Indiquez le prix de cette location :");
		double prix = input.nextDouble();
		input.nextLine();
		location.genererFacture(prix);
	}

	public Location selectionRenduLocation(Client client){
		boolean verif = false;
		for(int i=0;i<client.getLocationsEffectuees().size();i++){
			if(client.getLocationsEffectuees().get(i).getEnCours()){
				verif = true;
				break;
			}
		}
		if(verif){
			System.out.println("Veuillez selectionner le numéro de location auquel le client est rattaché :");
			for(int i=0;i<client.getLocationsEffectuees().size();i++){
				if(client.getLocationsEffectuees().get(i).getEnCours()){
					printLocation(client.getLocationsEffectuees().get(i));
				}
			}
			int choixLocation = input.nextInt();
			input.nextLine();
			return client.getLocationsEffectuees().get(choixLocation);
		}
		return null;
	}

	public ArrayList<Support> selectionRenduSupports(Client client, Location location){
		ArrayList<Support> supportsRendus = new ArrayList<Support>();
		System.out.println("Parmi les supports suivants, veuillez selectionner ceux que vous voulez rendre (-1 pour terminer) : ");
		int choix = 0;
		do {
			printSupportsClientLocation(client, location);
			choix = input.nextInt();
			input.nextLine();
			if(choix != -1) { supportsRendus.add(client.getSupportsEmpruntes().get(choix)); }
		} while(choix != -1);
		return supportsRendus;
	}

	public void statistiques(){
		boolean continuer = true;
		do {
			System.out.println("Veuillez choisir une catégorie parmi celles-ci : ");
			for(int i=0;i<this.categories.size();i++){
				System.out.println(i + " - " + this.categories.get(i).getLibelle());
			}
			int choixCat = input.nextInt();
			input.nextLine();
			System.out.println("Veuillez selectionner une statistique parmi celles-ci : ");
			System.out.println("0 - Dénombrement par support");
			System.out.println("1 - Dénombrement par genre");
			int choixStat = input.nextInt();
			input.nextLine();
			switch(choixStat){
				case 0:
					denombrementSupport(this.categories.get(choixCat));
					break;
				case 1:
					denombrementGenre(this.categories.get(choixCat));
					break;
				default:
					System.out.println("Bravo ! Vous avez découvert un secret caché !");
					break;
			}
			System.out.println("Voulez-vous selectionner une autre statistique ? : \n0 - Oui\n1 - Non");
			int choixContinuer = input.nextInt();
			input.nextLine();
			if(choixContinuer == 1) { continuer = false; }
		} while(continuer);
	}

	public void denombrementSupport(Categorie choixCat){
		ArrayList<Support> tousLesSupports = new ArrayList<Support>();
		for(int i=0;i<this.films.size();i++){
			tousLesSupports.addAll(this.films.get(i).getSupportsDuFilm());
		}
		int nbDvd = 0;
		int nbBlueRay = 0;
		int nbK7 = 0;
		int nbNonIdentifies = 0;
		int nbDvdCatChoisie = 0;
		int nbBlueRayCatChoisie = 0;
		int nbK7CatChoisie = 0;
		int nbNonIdentifiesCatChoisie = 0;
		for(int i=0;i<tousLesSupports.size();i++){
			if(tousLesSupports.get(i) instanceof Dvd) { 
				nbDvd++;
				if(tousLesSupports.get(i).getFilm().getCategorie().getLibelle() == choixCat.getLibelle()){
					nbDvdCatChoisie++;
				}
			}
			else if(tousLesSupports.get(i) instanceof BlueRay) {
				nbBlueRay++;
				if(tousLesSupports.get(i).getFilm().getCategorie().getLibelle() == choixCat.getLibelle()){
					nbBlueRayCatChoisie++;
				}
			}
			else if(tousLesSupports.get(i) instanceof k7) {
				nbK7++;
				if(tousLesSupports.get(i).getFilm().getCategorie().getLibelle() == choixCat.getLibelle()){
					nbK7CatChoisie++;
				}
			}
			else { 
				nbNonIdentifies++;
				if(tousLesSupports.get(i).getFilm().getCategorie().getLibelle() == choixCat.getLibelle()){
					nbNonIdentifiesCatChoisie++;
				}
			}
		}
		System.out.println("----------------------------------------");
		System.out.println("Nombre de Dvds : " + nbDvd + " dont " + nbDvdCatChoisie + " de la catégorie '" + choixCat.getLibelle() + "'.");
		System.out.println("Nombre de BlueRays : " + nbBlueRay + " dont " + nbBlueRayCatChoisie + " de la catégorie '" + choixCat.getLibelle() + "'.");
		System.out.println("Nombre de K7s : " + nbK7 + " dont " + nbK7CatChoisie + " de la catégorie '" + choixCat.getLibelle() + "'.");
		System.out.println("Nombre de supports non-identifiés : " + nbNonIdentifies + " dont " + nbNonIdentifiesCatChoisie + " de la catégorie '" + choixCat.getLibelle() + "'.");
		System.out.println("----------------------------------------");
	}

	public void denombrementGenre(Categorie choixCat){
		ArrayList<Integer> denombrement = new ArrayList<Integer>();
		ArrayList<Integer> dontCategorie = new ArrayList<Integer>();
		for(int i=0;i<this.genres.size();i++){
			denombrement.add(0);
			dontCategorie.add(0);
		}
		System.out.println(denombrement.size());
		for(int i=0;i<this.films.size();i++){
			for(int j=0;j<this.genres.size();j++){
				for(int k=0;k<this.films.get(i).getGenres().size();k++){
					if(this.films.get(i).getGenres().get(k).getLibelle() == this.genres.get(j).getLibelle()){
						denombrement.set(j, denombrement.get(j) + 1);
						if(this.films.get(i).getCategorie().getLibelle() == choixCat.getLibelle()){
							dontCategorie.set(j, dontCategorie.get(j) + 1);
						}
					}
				}
			}
		}
		System.out.println("----------------------------------------");
		for(int i=0;i<denombrement.size();i++){
			System.out.println("Nombre de films du genre " + this.genres.get(i).getLibelle() + " : " + denombrement.get(i) + " dont " + dontCategorie.get(i) + " de la catégorie '" + choixCat.getLibelle() + "'.");
		}
		System.out.println("----------------------------------------");
	}

	public void ajouterDuSoldePrepaye(Client client){
		System.out.println("Votre solde actuel : " + client.getSolde() + "€");
		System.out.println("Le solde actuel sur votre compte prépayé : " + this.comptes.get(client).getSolde() + "€");
		System.out.println("Veuillez entrer le montant que vous souhaitez transferer sur votre compte prépayé :");
		double montant = input.nextDouble();
		if(montant < 0){ System.out.println("Humm... Vous ne pouvez pas ajouter un solde négatif..."); }
		else if(montant > client.getSolde()){ System.out.println("Opération impossible, vous ne disposez pas d'assez d'argent."); }
		else{
			this.comptes.get(client).setSolde(this.comptes.get(client).getSolde() + montant);
			client.setSolde(client.getSolde() - montant);
			System.out.println("Opération effectuée avec succès !");
			System.out.println("Votre solde actuel : " + client.getSolde() + "€");
			System.out.println("Le solde actuel sur votre compte prépayé : " + this.comptes.get(client).getSolde() + "€");
		}
	}

	public void printLocation(Location location){
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
		System.out.println("------------------------------");
		System.out.println("---------- Location ----------");
		System.out.println("------------------------------");
		System.out.println("Numéro de location : " + location.getIdLocation());
		System.out.println("Client : " + location.getClient().getPrenom() + " " + location.getClient().getNom());
		if(location.getEnCours()){ System.out.println("Location en cours."); }
		else { System.out.println("Location terminée."); }
		System.out.println("Date de début de location : " + dateFormat.format(location.getDateDebutLocation()));
		System.out.println("Prix actuel indiqué sur la facture : " + location.getFacture().getPrix() + "€");
		if(location.getFacture().getMoyenDePaiement() == null){ System.out.println("Futur moyen de paiement inconnu."); }
		else{ System.out.println("Moyen de paiement utilisé : " + location.getFacture().getMoyenDePaiement().getLibelle()); }
		for(int i=0;i<location.getSupportsLoues().size();i++){
			System.out.println("***** Support n°" + location.getSupportsLoues().get(i).getIdSupport() + " *****");
			if(location.getSupportsLoues().get(i) instanceof Dvd){ System.out.println("***** Dvd n°" + ((Dvd)location.getSupportsLoues().get(i)).getIdDVD() + " *****"); }
			else if (location.getSupportsLoues().get(i) instanceof BlueRay){ System.out.println("***** BlueRay n°" + ((BlueRay)location.getSupportsLoues().get(i)).getIdBlueRay() + " *****"); }
			else if (location.getSupportsLoues().get(i) instanceof k7){ System.out.println("***** K7 n°" + ((k7)location.getSupportsLoues().get(i)).getIdK7() + " *****"); }
			System.out.println("Nom du film : " + location.getSupportsLoues().get(i).getFilm().getTitre());
			System.out.println("Date de fin de location prévue : " + dateFormat.format(location.getDatesRenduPrevues().get(i)));
			if(location.getDatesRenduReeles().get(i).compareTo(new Date(0,0,0)) == 0){ System.out.println("Non-rendu"); }
			else{ System.out.println("Date de fin de location réelle : " + dateFormat.format(location.getDatesRenduReeles().get(i))); }
			System.out.println("***************");
		}
		System.out.println("------------------------------");
		System.out.println();
	}

	public void printClient(Client client){
		System.out.println("------------------------------");
		System.out.println("----------- Client -----------");
		System.out.println("------------------------------");
		System.out.println("idClient : " + client.getIdClient());
		System.out.println("Prénom : " + client.getPrenom());
		System.out.println("Nom : " + client.getNom());
		System.out.println("Email : " + client.getEmail());
		printSupportsClient(client);
		System.out.println("------------------------------");
		System.out.println();
	}

	public void printSupportsClient(Client client){
		System.out.println("++++++++++++++++++++++++++");
		if(client.getSupportsEmpruntes().size() == 0){
			System.out.println(client.getPrenom() + " " + client.getNom() + " ne possède pas de Support.");
		}
		else{
			System.out.println(client.getPrenom() + " " + client.getNom() + " possède les Supports suivants :");
			for(int i=0;i<client.getSupportsEmpruntes().size();i++){
				System.out.println(i + " - Support n°" + client.getSupportsEmpruntes().get(i).getIdSupport() + " : " + client.getSupportsEmpruntes().get(i).getFilm().getTitre());
			}
		}
		System.out.println("++++++++++++++++++++++++++");
		System.out.println();
	}

	public void printSupportsClientLocation(Client client, Location location){
		System.out.println("++++++++++++++++++++++++++");
		for(int i=0;i<client.getSupportsEmpruntes().size();i++){
			for(int j=0;j<location.getSupportsLoues().size();j++){
				if(client.getSupportsEmpruntes().get(i) == location.getSupportsLoues().get(j)){
					System.out.println(i + " - Support n°" + client.getSupportsEmpruntes().get(i).getIdSupport() + " : " + client.getSupportsEmpruntes().get(i).getFilm().getTitre());
				}
			}
		}
		System.out.println("++++++++++++++++++++++++++");
		System.out.println();
	}

	public void printAllLocations(){
		if(this.locations.size()==0){ System.out.println("Aucune location n'est enregistrée."); }
		else{
			for(int i=0;i<this.locations.size();i++){
				printLocation(this.locations.get(i));
			}
		}
	}

	public void printAllClients(){
		if(this.clients.size()==0){ System.out.println("Aucune client n'est enregistré."); }
		else{
			for(int i=0;i<this.clients.size();i++){
				printClient(this.clients.get(i));
			}	
		}
	}

	public Date addDays(Date dateDebut, int nbJours){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateDebut);
		cal.add(Calendar.DATE, nbJours);
		Date date = cal.getTime();
		return date;
	}

	public int calculDateHeures(Date d1, Date d2){
		long diff = d2.getTime() - d1.getTime();
		long hours = TimeUnit.MILLISECONDS.toHours(diff);
		return (int)hours;
	}
}
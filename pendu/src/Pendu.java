import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.io.*;

import net.miginfocom.swing.MigLayout;

public class Pendu extends JFrame 
{   
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*les attributs*/
	private final JButton tabButtonAlphabet[]=new JButton[26] ; 
	private JLabel  tabLabel[]=new JLabel[11];
	private String  mot;
	private Dictionnaire dictionnaire;
	private int nbEchec ,nbcharSuccess; 
	private int niveau=1;
	private DessinPendu dessin;
	private JTextField textscore;
	private JMenuItem  MenuRejouer, MenuQuitter;
	private  ArrayList< JRadioButtonMenuItem >tabNiveau=new ArrayList<JRadioButtonMenuItem>(); 
	private int flag = 0; 

	/***************************************** constructeur *************************************************/
	public Pendu()
	{
		super("Le Pendu");
		getContentPane().setEnabled(false);
		setBounds(500,150,460,400);
		setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ConstruireInterface();
	}

	/******************************** méthode qui retourne le niveau de jeux *********************************/
	public int getNiveau()
	{
		String nomNiveau,reponse;
		boolean changer=false;
		if(Integer.parseInt(textscore.getText())== -2)
		{
			JOptionPane.showMessageDialog(this,"Game over","Information",JOptionPane.INFORMATION_MESSAGE);
			if(niveau!=1)
				niveau=niveau-1;
			changer=true;	  
		}
		else
		{
			if(Integer.parseInt(textscore.getText())== 3)
			{ 
				if(niveau==3)
				{  
					niveau=1;
					reponse=JOptionPane.showInputDialog(this,"Felicitation !!!\nvous avez terminé tous le niveaux niveau du jeux\nEntrer votre nom: ","Information",JOptionPane.QUESTION_MESSAGE) ;
					if(reponse!=null)
						enregistrerJoueur(reponse);
				}
				else
				{
					nomNiveau=((JRadioButtonMenuItem)tabNiveau.get(niveau)).getText();
					JOptionPane.showMessageDialog(this,"Felicitation !!!\nvous avez passé  au niveau "+nomNiveau,"Information",JOptionPane.INFORMATION_MESSAGE);
					niveau+=1;
				}
				changer=true;
			}
		}
		//s'il y a un changement au niveau du jeu
		if(changer==true)
		{
			textscore.setText("0");
			JRadioButtonMenuItem radioButton=(JRadioButtonMenuItem)tabNiveau.get(niveau-1);
			radioButton.setSelected(true);
			return niveau;
		}
		//sinon
		return -1;
	}	
	/**************************** méthode qui permet de charger un niveau de jeux *****************************/

	public void chargerNiveau(int n)
	{
		if(n!=-1)
		{
			switch(n)
			{
			case 1:dictionnaire.charger("./data/debutant.txt");
			break;
			case 2:dictionnaire.charger("./data/intermediaire.txt");
			break;
			case 3:dictionnaire.charger("./data/expert.txt");
			break;
			}

		}
	}

	/*********************************** méthode pour enregistrer le joueur gagnant ***************************/
	
	public void enregistrerJoueur(String nom)
	{
		try
		{
			String filename= "./data/historique.txt";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    fw.write(nom);//appends the string to the file			
		    fw.write("\n");//appends the string to the file.					    
		    fw.close(); 
		}
		catch(IOException e)
		{
			System.out.println("Erreur !!!");
		}	        		            
	}

	/*********************** méthode rechercher les positions d'un Caractère dans un mot **********************/
	
	public int[] rechercher(String mot, char lettre) 
	{
		boolean trouve=false;
		ArrayList<Integer> al=new ArrayList<Integer>();
		for(int i=0;i<mot.length();i++)
		{
			if(mot.charAt(i)==lettre)
			{  
				al.add(i); 
				trouve=true;
			}
		}
		if(trouve==true)
		{
			int pos[]=new int[ al.size()];
			for(int i=0;i< al.size();i++)
			{ 	
				pos[i]=(Integer)al.get(i);
			}
			return pos;
		}
		return null;

	}

	/******************************** méthode qui lance une nouvelle partie ***********************************/
	public void NouvellePartie()
	{ 
		//charger un niveau si ...
		chargerNiveau(getNiveau());
		//choisir un mot
		mot=(String)dictionnaire.list.get((int)(Math.random()*dictionnaire.list.size()));
		//effacer le mot de dictionnaire pour assurer que le programme ne le choisira une autre fois 
		dictionnaire.list.remove(mot);
		System.out.println(mot);
		System.out.println(dictionnaire.list.size());
		//si le dictionnaire est vide
		if(dictionnaire.list.size()==1)
			chargerNiveau(niveau);
		//effacer le dessin actuelle
		dessin.clear();
		//dessiner un autre dessin 
		dessin.update(mot.length());
		nbEchec=0;
		nbcharSuccess=0;
		for(int i=1;i<11;i++)
			tabLabel[i].setText(null);  
		//rendre les button visible
		for(int i=0;i<26;i++)
		{
			tabButtonAlphabet[i].setVisible(true);
			tabButtonAlphabet[i].setEnabled(true);
		}
		flag = flag+1;
	}


	/******************************* méthode pour construire  l'interface ************************************/
	public void ConstruireInterface()
	{ 

		//initialisation	
		Container interieur = getContentPane();
		JPanel p2=new JPanel();
		JPanel p4=new JPanel();
		p4.setBackground(SystemColor.controlLtHighlight);
		dictionnaire=new Dictionnaire();
		chargerNiveau(1);

		//caractéristique de Menu
		JMenuBar mbr=new JMenuBar();
		JMenu MenuPartie=new JMenu("Partie");
		JMenu MenuNiveau=new JMenu("Niveau");
		MenuRejouer=new JMenuItem("Rejouer");
		MenuQuitter=new JMenuItem("Quitter");
		
		tabNiveau.add(new JRadioButtonMenuItem("Facile"));
		tabNiveau.add(new JRadioButtonMenuItem("Moyen"));
		tabNiveau.add(new JRadioButtonMenuItem("Difficile"));
		ButtonGroup groupe = new ButtonGroup() ;

		for(int i=0;i<3;i++)
		{
			MenuNiveau.add((JRadioButtonMenuItem)tabNiveau.get(i));
			groupe.add((JRadioButtonMenuItem)tabNiveau.get(i));
		}
		
		setJMenuBar(mbr);
		MenuPartie.add( MenuRejouer);
		mbr.add(MenuPartie);
		MenuPartie.add(MenuNiveau);
		MenuPartie.add( MenuQuitter);
		
		//caractéristique de dessin   
		dessin=new DessinPendu(0);
		dessin.setPreferredSize(new Dimension(420, 140));
		dessin.setBackground(SystemColor.control);

		//caractéristique de panneau (south)
		p2.setLayout(new GridLayout(3,9,5,5));
		p2.setBorder(new SoftBevelBorder(1));
		char tabLettre[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		for(int i=0;i<26;i++)
		{
			tabButtonAlphabet[i]=new JButton(String.valueOf(tabLettre[i]));
			tabButtonAlphabet[i].setEnabled(false);
			p2.add(tabButtonAlphabet[i]);
		}
		
		JPanel p41=new JPanel();
		p41.setBackground(SystemColor.inactiveCaptionBorder);
		textscore=new JTextField(5);
		textscore.setEditable(false);
		textscore.setHorizontalAlignment((int)CENTER_ALIGNMENT);
		p41.add(new JLabel("Score :"));
		p41.add(textscore);
		textscore.setText("0");
		JPanel p42=new JPanel();
		p42.setBackground(SystemColor.activeCaption);
		p42.setLayout(new GridLayout(4,1,5,10));
		
		p4.setPreferredSize(new Dimension(130, 260));
		p4.setBorder(new SoftBevelBorder(1));
		p4.setLayout(new GridLayout(2,1,10,10));
		p4.add(p41);
		p4.add(p42);
		String message = "Bienvenue dans le jeu du pendu !! \nVoici les règles du pendu : \n\n"
				+ "Lancez une partie vous avez 3 Niveaux avant de gagner une partie.\n"
				+ "Lorsque vous trouvez un mot vous gagnerez 1 Point. Au bout de "
				+ "\n3 Points, vous passerez au niveau supérieur."
				+ "Si vous ne trouvez pas le mot,\n"
				+ "vous perdez 1 Point.Au bout de 2, vous perdez.\n"
				+ "Dans le menu il est possible de choisir son niveau.\n\n"
				+ "Bon jeu !! ";
		//Message qui s'affiche une fois lors du clique sur Nouvelle Partie. 
		JButton btnNouvellePartie = new JButton("Nouvelle Partie");
		btnNouvellePartie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NouvellePartie();
				if(flag == 1){
					JOptionPane.showMessageDialog(null, message);// Affichage du message.
				}
			}
		});
		
		JButton btnAjouterUnMot = new JButton("Ajouter un Mot ");
		btnAjouterUnMot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Cocher la liste et ajouter le mot en question.");
				String[] myOptions = {"debutant" , "intermédiaire" , "expert"};
				String fichier = JOptionPane.showInputDialog(null, "Selectionner la liste.", "Ajouter un Mot", JOptionPane.QUESTION_MESSAGE, null, myOptions, myOptions[0]).toString();
				System.out.println(fichier);
				String mot = JOptionPane.showInputDialog(null, "Votre mot :");
				mot = mot.toUpperCase();
				System.out.println(mot);
				try
				{	
					if(fichier == "debutant"){
						String facile= "./data/debutant.txt";
					    FileWriter writeFacile = new FileWriter(facile,true); //the true will append the new data
					    writeFacile.write("\n");//appends the string to the file
					    writeFacile.write(mot);//appends the string to the file								    
					    writeFacile.close(); 
					}
					else if(fichier == "intermédiaire"){
						String moyen= "./data/intermediaire.txt";
					    FileWriter writeMoyen= new FileWriter(moyen,true); //the true will append the new data
					    writeMoyen.write("\n");//appends the string to the file
					    writeMoyen.write(mot);//appends the string to the file
					    writeMoyen.close();
					}
					else{
						String difficile= "./data/expert.txt";
					    FileWriter writeDifficile = new FileWriter(difficile,true); //the true will append the new data
					    writeDifficile.write("\n");//appends the string to the file
					    writeDifficile.write(mot);//appends the string to the file
					    writeDifficile.close();
					}
				}
				catch(IOException e)
				{
					System.out.println("Erreur !!!");
				}	        		 
			}
		});
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*System.out.println(mot);
				System.out.println("Score:" + textscore.getText());
				//System.out.print();
				System.out.println("Niveau:" + niveau);
				
				for(int i=0; i>= nbEchec; i++){
					System.out.print(tabLabel[i].getText());
				}*/
					System.exit(0);
			}
		});
				p42.add(btnNouvellePartie);
				p42.add(btnAjouterUnMot);
				p42.add(btnQuitter);
		
		getContentPane().setLayout(new MigLayout("", "[14px][303px][4px][119px]", "[298px][14px][16px]"));

		//ajout des panneaux
		interieur.add(dessin, "cell 1 0,grow");

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(1));
		panel.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent arg0) {
				
			}
		});
		
		panel.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(panel, "cell 0 1 4 1,grow");
		//panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(new MigLayout("", "[]", "[]"));
		for(int i=0;i<tabLabel.length;i++)
		{
			tabLabel[i]=new JLabel();
			tabLabel[i].setVerticalAlignment((int)CENTER_ALIGNMENT);
			panel.add(tabLabel[i])	;
		}
		tabLabel[0].setText(" Les Erreurs:");
		interieur.add(p2, "cell 0 2 4 1,growx,aligny top");
		interieur.add(p4, "cell 3 0,alignx left,growy");


		/************************************** les écouteurs ***************************************/
		MenuRejouer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					textscore.setText("0");
					NouvellePartie();
					if(flag == 1){
						JOptionPane.showMessageDialog(null, message);// Affichage du message.
					} 
				}
			});//listener sur le menuItem "rejouer"


		MenuQuitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				System.out.print(mot);
				System.exit(0);
				}
			});//listener sur le menuItem "quitter"

	

		for(int i=0;i<3;i++)          
			tabNiveau.get(i).addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					Object source=evt.getSource();
					niveau=tabNiveau.indexOf(source)+1;chargerNiveau(niveau);
					textscore.setText("0");
					NouvellePartie();
					}
				});//listener sur le tableau de niveau "debutant","intermidiaire",expert"


		for(int i=0;i<26;i++)
			tabButtonAlphabet[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					JButton source=(JButton)evt.getSource();
					ecouteurAlphabet(source);
					}
				});

	}//Fin ConstruireInterface



	/*********************** methode qui gère l'ecouteur des buttons "de l'Alphabet" ************************/ 
	public void  ecouteurAlphabet(JButton source)
	{
		int[]pos;	
		String str=mot;
		
		source.setVisible(false);
		if(rechercher(mot,source.getText().charAt(0))==null)
		{
			nbEchec++;
			dessin.dessinerErreur(nbEchec);
			tabLabel[nbEchec].setText(source.getText());
		}
		else
		{
			pos=new int[2];
			pos=rechercher(mot,source.getText().charAt(0));
			nbcharSuccess+=pos.length;
			dessin.dessinerLettre(source.getText().charAt(0),pos);	
		}

		if(nbEchec==10)
		{

			JOptionPane.showMessageDialog(this,"Dommage,vous avez perdu cette partie!\nLe mot à trouver était: "+str,"Fin de la Partie",JOptionPane.DEFAULT_OPTION);
			textscore.setText(String.valueOf(Integer.parseInt(textscore.getText())-1));
			for(int i=0;i<26;i++)
				tabButtonAlphabet[i].setEnabled(false);

		}
		if(nbcharSuccess==mot.length())
		{
			JOptionPane.showMessageDialog(this,"Bravo! vous avez gagné cette partie","Information",JOptionPane.DEFAULT_OPTION);
			textscore.setText(String.valueOf(Integer.parseInt(textscore.getText())+1));
			for(int i=0;i<26;i++)
				tabButtonAlphabet[i].setEnabled(false);
		}
	}

	/************************************* Main pour tester notre programme ***********************************/
	public static  void main(String args[])
	{

		Pendu p=new Pendu();

		p.pack();
		p.setVisible(true);

	}
}
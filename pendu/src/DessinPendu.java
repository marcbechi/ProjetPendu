import java.awt.*;
import javax.swing.*;
import java.util.LinkedList;
import java.util.*;

public class DessinPendu extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*les attributs*/
	private int nbrLettre;
	private int tab[];
	private LinkedList<IDrawable> drawables= drawables = new LinkedList<IDrawable>(); 
	private Image Pendu_1=this.getToolkit().getImage("./images/Pendu_1.png");
	private Image Pendu_2=this.getToolkit().getImage("./images/Pendu_2.png");
	private Image Pendu_3=this.getToolkit().getImage("./images/Pendu_3.png");
	private Image Pendu_4=this.getToolkit().getImage("./images/Pendu_4.png");
	private Image Pendu_5=this.getToolkit().getImage("./images/Pendu_5.png");
	private Image Pendu_6=this.getToolkit().getImage("./images/Pendu_6.png");
	private Image Pendu_7=this.getToolkit().getImage("./images/Pendu_7.png");
	private Image Pendu_8=this.getToolkit().getImage("./images/Pendu_8.png");
	private Image Pendu_9=this.getToolkit().getImage("./images/Pendu_9.png");
	private Image Pendu_10=this.getToolkit().getImage("./images/Pendu_10.png");
	
	/***************************************  constructeur **************************************************/
	public DessinPendu(int nbrLettre)
	{
		this.nbrLettre=nbrLettre; 
	}


	/**************************** methode pour ajoueter un IDrawable dans la liste drawables *****************/
	public void addDrawable(IDrawable d) 
	{
		drawables.add(d);
		repaint();
	}



	/******************* méthode pour effacer notre dessin lors d'une nouvelle partie ***********************/
	public void clear()
	{
		drawables.clear();
		repaint();
	}

	/********************************* méthode de mise àjour de notre dessin ********************************/
	public void update(int nbrLettre)
	{
		this.nbrLettre=nbrLettre;
		this.repaint();
	} 

	/************************************ méthode pour dessiner : le pendu **********************************/                
	public void paint(Graphics g) 
	{
		super.paint(g);
		construire_pendu(g);

		//dessiner les IDrawbles à partir de la liste drawbles
		for (Iterator iter = drawables.iterator(); iter.hasNext();) 
		{
			IDrawable d = (IDrawable) iter.next();
			d.draw(g);
		}

	}

	/******************************** méthode qui dessine les positions des lettres  ********************/
	public void construire_pendu(Graphics g)
	{
		int x1,x2;                             
		x1=20; 

		if(nbrLettre!=0)
		{
			//table de positions des lettres
			tab=new int [nbrLettre];
			//remplir la table
			for(int i=0;i<nbrLettre;i++)
			{
				x2=x1+10;
				g.drawLine(x1,40,x2,40);
				tab[i]=x1;
				x1=x2+10;
			}
		}
	}

	/************************************* méthode dessiner une Lettre ****************************************/
	public void dessinerLettre(char c, int[] pos) 
	{

		for(int i=0;i<pos.length;i++)
		{
			addDrawable(new StringDrawble(Color.black,String.valueOf(c),new Point(tab[pos[i]],40)));

		}
	}

	/*********************************** méthode pour dessiner dans le cas d'erreur ***************************/
	public void dessinerErreur(int n) 
	{

		switch(n)
		{
		case 1: //on dessine la tête

			addDrawable(new  ImageDrawble(Pendu_1,new Point(150,150),150,150,this));         
			break;
		case 2://on dessine le corps
			addDrawable(new ImageDrawble(Pendu_2,new Point (150,150),150,150,this)); 
			break;
		case 3://on dessine les bras ..... les jambes
			addDrawable(new	ImageDrawble(Pendu_3,new Point (150,150),150,150,this)); 
			break;
		case 4:
			addDrawable(new  ImageDrawble(Pendu_4,new Point(150,150),150,150,this));
			break;
		case 5:
			addDrawable(new  ImageDrawble(Pendu_5,new Point(150,150),150,150,this));
			break;
		case 6:
			addDrawable(new ImageDrawble(Pendu_6,new Point(150,150),150,150,this));
			break;
		case 7:
			addDrawable(new ImageDrawble(Pendu_7,new Point(150,150),150,150,this));
			break;
		case 8:
			addDrawable(new ImageDrawble(Pendu_8,new Point(150,150),150,150,this));
			break;
		case 9:
			addDrawable(new ImageDrawble(Pendu_9,new Point(150,150),150,150,this));
			break;
		case 10:
			addDrawable(new ImageDrawble(Pendu_10,new Point(150,150),150,150,this));
			break;			
		}

	}

}//fin DessinPendu
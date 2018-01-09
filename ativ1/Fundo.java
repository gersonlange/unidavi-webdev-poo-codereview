/*
 * Criado em 02/04/2007
 */
package br.inf.edge.deskutil.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import br.inf.edge.deskutil.io.Sistema;
import br.inf.edge.deskutil.sys.Cores;
import br.inf.edge.deskutil.ui.menu.SubMenuItemUI;
import br.inf.edge.deskutil.ui.tela.TelaDashboard;

/**
 * @author Gerson Lange
 * 
 * TODO Fundo da tela principal
 * 
 */
public class Fundo extends JDesktopPane
{
	private static final long serialVersionUID = -4055014863557208187L;

	private static Fundo instance;

	private static String fundo, logo;
	private static Image empresa, revenda;

	private Image imgFundo, imgLogo;
	private BufferedImage scaledImage;
	private TelaDashboard dashboard;

	private int cachedHeight = -1, logoY, empresaHeight;
	private int cachedWidth = -1, logoX, empresaWidth;
	private int empresaX, empresaY, revendaY, revendaX;
	private boolean isFundo = true, isLogo = true;

	private Fundo()
	{
		if (fundo != null)
			imgFundo = new ImageIcon(getClass().getClassLoader().getResource(fundo)).getImage();

		if (logo != null)
			imgLogo = new ImageIcon(getClass().getClassLoader().getResource(logo)).getImage();

		if ( Sistema.isManager() )
			setBackground( new Color(128, 56, 56) );
		else if ( Sistema.isFrenteCaixa() )
			setBackground( new Color(35, 86, 145) );

		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				dashpos();
			}
		});

		if ( Sistema.isFrenteCaixa() || Sistema.isManager() )
		{
			addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					checkPopup(e);
				}
				
				@Override
				public void mousePressed(MouseEvent e)
				{
					checkPopup(e);
				}
				
				@Override
				public void mouseReleased(MouseEvent e)
				{
					checkPopup(e);
				}
				
				private void checkPopup(MouseEvent e)
				{
					if (e.isPopupTrigger())
						new Menu().show(Fundo.this, e.getX(), e.getY());
				}
			});
		}
	}

	@Override
	public Component add(Component comp)
	{
		if ( comp instanceof TelaDashboard )
		{
			if ( dashboard != null )
				remove(dashboard);

			dashboard = (TelaDashboard)comp;

			dashpos();
		}

		return super.add(comp);
	}

	private void dashpos()
	{
		if ( dashboard != null )
		{
			dashboard.setLocation(getWidth() - dashboard.getWidth() - 5, 10);
			dashboard.setSize(dashboard.getWidth(), getHeight() - 150);
		}
	}

	public static void setImagemFundo(String imgFundo)
	{
		fundo = imgFundo;
	}
	
	public static void setImagemLogo(String imgLogo)
	{
		logo = imgLogo;
	}

	public static void setImagemEmpresa(Image imgEmpresa)
	{
		empresa = imgEmpresa;
		
		getInstance().repaint();
	}

	public static void setImagemRevenda(Image imgRevenda)
	{
		revenda = imgRevenda;
		
		getInstance().repaint();
	}

	public static Fundo getInstance()
	{
		if (instance == null)
			instance = new Fundo();

		return instance;
	}

	@Override
	public JInternalFrame[] getAllFrames()
	{
		JInternalFrame[] frames = super.getAllFrames();
		Vector<JInternalFrame> d = new Vector<>();

		for ( JInternalFrame f : frames )
			d.add(f);

		return d.toArray( new JInternalFrame[d.size()] );
	}

	public boolean telaAberta()
	{
		return getAllFrames().length > 0;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	if ( isFundo )
    	{
	    	if (cachedHeight != getHeight() || cachedWidth != getWidth())
			{
				createScaledImage();

				if (imgLogo != null)
				{
					logoX = getWidth() - imgLogo.getWidth(this) - 20;
					logoY = getHeight() - imgLogo.getHeight(this) - 20;
				}

				if (empresa != null)
				{
					empresaWidth = empresa.getWidth(this);
					empresaHeight = empresa.getHeight(this);
					empresaX = ((getWidth() - empresaWidth) / 2);
					empresaY = (getHeight() - empresaHeight) / 2 - 20;
				}			
			}

			g2.drawImage(scaledImage, 0, 0, this);
    	}

		if ( imgLogo != null)
			g2.drawImage(imgLogo, logoX, logoY, this);

		if (revenda != null)
		{
			revendaX = 20;
			revendaY = getHeight() - revenda.getHeight(this) - 20;
		}

		if (revenda != null)
			g2.drawImage(revenda,revendaX,revendaY,this);

		if ( isLogo && empresa != null )
			g2.drawImage(empresa,empresaX,empresaY,empresaWidth,empresaHeight,this);

		if ( Sistema.isFrenteCaixa() )
		{
			g2.setFont(g2.getFont().deriveFont(Font.BOLD));
			g2.setColor(new Color(210,210,210));
			g2.drawString("Local Gravação TXT MD5: " + new File("paf" + File.separator + "ANEXO III.txt").getAbsolutePath(), 20, getHeight() - 5);
		}
	}

	private void createScaledImage()
	{
		cachedWidth  = getWidth();
		cachedHeight = getHeight();

		if (cachedWidth > 0 && cachedHeight > 0)
		{
			scaledImage = new BufferedImage(cachedWidth, cachedHeight, BufferedImage.TYPE_INT_RGB);

			if (imgFundo != null)
			{
				scaledImage.createGraphics().drawImage( imgFundo, AffineTransform.getScaleInstance(
						((float) cachedWidth) / imgFundo.getWidth(this),
						((float) cachedHeight) / imgFundo.getHeight(this)), null );
			}
		}
		else
			scaledImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	}
	
	private class Menu extends JPopupMenu
	{
		private static final long serialVersionUID = 5511745520571871799L;

		public Menu()
		{
			setOpaque(false);
			setBorderPainted(false);

			JMenuItem menu = new JMenuItem( isFundo ? "Sem fundo" : "Com fundo" );
			menu.setUI(new SubMenuItemUI());
			menu.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					isFundo = ! isFundo;

					Fundo.this.repaint();
				}
			});
			add(menu);


			menu = new JMenuItem( isLogo ? "Sem logo" : "Com logo" );
			menu.setUI(new SubMenuItemUI());
			menu.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					isLogo = ! isLogo;

					Fundo.this.repaint();
				}
			});
			add(menu);
		}

		protected void paintComponent(Graphics g)
		{
			Graphics2D graph = (Graphics2D)g;
	    	graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	    	        RenderingHints.VALUE_ANTIALIAS_ON);

			Color contornoselecionado = Cores.subMenuContorno;
			Color fundoclaroselecionado = Color.WHITE;
			Color fundoescuroselecionado = Cores.subMenuFundo;
			
	       	graph.setPaint(new GradientPaint(100,20,fundoclaroselecionado,50,50,fundoescuroselecionado,true));

			graph.fillRoundRect(0,0,getWidth()-1,getHeight()-1,6,6);
			
			graph.setColor(contornoselecionado);
			graph.drawRoundRect(0,0,getWidth()-1,getHeight()-1,6,6);
		}

		@Override
		protected void paintBorder(Graphics g)
		{

		}
	}
}
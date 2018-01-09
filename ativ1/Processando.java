/**
 * 5 de jan de 2017
 */
package br.inf.edge.deskutil.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;

import br.inf.edge.deskutil.io.MsgClasse;
import br.inf.edge.deskutil.sys.Dados;
import br.inf.edge.deskutil.ui.campo.CampoCodigo;
import br.inf.edge.deskutil.ui.campo.CampoCombo;
import br.inf.edge.deskutil.ui.campo.CampoPadrao;
import br.inf.edge.deskutil.ui.campo.CampoSenha;
import br.inf.edge.deskutil.ui.campo.CampoTextoArea;
import br.inf.edge.deskutil.ui.tabela.Tabela;
import br.inf.edge.deskutil.util.Utils;
import sun.swing.SwingUtilities2;

/**
 * @author Gerson Lange
 */
public class Processando extends MsgClasse
{
	private Component tela;
	private Window win;
	private Image splashImage;
	private MouseAdapter mouseList;
	private Vector<Component> compEnabled;

	private String mensagem;
	private boolean mostrar, branco;
	private int largura, altura, coluna, linha;

	public Processando(Component comp1, boolean branco)
	{
		tela = comp1;
		mostrar = true;
		this.branco = branco;

		comp1.addMouseListener(mouseList = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				comp1.setCursor( new Cursor(Cursor.WAIT_CURSOR) );
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				comp1.setCursor( new Cursor(Cursor.DEFAULT_CURSOR) );
			}
		});

		compEnabled = new Vector<>();

		Vector<Component> componentes;

		if ( comp1 instanceof TelaInternal )
		{
			componentes = Utils.getComponentes(false, true, ((TelaInternal)comp1).getContentPane());
			win = Dados.getFramePrincipal();
		}
		else if ( comp1 instanceof JDialog )
		{
			componentes = Utils.getComponentes(true, true, (JDialog)comp1);
			win = (JDialog)comp1;
		}
		else if ( comp1 instanceof JFrame )
		{
			componentes = Utils.getComponentes(false, true, ((JFrame)comp1).getContentPane());
			win = (JFrame)comp1;
		}
		else
		{
			componentes = Utils.getComponentes(false, true, comp1 );
			win = Dados.getFramePrincipal();
		}

		setTela(win);
		setWinOwner(win);

		Component comp;

		for (int i = 0 ; i < componentes.size() ; i++)
        {
        	comp = componentes.get(i);

    		if (comp instanceof CampoPadrao)
    		{
    			if ( ((CampoPadrao)comp).isEnabled() )
        			compEnabled.add( comp );
    		}
    		else if (comp instanceof CampoCodigo)
    		{
    			if ( ((CampoCodigo)comp).isEnabled() )
        			compEnabled.add( comp );
    		}
    		else if (comp instanceof CampoSenha)
    		{
    			if ( ((CampoSenha)comp).isEnabled() )
    				compEnabled.add( comp );
    		}
    		else if (comp instanceof CampoCombo)
    		{
    			if ( ((CampoCombo)comp).isEnabled() );
    			compEnabled.add( comp );
    		}
        	else if (comp instanceof JCheckBox)
        	{
    			if ( ((JCheckBox)comp).isEnabled() )
    				compEnabled.add( comp );
        	}
    		else if (comp instanceof CampoTextoArea)
    		{
    			if ( ((CampoTextoArea)comp).isEnabled() )
        			compEnabled.add( comp );
    		}
    		else if (comp instanceof Tabela)
    		{
    			if ( ((Tabela)comp).isEnabled() )
    				compEnabled.add( comp );
    		}
    		else if (comp instanceof JList)
    		{
    			if ( ((JList<?>)comp).isEnabled() )
        			compEnabled.add( comp );
    		}
    		else if (comp instanceof JButton)
    		{
    			if ( ((JButton)comp).isEnabled() )
        			compEnabled.add( comp );
    		}
        }

		setEna(false);
		tela.repaint();
	}
	
	public void remove()
	{
		mostrar = false;

		tela.removeMouseListener(mouseList);
        tela.setCursor( new Cursor(Cursor.DEFAULT_CURSOR) );
		setEna(true);

		tela.repaint();
	}

	@Override
	public void setMensagem(String mensagem)
	{
		this.mensagem = mensagem;

		tela.repaint();
	}

	@Override
	public void mostrar()
	{
		mostrar = true;

		tela.repaint();
	}
	
	@Override
	public void ocultar()
	{
		mostrar = false;

		tela.repaint();
	}

	private void setEna(boolean b)
	{
		if ( compEnabled != null )
		{
			for ( Component c : compEnabled )
				c.setEnabled(b);
		}

		if ( b )
			compEnabled = null;
	}

	public Window getWindowOwner()
	{
		return win;
	}

	public void setBranco(boolean branco)
	{
		this.branco = branco;
	}

	public void paint(Graphics g)
	{
		if (splashImage == null)
		{
			MediaTracker media = new MediaTracker(tela);

			splashImage = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("br/inf/edge/deskutil/img/aguarde_mensagem.png"));
			media.addImage(splashImage,0);

			try
			{
				media.waitForID(0);
			}
			catch (InterruptedException e) { }

			media = null;
		}

		largura = 550;
		altura  = 200;
		coluna = (tela.getWidth() - largura) / 2;
		linha  = (tela.getHeight() - altura) / 2;

		Graphics2D g2d = (Graphics2D)g;

		if ( branco )
		{
			g2d.setColor(new Color(255, 255, 255, 150));
			g2d.fillRoundRect(5, 5, tela.getWidth()-10, tela.getHeight()-10, 5, 5);
		}

		if ( mostrar )
		{
			g2d.setColor(Color.WHITE);
			g2d.fillRect(coluna, linha, largura, altura);
			g2d.drawImage(splashImage, coluna, linha, largura, altura, tela);

			if ( mensagem != null && ! mensagem.trim().isEmpty() )
			{
	    		int mWidth = largura - 4, w = 0;
	        	Font font1 = tela.getFont().deriveFont(Font.BOLD, 30f);
	
	        	FontMetrics fm = SwingUtilities2.getFontMetrics(null, font1);
	
	    		while (mWidth > 0 && (w = fm.stringWidth(mensagem)) > mWidth)
	    			font1 = font1.deriveFont(new Float(font1.getSize()-1));
	
				g2d.setFont( font1 );

				int mx = (int)((largura - w) / 2);

				g2d.setColor(Color.DARK_GRAY);
				g2d.drawString(mensagem, coluna + mx, linha + 180);
			}
		}
	}
	
	public void setStatementParar(Statement stmt)
	{
		
	}
	
	public void removerStatementParar()
	{
		
	}

	public boolean isParar()
	{
		return false;
	}
}
/**
 * 29/01/2008
 */
package br.inf.edge.deskutil.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import br.inf.edge.deskutil.sys.Descricoes;
import br.inf.edge.deskutil.ui.campo.CampoValor;
import br.inf.edge.deskutil.ui.layout.FlowBaseDownConstraints;
import br.inf.edge.deskutil.ui.layout.FlowBaseDownLayout;

/**
 * @author Gerson Lange
 *
 */
public class Totalizador extends JComponent
{
	private static final long serialVersionUID = 197276222003057695L;

	private JLabel lbTitulo, lbValor;
	private NumberFormatter formatSubtotal;
	private double valor = 0d;

	public Totalizador()
	{
		setLayout(new FlowBaseDownLayout(FlowBaseDownLayout.LEADING,5,5));
		setOpaque(false);
		setBackground(Color.WHITE);

		FlowBaseDownConstraints cons = new FlowBaseDownConstraints();

        lbTitulo = new JLabel();
        lbTitulo.setFont(new Font("Arial",Font.BOLD,16));
        lbTitulo.setForeground(Color.BLUE);
        add(lbTitulo,cons);
        
        cons.linha ++;

        lbValor = new JLabel("0,00");
        lbValor.setFont(new Font("Arial",Font.BOLD,26));
        lbValor.setForeground(Color.BLACK);
        lbValor.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbValor,cons);

		formatSubtotal = new CriaMascaraValor(11,2,CampoValor.TIPO_DOUBLE,true).getMascara();
	}
	
	public Totalizador(String titulo, int tamanhoValor, int decimais)
	{
		this();
		setTitulo(titulo);
		setTamanhoValor(tamanhoValor);

		formatSubtotal = new CriaMascaraValor(tamanhoValor,decimais,CampoValor.TIPO_DOUBLE,true).getMascara();
	}
	
	public void setTitulo(String titulo)
	{
		lbTitulo.setText(Descricoes.get(titulo));
	}
	
	public void setValor(double valor)
	{
		this.valor = valor;

		try
		{
			lbValor.setText(formatSubtotal.valueToString(valor));
		}
		catch (Exception e) {}
	}
	
	public double getValor()
	{
		return valor;
	}
	
	public String getTexto()
	{
		return lbValor.getText();
	}
	
	public void setTamanhoValor(int tamanho)
	{
    	int tamanhoCaixa = (lbValor.getFontMetrics(lbValor.getFont()).charWidth('0') * tamanho);
    	int alturaCaixa = lbValor.getFontMetrics(lbValor.getFont()).getHeight();

    	lbValor.setPreferredSize(new Dimension(tamanhoCaixa,alturaCaixa));
	}
	
	public void setCorValor(Color c)
	{
		lbValor.setForeground(c);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		int curva = 8;

		Graphics2D graph = (Graphics2D)g;
    	graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	        RenderingHints.VALUE_ANTIALIAS_ON);

    	graph.setColor(getBackground());
		graph.fillRoundRect(0,0,getWidth()-1,getHeight()-1,curva,curva);

		graph.setColor(getBackground().darker().darker());
		graph.drawRoundRect(0,0,getWidth()-1,getHeight()-1,curva,curva);

		super.paintComponent(g);
	}
}
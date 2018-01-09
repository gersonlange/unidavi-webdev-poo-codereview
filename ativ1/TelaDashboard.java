/*
 * Criado em 26/04/2007
 */
package br.inf.edge.deskutil.ui.tela;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.inf.edge.deskutil.sys.Descricoes;
import br.inf.edge.deskutil.ui.Fundo;
import br.inf.edge.deskutil.ui.layout.FlowBaseDownConstraints;
import br.inf.edge.deskutil.ui.layout.FlowBaseDownLayout;

/**
 * @author Gerson Lange
 */
public class TelaDashboard extends JPanel
{
	private static final long serialVersionUID = -8095614518817225437L;

	private static TelaDashboard instance;

	public static final String NOTIFICACAO  = "notificacao";
	public static final String BACKUP       = "backup";
	
	public static final String ICONE_ESCLAMACAO   = "br/inf/edge/sistema/img/dashboard_esclamacao.png";
	public static final String ICONE_INTERROGACAO = "br/inf/edge/sistema/img/dashboard_pergunta.png";
	public static final String ICONE_ENGRENAGEM   = "br/inf/edge/sistema/img/dashboard_engrenagem.png";

	private JPanel panel;
	private FlowBaseDownConstraints cons;

	private HashMap<String, PanelDashBoard> itens;

	private TelaDashboard()
	{
		super();

		setBorder(null);
		setSize(280, 80);
		setOpaque(false);
		setLayout(new BorderLayout());

		cons = new FlowBaseDownConstraints();
		cons.preencher = true;

		setOpaque(false);

		panel = new JPanel(new FlowBaseDownLayout(0, 10));
		panel.setOpaque(false);

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setOpaque(false);
		scroll.setAutoscrolls(true);
		scroll.getViewport().setOpaque(false);
		scroll.getViewport().setBorder(null);
		scroll.setBorder(null);
		add(scroll);

		setVisible(true);

		Fundo.getInstance().add(this);

		itens = new HashMap<>();
	}

	public TelaDashboard item(String chave, String texto)
	{
		String icone = null, titulo = null;

		if ( NOTIFICACAO.equals(chave) )
		{
			icone = "br/inf/edge/sistema/img/dashboard_notificacao_32.png";
			titulo = Descricoes.get("Gerais.campo.notificacao");
		}
		else if ( BACKUP.equals(chave) )
		{
			icone = "br/inf/edge/sistema/img/dashboard_backup_32.png";
			titulo = Descricoes.get("Gerais.campo.backup");
		}

		return item(chave, icone, titulo, texto);
	}

	public TelaDashboard item(String chave, String icone, String titulo, String texto)
	{
		PanelDashBoard p = new PanelDashBoard(icone, titulo, texto);
		panel.add(p, cons);

		cons.linha ++;

		itens.put(chave, p);

		return this;
	}
	
	public PanelDashBoard get(String chave)
	{
		return itens.get(chave);
	}

	public void remove(String chave)
	{
		if ( itens.containsKey(chave) )
			panel.remove(itens.get(chave));
	}

	public static void addItem(String chave, String texto)
	{
		if ( instance == null )
			return;

		instance.item(chave, texto);
	}

	public static void addItem(String chave, String icone, String titulo, String texto)
	{
		if ( instance == null )
			return;

		instance.item(chave, icone, titulo, texto);
	}

	public static void removeItem(String chave)
	{
		if ( instance != null )
			instance.remove(chave);
	}

	public static PanelDashBoard getItem(String chave)
	{
		return instance.get(chave);
	}

	public static TelaDashboard abrir()
	{
		if ( instance == null )
			instance = new TelaDashboard();

		return instance;
	}

	public static void fecha()
	{
		if ( instance != null )
			Fundo.getInstance().remove(instance);

		instance = null;
	}
}
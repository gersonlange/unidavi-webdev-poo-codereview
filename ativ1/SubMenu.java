/**
 * 15/07/2008
 */
package br.inf.edge.deskutil.ui.menu;

import java.awt.Graphics;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenu;

import br.inf.edge.deskutil.sys.Acessos;
import br.inf.edge.deskutil.sys.Descricoes;

/**
 * @author Gerson Lange
 *
 */
public class SubMenu extends JMenu
{
	private static final long serialVersionUID = 5142918746583701314L;

	protected static final String SEPARADOR = "separador";
	
	public SubMenu(String resource, Vector<Menu.ItemMenu> itens)
	{
		String texto = Descricoes.get(resource + ".menu.nome");

		setText(texto);
		setOpaque(false);
		setBorderPainted(false);
		setUI(new SubMenuUI());

		String atalho = Descricoes.get(resource + ".menu.atalho");

		if (atalho != null && ! atalho.trim().isEmpty())
			setMnemonic(atalho.charAt(0));

		String tooltip = Descricoes.get(resource + ".menu.tooltip");

		if (tooltip != null && ! tooltip.trim().isEmpty())
			setToolTipText(tooltip);

		String icon = Descricoes.get(resource + ".icone.16");

		if (icon == null)
			icon = Descricoes.get("Menu.icon.transparente.16");

		setIcon(new ImageIcon(getClass().getClassLoader().getResource(icon)));

		if (itens != null)
		{
			boolean separador = false;

			for (Menu.ItemMenu item : itens)
			{
				if (SEPARADOR.equals(item.getKey()))
					separador = true;
				else
				{
					if (Acessos.isAcessos(item.getKey(),true))
					{
						if (separador)
						{
							addSeparator();
							separador = false;
						}

						add(new SubMenuItem(
								item.getDescricao(),
								item.getClasse(),
								item.getKey(),
								false,
								item.isBarra()));
					}
				}
			}
		}
	}
	
	@Override
	protected void paintBorder(Graphics g) {}
}
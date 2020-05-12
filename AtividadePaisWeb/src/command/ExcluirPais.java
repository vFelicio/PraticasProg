package command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Pais;
import service.PaisService;

public class ExcluirPais implements Command {

	@Override
	public void executar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pId = request.getParameter("id");
		String pNome = request.getParameter("pais");

		int id = -1;
		try {
			id = Integer.parseInt(pId);
		} catch (NumberFormatException e) {

		}

		Pais pais = new Pais();
		pais.setIdPais(id);
		pais.setNomePais(pNome);

		PaisService ps = new PaisService();
		
		RequestDispatcher view = null;
		HttpSession session = request.getSession();

		ps.excluir(pais.getIdPais());
		@SuppressWarnings("unchecked")
		ArrayList<Pais> lista = (ArrayList<Pais>) session
				.getAttribute("lista");
		lista.remove(busca(pais, lista));
		session.setAttribute("lista", lista);
		view = request.getRequestDispatcher("ListarPaises.jsp");
		view.forward(request, response);

	}

	public int busca(Pais pais, ArrayList<Pais> lista) {
		Pais to;
		for (int i = 0; i < lista.size(); i++) {
			to = lista.get(i);
			if (to.getIdPais() == pais.getIdPais()) {
				return i;
			}
		}
		return -1;
	}

}

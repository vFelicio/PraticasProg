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

public class EditarPais implements Command {

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
		
		pais = ps.carregar(pais.getIdPais());
		request.setAttribute("pais", pais);
		view = request.getRequestDispatcher("AlterarPais.jsp");		
		
		view.forward(request, response);

	}
	
	private void recPopArea(HttpServletRequest request, Pais pais) {
		long pPopulacao = Long.parseLong(request.getParameter("populacao"));
		double pArea = Double.parseDouble(request.getParameter("area"));
		pais.setPopulacaoPais(pPopulacao);
		pais.setAreaPais(pArea);
	}

	public int busca(Pais pais, ArrayList<Pais> lista) {
		Pais to;
		for(int i = 0; i < lista.size(); i++){
			to = lista.get(i);
			if(to.getIdPais() == pais.getIdPais()){
				return i;
			}
		}
		return -1;
	}

}

package br.com.ultcode.livraria.modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.ultcode.livraria.dao.LivroDao;

public class LivroDataModel extends LazyDataModel<Livro> {
	private static final long serialVersionUID = -4598067527411264871L;

	@Inject
	private LivroDao dao;

	@PostConstruct
	private void init() {
		setRowCount(dao.contaTodos());
	}

	@Override
	public List<Livro> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		String titulo = (String) filters.get("titulo");
		String preco = (String) filters.get("preco");

		Map<String, String> colunaValor = new HashMap<>();
		colunaValor.put("titulo", titulo);
		colunaValor.put("preco", preco);

		return dao.listaTodosPaginada(first, pageSize, colunaValor);
	}
}

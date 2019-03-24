package br.com.ultcode.livraria.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.ultcode.livraria.dao.LivroDao;
import br.com.ultcode.livraria.modelo.Livro;
import br.com.ultcode.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	private static final long serialVersionUID = -7531882034128800926L;

	@Inject
	private LivroDao livroDao;

	public List<Venda> getVendas(long seed) {

		List<Livro> livros = livroDao.buscaTodos();
		List<Venda> vendas = new ArrayList<>();

		Random random = new Random(seed);

		for (Livro livro : livros) {
			vendas.add(new Venda(livro, random.nextInt(500)));
		}

		return vendas;

	}

	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);

		List<Venda> vendas = getVendas(1234);
		ChartSeries vendas2016 = new ChartSeries();
		vendas2016.setLabel("Vendas de 2016");
		vendas.forEach(v -> {
			vendas2016.set(v.getLivro().getTitulo(), v.getQuantidade());
		});

		vendas = getVendas(4321);
		ChartSeries vendas2017 = new ChartSeries();
		vendas2017.setLabel("Vendas de 2017");
		vendas.forEach(v -> {
			vendas2017.set(v.getLivro().getTitulo(), v.getQuantidade());
		});

		model.addSeries(vendas2016);
		model.addSeries(vendas2017);

		return model;
	}
}

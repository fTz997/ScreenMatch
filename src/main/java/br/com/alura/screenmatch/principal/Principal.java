package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();

    private final String END = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ca7a04f4";

    public void exibeMenu(){
        System.out.println("Digite o nome da série");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(END + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);


		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dados.totalTemporadas(); i++){
			json = consumo.obterDados(END + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
			DadosTemporada dadosTemp = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemp);
		}

		temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodiosList().forEach(e -> System.out.println(e.titulo())));
    }

}

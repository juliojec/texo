package br.com.texo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.texo.model.Movie;
import br.com.texo.model.WinnerEnum;
import br.com.texo.service.MovieService;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class Main {
  
  @Inject
  MovieService movieService;
  
  @Transactional
  void startup(@Observes StartupEvent event) throws FileNotFoundException, IOException { 
    URL csvUrl = Main.class.getClassLoader().getResource("movielist.csv");
    System.out.println("Importando CSV de Filmes");
    
    List<Movie> movies = new ArrayList<Movie>();
    try (BufferedReader br = new BufferedReader(new FileReader(csvUrl.getPath()))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(";");
            
            if(("year").equals(String.valueOf(values[0]))) continue;
            
            Movie movie = new Movie();
            movie.setYear(Integer.valueOf(values[0]));
            movie.setTitle(String.valueOf(values[1]));
            movie.setStudio(String.valueOf(values[2]));
            movie.setProducer(String.valueOf(values[3]));
            
            if(values.length > 4) {
              movie.setWinner(values[4].isEmpty() ? WinnerEnum.no : WinnerEnum.valueOf(values[4]));
            } else {
              movie.setWinner(WinnerEnum.no);
            }
            
            movies.add(movie);
        }
    }
    
    movieService.save(movies);
    
    System.out.println("Fim da Importação - Foram importandos " + movies.size() + " filmes.");
    
  }
}

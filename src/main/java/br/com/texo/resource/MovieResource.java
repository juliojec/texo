package br.com.texo.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.texo.model.Interval;
import br.com.texo.model.Movie;
import br.com.texo.model.Producer;
import br.com.texo.model.WinnerEnum;
import br.com.texo.repository.MovieRepository;

@Path("/movie")
@RequestScoped
@Transactional
public class MovieResource {
  
  @Inject
  MovieRepository movieRepository;

  @GET
  @Path("interval")
  @Produces(MediaType.APPLICATION_JSON)
  public Response maxInterval() {
    
    List<Movie> list = movieRepository.find("winner", WinnerEnum.yes).list();
    List<Movie> listWinners = new ArrayList<Movie>();
    
    List<Producer> producers = new ArrayList<Producer>();
    
    for (Movie movie : list) {
      List<Movie> moviesByProducer = list.stream().filter(m -> m.getProducer().equals(movie.getProducer())).collect(Collectors.toList());
      if(moviesByProducer.size() > 1 && !listWinners.stream().filter(l -> l.getIdMovie().equals(movie.getIdMovie())).findAny().isPresent()) {
        listWinners.add(movie);
      }
    }
    
    Map<String, Movie> max = listWinners.stream().collect(Collectors.toMap(Movie::getProducer, Function.identity(), 
        BinaryOperator.maxBy(Comparator.comparing(Movie::getYear))));
    
    Map<String, Movie> min = listWinners.stream().collect(Collectors.toMap(Movie::getProducer, Function.identity(), 
        BinaryOperator.minBy(Comparator.comparing(Movie::getYear))));
    
    max.forEach((produtor, movie) -> {
        Producer producer = new Producer();
        producer.setProducer(produtor);
        producer.setPreviousWin(min.get(produtor).getYear());
        producer.setFollowingWin(movie.getYear());
        Integer yearMin = min.get(produtor).getYear();
        producer.setInterval(movie.getYear() - yearMin);
        producers.add(producer);
    });
    
    Interval intervals = new Interval();
    
    Producer producerMaxInterval = producers
        .stream()
        .min(Comparator.comparing(Producer::getInterval))
        .orElseThrow(NoSuchElementException::new);
    
    Producer producerMinInterval = producers
        .stream()
        .min(Comparator.comparing(Producer::getInterval))
        .orElseThrow(NoSuchElementException::new);
    
    intervals.setMax(Arrays.asList(producerMaxInterval));
    intervals.setMin(Arrays.asList(producerMinInterval));
    
    return Response.ok(intervals).build();
  }
  
  @GET
  @Path("list")
  @Produces(MediaType.APPLICATION_JSON)
  public Response lista() throws FileNotFoundException, IOException {
    
    List<Movie> lista = movieRepository.listAll();
    return Response.ok(lista).build();
  }
  
}

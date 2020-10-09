package br.com.texo.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.texo.model.Movie;
import br.com.texo.repository.MovieRepository;

@ApplicationScoped
public class MovieService {
  
  @Inject
  MovieRepository movieRepository;
  
  public void save(List<Movie> movies) {
    movieRepository.persist(movies);
  }
    
}

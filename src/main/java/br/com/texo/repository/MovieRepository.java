package br.com.texo.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import br.com.texo.model.Movie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

  public Optional<Movie> findByTitle(String title){
    return find("title", title).firstResultOptional();
  }
  
  public List<Movie> findCooperados(Integer year){
    return find("year", year).list();
  }
  
}

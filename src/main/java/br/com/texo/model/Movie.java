package br.com.texo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  @Column(name = "id_movie")
  private Integer idMovie;
  
  @Column(name = "year")
  private Integer year;
  
  @Column(name = "title")
  private String title;
  
  @Column(name = "studio")
  private String studio;
  
  @Column(name = "producer")
  private String producer;
  
  @Column(name = "winner")
  @Enumerated(EnumType.STRING)
  private WinnerEnum winner;

  public Integer getIdMovie() {
    return idMovie;
  }

  public void setIdMovie(Integer idMovie) {
    this.idMovie = idMovie;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getStudio() {
    return studio;
  }

  public void setStudio(String studio) {
    this.studio = studio;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public WinnerEnum getWinner() {
    return winner;
  }

  public void setWinner(WinnerEnum winner) {
    this.winner = winner;
  }
  
}

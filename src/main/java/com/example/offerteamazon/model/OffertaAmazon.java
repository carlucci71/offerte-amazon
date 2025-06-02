package com.example.offerteamazon.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OffertaAmazon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeProdotto;
    private String descrizione;
    private String urlImmagine;
    private String urlAffiliato;
    private double prezzoSegnalato;
    private double prezzoOriginale;
    private LocalDateTime dataSegnalazione = LocalDateTime.now();

    public Long getId() { return id; }
    public String getNomeProdotto() { return nomeProdotto; }
    public void setNomeProdotto(String nomeProdotto) { this.nomeProdotto = nomeProdotto; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getUrlImmagine() { return urlImmagine; }
    public void setUrlImmagine(String urlImmagine) { this.urlImmagine = urlImmagine; }
    public String getUrlAffiliato() { return urlAffiliato; }
    public void setUrlAffiliato(String urlAffiliato) { this.urlAffiliato = urlAffiliato; }
    public double getPrezzoSegnalato() { return prezzoSegnalato; }
    public void setPrezzoSegnalato(double prezzoSegnalato) { this.prezzoSegnalato = prezzoSegnalato; }
    public double getPrezzoOriginale() { return prezzoOriginale; }
    public void setPrezzoOriginale(double prezzoOriginale) { this.prezzoOriginale = prezzoOriginale; }
    public LocalDateTime getDataSegnalazione() { return dataSegnalazione; }
    public void setDataSegnalazione(LocalDateTime dataSegnalazione) { this.dataSegnalazione = dataSegnalazione; }
}

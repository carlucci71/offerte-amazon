package com.example.offerteamazon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Cerca {

    public Cerca() {
    }

    public Cerca(double prezzoMassimo) {
        this.prezzoMassimo = prezzoMassimo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descrizione;
    private String email;
    private String telegram;
    private double prezzoMassimo;
    private LocalDateTime dataRicerca = LocalDateTime.now();

    public Long getId() { return id; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelegram() { return telegram; }
    public void setTelegram(String telegram) { this.telegram = telegram; }
    public double getPrezzoMassimo() { return prezzoMassimo; }
    public void setPrezzoMassimo(double prezzoMassimo) { this.prezzoMassimo = prezzoMassimo; }
    public LocalDateTime getDataRicerca() { return dataRicerca; }
    public void setDataRicerca(LocalDateTime dataRicerca) { this.dataRicerca = dataRicerca; }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cerca{");
        sb.append("id=").append(id);
        sb.append(", descrizione='").append(descrizione).append('\'');
        sb.append(", telegram='").append(telegram).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", prezzoMassimo=").append(prezzoMassimo);
        sb.append(", dataRicerca=").append(dataRicerca);
        sb.append('}');
        return sb.toString();
    }
}

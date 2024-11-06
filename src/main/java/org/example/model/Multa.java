package org.example.model;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "multa")
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multa")
    private int id_multa;

    @Column(name ="precio")
    private double precio;

    @Column(name = "fecha")
    private LocalDate fecha;


    @ManyToOne
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    private Coche coche;


    public Multa() {
    }

    public Multa(double precio, LocalDate fecha, Coche coche) {
        this.precio = precio;
        this.fecha = fecha;
        this.coche=coche;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }

    public int getId_multa() {
        return id_multa;
    }



    public void setId_multa(int id_multa) {
        this.id_multa = id_multa;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


}

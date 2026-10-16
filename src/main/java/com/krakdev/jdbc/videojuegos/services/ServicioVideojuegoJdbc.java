package com.krakdev.jdbc.videojuegos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krakdev.jdbc.videojuegoss.VideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuego;

@Service
public class ServicioVideojuegoJdbc {

    private VideojuegoJdbc videojuegoJdbc = new VideojuegoJdbc();
    

    //  Servicio para crear----------------------------------------------------------
    public Videojuego crear(Videojuego videojuego) {
        return videojuegoJdbc.insertar(videojuego);
    }

    // Servicio para listar ----------------------------------------------
    public List<Videojuego> listar() {
        return videojuegoJdbc.listar();
    }

    //  Servicio para buscar por código--------------------------------------------
    public Videojuego buscarPorCodigo(String codigo) {
        return videojuegoJdbc.buscarPorCodigo(codigo);
    }

    //  Servicio para actualizar -------------------------------------------------
    public Videojuego actualizar(Videojuego videojuego) {
        return videojuegoJdbc.actualizar(videojuego);
    }

    // Servicio para eliminar por código----------------------------------------
    public boolean eliminar(String codigo) {
        return videojuegoJdbc.eliminar(codigo);
    }
}
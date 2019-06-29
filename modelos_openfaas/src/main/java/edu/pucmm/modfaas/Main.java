package edu.pucmm.modfaas;

import edu.pucmm.modfaas.entidades.Estudiante;
import edu.pucmm.modfaas.servicios.EstudianteService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EstudianteService estudianteService = EstudianteService.getInstancia();

        //borrando si existe.
        estudianteService.removeEstudiante(20011136);

        //creando el registro del estudiante
        estudianteService.crearEstudiante(new Estudiante(20011136,
                "Carlos Camacho",
                "Telemática"));

        //listando los estudiantes.
        List<Estudiante> listaEstudiantes = estudianteService.getListaEstudiantes();
        listaEstudiantes.forEach(e -> System.out.println(e.toString()));

        //actualizando
        estudianteService.actualizarEstudiante(new Estudiante(20011136,
                "Carlos Camacho",
                "Sistemas"));

        //listando los estudiantes.
        listaEstudiantes = estudianteService.getListaEstudiantes();
        listaEstudiantes.forEach(e -> System.out.println(e.toString()));

    }
}

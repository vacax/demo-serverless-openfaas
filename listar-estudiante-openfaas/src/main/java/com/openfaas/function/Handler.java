package com.openfaas.function;

import com.google.gson.Gson;
import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import edu.pucmm.modfaas.entidades.Estudiante;
import edu.pucmm.modfaas.servicios.EstudianteService;

import java.util.List;

public class Handler extends com.openfaas.model.AbstractHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();

        //Listado el usuario.
        try {
            Gson gson = new Gson();
            List<Estudiante> lista = EstudianteService.getInstancia().getListaEstudiantes();
            res.setContentType("application/json");
            res.setBody(gson.toJson(lista));
        }catch (Exception e){
            res.setStatusCode(500);
            res.setBody("Error: "+e.getMessage());
        }

        return res;
    }
}

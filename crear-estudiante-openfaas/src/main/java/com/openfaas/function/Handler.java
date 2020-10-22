package com.openfaas.function;

import com.google.gson.Gson;
import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import edu.pucmm.modfaas.entidades.Estudiante;
import edu.pucmm.modfaas.servicios.EstudianteService;
import edu.pucmm.modfaas.util.Utilidades;

public class Handler extends com.openfaas.model.AbstractHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();

        //leyendo la información desde la variable de ambiente
        Utilidades.ConfiguracionDb config = Utilidades.getConfiguracionDb();
        System.out.println("La información de ambiente recuperada: "+config.toString());

        //
        System.out.println("Recuperando Path Raw: "+req.getPathRaw());
        System.out.println("Recuperando QueryRaw: "+req.getQueryRaw());
        System.out.println("Recuperando Body: "+req.getBody());
        req.getHeaders().forEach((k,v) -> System.out.println(String.format("Header[%s] = %s",k,v)));


        //Recuperando el usuario.
        try {

            Gson gson = new Gson();
            Estudiante estudiante = new Gson().fromJson(req.getBody(), Estudiante.class);
            EstudianteService.getInstancia(config).crearEstudiante(estudiante);
            res.setContentType("application/json");
            res.setBody(gson.toJson(estudiante));

        }catch (Exception e){
            res.setStatusCode(500);
            res.setBody("Error: "+e.getMessage());
        }

        return res;
    }
}

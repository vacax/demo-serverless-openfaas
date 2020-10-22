package com.openfaas.function;

import com.google.gson.Gson;
import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import edu.pucmm.modfaas.entidades.Estudiante;
import edu.pucmm.modfaas.servicios.EstudianteService;

public class Handler extends com.openfaas.model.AbstractHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();

        //Eliminando
        try {

            //
            System.out.println("Recuperando Path Raw: "+req.getPathRaw());
            System.out.println("Recuperando QueryRaw: "+req.getQueryRaw());
            System.out.println("Recuperando Body: "+req.getBody());
            req.getHeaders().forEach((k,v) -> System.out.println(String.format("Header[%s] = %s",k,v)));

            //
            Gson gson = new Gson();
            Estudiante estudiante = new Gson().fromJson(req.getBody(), Estudiante.class);
            System.out.println("Obteniendo el estudiante: "+estudiante.toString());
            boolean ok = EstudianteService.getInstancia().removeEstudiante(estudiante.getMatricula());
            res.setContentType("application/json");
            res.setBody(gson.toJson(ok));

        }catch (Exception e){
            res.setStatusCode(500);
            res.setBody("Error: "+e.getMessage());
            e.printStackTrace();
        }

        return res;

    }
}

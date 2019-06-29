package com.openfaas.function;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Handler implements com.openfaas.model.IHandler {

    /**
     * 
     * @param req
     * @return
     */
    public IResponse Handle(IRequest req) {
        Response res = new Response();
        try {
            res.setContentType("text/html");
            res.setBody(cargarTemplate());
        }catch (Exception e){
            e.printStackTrace();
            res.setStatusCode(500);
            res.setBody("Error: "+e.getMessage());
        }

        return res;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    private Configuration getConfiguracion() throws Exception{
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        return cfg;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    private String cargarTemplate() throws Exception {
        Configuration cfg = getConfiguracion();
        Map mapa = new HashMap();
        mapa.put("mensaje", "Hola Mundo");
        mapa.put("titulo", "Prueba");
        Template template = cfg.getTemplate("index.ftl");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(output);

        template.process(mapa, out);
        out.close();

        return output.toString("UTF-8");
    }
}

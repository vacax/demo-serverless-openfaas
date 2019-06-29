package edu.pucmm.modfaas.servicios;

import edu.pucmm.modfaas.entidades.Estudiante;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class EstudianteService {

    private static EstudianteService instancia;

    /**
     * 
     */
    private EstudianteService(){

    }

    /**
     *
     * @return
     */
    public static EstudianteService getInstancia(){
        if(instancia==null){
            instancia = new EstudianteService();
        }
        return instancia;
    }


    private Sql2o getConexion(){
        Sql2o sql2o = new Sql2o("jdbc:mysql://192.168.77.10:3306/openfaas?useSSL=false", "root", "12345678");
        return sql2o;
    }

    /**
     * 
     * @param estudiante
     * @return
     */
    public Estudiante crearEstudiante(Estudiante estudiante){
        String insertSql =
                "insert into estudiante(matricula, nombre, carrera) " +
                        "values (:matricula, :nombre, :carrera)";

        try (Connection con = getConexion().open()) {
            con.createQuery(insertSql)
                    .addParameter("matricula", estudiante.getMatricula())
                    .addParameter("nombre", estudiante.getNombre())
                    .addParameter("carrera", estudiante.getCarrera())
                    .executeUpdate();
        }

        return estudiante;
    }


    public List<Estudiante> getListaEstudiantes(){
        String sql = "select * from estudiante";


        try(Connection con = getConexion().open()) {
            return con.createQuery(sql).executeAndFetch(Estudiante.class);
        }
    }

    /**
     * 
     * @param matricula
     * @return
     */
    public boolean removeEstudiante(int matricula){
        boolean ok = false;
        String deleteSql ="delete from estudiante where matricula=:matricula";

        try (Connection con = getConexion().open()) {
            con.createQuery(deleteSql)
                    .addParameter("matricula", matricula)
                    .executeUpdate();
            ok = true;
        }

        return ok;
    }


    public Estudiante actualizarEstudiante(Estudiante estudiante){
        String updateSql = "update estudiante set nombre=:nombre, carrera=:carrera" +
                " where matricula = :matricula ";

        try (Connection con = getConexion().open()) {
            con.createQuery(updateSql)
                    .addParameter("matricula", estudiante.getMatricula())
                    .addParameter("nombre", estudiante.getNombre())
                    .addParameter("carrera", estudiante.getCarrera())
                    .executeUpdate();
        }

        return estudiante;
    }


}

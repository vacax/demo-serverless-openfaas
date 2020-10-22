package edu.pucmm.modfaas.util;

public class Utilidades {

    public static ConfiguracionDb getConfiguracionDb(){
        ConfiguracionDb config = new ConfiguracionDb();
        config.setIp(System.getenv("database_ip"));
        config.setPuerto(3306); //Integer.parseInt(System.getenv("database_port"))
        config.setNombre(System.getenv("database_name"));
        config.setUsuario(System.getenv("database_user"));
        config.setPassword(System.getenv("database_password"));
        return config;
    }

    public static class ConfiguracionDb{
        String ip;
        int puerto;
        String nombre;
        String usuario;
        String password;

        @Override
        public String toString() {
            return "ConfiguracionDb{" +
                    "ip='" + ip + '\'' +
                    ", puerto=" + puerto +
                    ", nombre='" + nombre + '\'' +
                    ", usuario='" + usuario + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPuerto() {
            return puerto;
        }

        public void setPuerto(int puerto) {
            this.puerto = puerto;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

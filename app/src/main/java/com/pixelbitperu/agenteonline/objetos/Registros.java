package com.pixelbitperu.agenteonline.objetos;

public class Registros {


    String registroID;
    String dni;
    String apellidoPaterno;
    String apellidoMaterno;
    String nombres;
    String tipoProducto;

    public Registros(String registroID, String dni, String apellidoPaterno, String apellidoMaterno, String nombres, String tipoProducto) {
        this.registroID = registroID;
        this.dni = dni;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombres = nombres;
        this.tipoProducto = tipoProducto;
    }

    public String getRegistroID() {
        return registroID;
    }

    public void setRegistroID(String registroID) {
        this.registroID = registroID;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
}

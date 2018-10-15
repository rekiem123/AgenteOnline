package com.pixelbitperu.agenteonline.objetos;

public class Clientes {


    String clienteID;
    String dni;
    String apellidoPaterno;
    String apellidoMaterno;
    String nombres;
    String razonSocial;
    String tipoProducto;
    String direccionFormulario;
    String latitud;
    String longitud;
    String direccionAutomatica;
    String fotoID;


    public Clientes(String clienteID, String dni, String apellidoPaterno, String apellidoMaterno, String nombres, String razonSocial,String tipoProducto,
                            String direccionFormulario, String latitud, String longitud, String direccionAutomatica, String fotoID) {

        this.clienteID = clienteID;
        this.dni = dni;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombres = nombres;
        this.razonSocial = razonSocial;
        this.tipoProducto = tipoProducto;

        this.direccionFormulario = direccionFormulario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccionAutomatica = direccionAutomatica;
        this.fotoID = fotoID;

    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getFotoID() {
        return fotoID;
    }

    public void setFotoID(String fotoID) {
        this.fotoID = fotoID;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionFormulario() {
        return direccionFormulario;

    }

    public void setDireccionFormulario(String direccionFormulario) {
        this.direccionFormulario = direccionFormulario;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccionAutomatica() {
        return direccionAutomatica;
    }

    public void setDireccionAutomatica(String direccionAutomatica) {
        this.direccionAutomatica = direccionAutomatica;
    }

    public String getClienteID() {
        return clienteID;
    }

    public void setClienteID(String clienteID) {
        this.clienteID = clienteID;
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

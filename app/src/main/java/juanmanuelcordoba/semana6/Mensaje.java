package juanmanuelcordoba.semana6;

import java.io.Serializable;

/**
 * Created by estudiante on 24/02/17.
 */

public class Mensaje implements Serializable {
    private final String correo,contraseña,telefono;

    public Mensaje(String correo,String contraseña, String telefono){
        this.correo=correo;
        this.contraseña=contraseña;
        this.telefono= telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

}

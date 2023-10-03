public class Producto {
    private String codigo;
    private String nombre;
    private double precioUnitario;
    private int cantidad;

    public Producto(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}

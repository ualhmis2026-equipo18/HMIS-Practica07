package practicas.practica03.parte01;

public class Usuario implements Comparable<Usuario> {
	private final String usuarioId;
	private String documentoId;
	private String direccion;
	 
	public Usuario(String usuarioId) {
		this.usuarioId = usuarioId.trim().toLowerCase();
	}
	
	public String getUsuarioId() {
		return this.usuarioId;
	}
	
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public int hashCode() {
		return usuarioId.hashCode();
	}
	
	@Override
	public String toString() {
		String result = this.usuarioId;

		if (this.documentoId != null) {
			result += " <" + this.documentoId + ">";
		}

		if (this.direccion != null) {
	    	result += " - " + this.direccion;
	    }
		
		return result;
	}
	
	@Override
	public int compareTo(Usuario o) {
		return this.usuarioId.compareTo(o.usuarioId);
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Usuario usuario = (Usuario) o;
	    return this.usuarioId.equals(usuario.usuarioId);
	}
}

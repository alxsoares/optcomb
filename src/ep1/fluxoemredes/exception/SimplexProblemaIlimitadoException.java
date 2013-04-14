/**
 * 
 */
package ep1.fluxoemredes.exception;

/**
 * @author wanderley
 * 
 */
public class SimplexProblemaIlimitadoException extends Exception {
	
	public SimplexProblemaIlimitadoException () {
		super ("Problema ilimitado!");
	}
	
	public SimplexProblemaIlimitadoException (String mensagem) {
		super (mensagem);
	}
}

/**
 * 
 */
package ep1.fluxoemredes.exception;

/**
 * @author wanderley
 *
 */
public class SimplexProblemaInviavelException extends Exception {

	public SimplexProblemaInviavelException () {
		super ("Problema Inviável!");
	}
	
	public SimplexProblemaInviavelException (String mensagem) {
		super (mensagem);
	}
}

package eu.europa.ec.jrc.marex.core.sub;


import static java.lang.String.format;


public class CiseTransportException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public CiseTransportException(Throwable cause) {
        super(cause);
    }


    public CiseTransportException(String message, Object... args) {
        super(format(message, args));
    }

    public CiseTransportException(String message, Throwable t, Object... args) {
        super(format(message, args), t);
    }

    @Override
    public String toString() {
        return format("[%s]=%s", getClass().getSimpleName(), getMessage());
    }
}

/* REPENTIR
package jrc.cise.gw.exceptions;
needed in some class of imported package jrc.cise.transport;

**/

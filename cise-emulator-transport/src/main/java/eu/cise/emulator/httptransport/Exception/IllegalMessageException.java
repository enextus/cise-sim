package eu.cise.emulator.httptransport.Exception;

import static java.lang.String.format;

@SuppressWarnings("unused")
public class IllegalMessageException extends RuntimeException {

    public static final String NULL_MESSAGE =
            "A Gateway MUST process only not null messages but\n" +
                    "the message passed to the processor was null.\n " +
                    "Please check that the invoking method passes a\n" +
                    "not null message.\n" +
                    "(Hint: check the SubmissionAgent implementation)\n";


    public static final String RECIPIENT_ID_AND_PROFILE_NOT_SPECIFIED =
            "A Gateway MUST process only not null messages but\n" +
                    "the message passed to the processor was null.\n " +
                    "Please check that the invoking method passes a\n" +
                    "not null message.\n" +
                    "(Hint: check the SubmissionAgent implementation)\n";

    public static final String RECIPIENT_ID_AND_PROFILE_BOTH_SPECIFIED =
            "A Gateway MUST process only not null messages but\n" +
                    "the message passed to the processor was null.\n " +
                    "Please check that the invoking method passes a\n" +
                    "not null message.\n" +
                    "(Hint: check the SubmissionAgent implementation)\n";


    public static final String SENDER_ID_NOT_SPECIFIED =
            "A Gateway MUST process only not null messages but\n" +
                    "the message passed to the processor was null.\n " +
                    "Please check that the invoking method passes a\n" +
                    "not null message.\n" +
                    "(Hint: check the SubmissionAgent implementation)\n";

    public static final String SENDER_ADDRESS_NOT_SPECIFIED =
            "A Gateway MUST process only not null messages but\n" +
                    "the message passed to the processor was null.\n " +
                    "Please check that the invoking method passes a\n" +
                    "not null message.\n" +
                    "(Hint: check the SubmissionAgent implementation)\n";


    public IllegalMessageException(Throwable cause) {
        super(cause);
    }

    public IllegalMessageException(String message, Throwable t, Object... args) {
        super(format(message, args), t);
    }

    public IllegalMessageException(String message) {
        super(message);
    }

    /**
     * public IllegalMessageException(String message, Throwable t, Object... args) {
     * super(message, t, args);
     * }
     * <p>
     * public IllegalMessageException(String message, Throwable t, Object...args) {
     * super(format(message, args), t);
     * }
     **/
    @Override
    public String toString() {
        return format("[%s]=%s", getClass().getSimpleName(), getMessage());
    }


}

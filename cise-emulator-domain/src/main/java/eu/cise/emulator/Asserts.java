package eu.cise.emulator;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("ALL")
public class Asserts {

    public static <T extends Exception> void asserts(boolean expression) throws T {
        asserts(expression, null);
    }

    public static <T extends Exception> void asserts(boolean expression, Class<T> ex) throws T {
        if (!expression) {
            generateException(ex, null);
        }
    }

    public static <O, T extends Exception> O notNull(O reference) throws T {
        return notNull(reference, null);
    }

    public static <O, T extends Exception> O notNull(O reference, Class<T> ex) throws T {
        if (reference == null) {
            generateException(ex, null);
        }
        return reference;
    }

    public static <O, T extends Exception> O instanceOf(O reference,
                                                        Class<?> wantedClass) throws T {
        return instanceOf(reference, wantedClass, null);
    }

    public static <O, T extends Exception> O instanceOf(O reference,
                                                        Class<?> wantedClass,
                                                        Class<T> ex) throws T {
        if (!wantedClass.isAssignableFrom(reference.getClass())) {
            generateException(ex, null);
        }
        return reference;
    }

    public static <O, T extends Exception> O notInstanceOf(O reference,
                                                           Class<?> wantedClass) throws T {
        return notInstanceOf(reference, wantedClass, null);
    }

    public static <O, T extends Exception> O notInstanceOf(O reference,
                                                           Class<?> wantedClass,
                                                           Class<T> ex) throws T {
        if (wantedClass.isAssignableFrom(reference.getClass())) {
            generateException(ex, null);
        }

        return reference;
    }

    private static <T extends Exception> void generateException(Class<T> exClass,
                                                                String errorMessage,
                                                                Object... args) throws T {
        try {
            if (errorMessage == null) {
                throw exClass.newInstance();
            } else {
                throw exClass.getDeclaredConstructor(String.class, Object[].class)
                        .newInstance(errorMessage, args);
            }
        } catch (InstantiationException |
                NoSuchMethodException |
                InvocationTargetException |
                IllegalAccessException e) {
            e.printStackTrace(); // TODO handle it better
        }
    }
}

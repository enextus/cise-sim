/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.cise.sim.helpers;

import static com.google.common.base.Strings.isNullOrEmpty;

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

    public static <T extends Exception> String notBlank(String reference) throws T {
        return notBlank(reference, null);
    }

    public static <T extends Exception> String notBlank(String reference, Class<T> ex) throws T {
        if (isNullOrEmpty(reference)) {
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

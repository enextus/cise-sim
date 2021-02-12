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

package eu.cise.sim.engine;

import eu.cise.servicemodel.v1.message.Acknowledgement;

import java.io.Serializable;
import java.util.Objects;

/**
 * Dispatching a message will produce a result (success or failure) with a
 * related message.
 * <br>
 * This class is mostly an immutable  value object that holds the resulting
 * status.
 */
@SuppressWarnings("unused")
public class DispatchResult  implements Serializable {

    // life, the universe and the everything.
    private static final long serialVersionUID = 42L;

    // internal state
    private final boolean ok;
    private final Acknowledgement result;

    /**
     * The constructor allow to create an immutable object that will contain
     * the status (success or failure) using a boolean and a string containing
     * a message
     *
     * @param ok     a boolean indicating if it's successful or not
     * @param result a result message describing the status
     */
    public DispatchResult(boolean ok, Acknowledgement result) {
        this.ok = ok;
        this.result = result;
    }

    /**
     * @return the status of the result: true is successful while false is
     * failed
     */
    public boolean isOK() {
        return ok;
    }

    /**
     * @return an additional message of the results that in the failure could
     * contain a description of the error
     */
    public Acknowledgement getResult() {
        return result;
    }

    // To compare value objects we need equals and hashcode methods
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DispatchResult that = (DispatchResult) o;

        if (ok != that.ok) return false;
        return Objects.equals(result, that.result);
    }

    public int hashCode() {
        int result1 = (ok ? 1 : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    public String toString() {
        return "DispatchResult{" +
                "ok=" + ok +
                ", result='" + result + '\'' +
                '}';
    }

}

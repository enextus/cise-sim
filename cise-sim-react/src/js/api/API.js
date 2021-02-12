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

import axios from 'axios';
import Error from '../errors/Error';

export const http_get = async (service, params) => {
  try {
    let response = await axios.get(
        getServiceURL(service),
        {
          headers: {
            'Content-Type': 'application/json'
          },
          params: params
        });

    return response.data;
  } catch (error) {
    console.error("request GET " + getServiceURL(service) + " failed.");
    return handleError(error);
  }
};

export const http_post = async (service, data) => {
  try {
    let response = await axios.post(
        getServiceURL(service),
        data,
        {
          headers: {
            'Content-Type': 'application/json'
          }
        });

    return response.data;

  } catch (error) {
    console.error("request POST " + getServiceURL(service) + " failed.");
    return handleError(error);
  }
};

export const http_delete = async (service) => {
  try {
    let response = await axios.delete(
        getServiceURL(service),
        {
          headers: {
            'Content-Type': 'application/json'
          },
          params: {}
        });

    return response.data;
  } catch (error) {
    console.error("request DELETE " + getServiceURL(service) + " failed.");
    return handleError(error);
  }
};

const getServiceURL = service => {
  return getHost().concat("/api/ui/").concat(service);
};

const getHost = () => {
    if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {

        if (window.location.hostname.includes("localhost")) {
            return window.location.protocol.concat("//").concat(
                window.location.hostname).concat(":8200");
        }

    }

    return window.location.protocol.concat("//").concat(window.location.host);
};

const handleError = (error) => {
  if (error.response) {
    /*
     * The request was made and the server responded with a
     * status code that falls out of the range of 2xx
     */
    console.log(error.response.data);
    console.log(error.response.status);
    console.log(error.response.headers);
    return new Error(error.response.status, error.response.data.error);
  } else if (error.request) {
    /*
     * The request was made but no response was received, `error.request`
     * is an instance of XMLHttpRequest in the browser and an instance
     * of http.ClientRequest in Node.js
     */
    console.log(error.request);
    return new Error(1, "Unable to reach the server: " + error.message);
  } else {
    // Something happened in setting up the request and triggered an Error
    console.log(error.message);
    return new Error(-1, "Unknown error : " + error.message);
  }
}

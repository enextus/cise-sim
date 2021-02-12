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

import {getTemplateById, getTemplateList} from './TemplateService';
import {action, computed, observable} from 'mobx';
import Template from './Template';

export default class TemplateStore {
    @observable templateList = [];
    @observable selected = "empty"; // selectedTemplateId
    @observable messageId = this.uuidv4();
    @observable correlationId = "";
    @observable requiresAck = false;
    @observable template = new Template({templateId: "", templateName: "", templateContent: ""});

    @computed
    get templateOptions() {
        return this.templateList.map(t => ({label: t.name, value: t.id}));
    }
    @computed
    get isTemplateSelected() {
        return !(this.selected === "empty");
    }

    @action
    createNewMessageId() {
        this.messageId = this.uuidv4();
    }


    @action
    resetPreview() {
       this.template = new Template({templateId: "", templateName: "", templateContent: ""});
    }


    @action
    async loadTemplateList() {

        if (this.templateList.length === 0) {
            const templates = await getTemplateList();
            templates.forEach(t => this.templateList.push(t));
        }
    }

    @action
    toggleRequiresAck() {
        this.requiresAck = !this.requiresAck;
    }

    @action
    async preview() {
        const getTemplateByIdResposnse = await getTemplateById(
            this.selected,
            this.messageId,
            this.correlationId,
            this.requiresAck);

        if (getTemplateByIdResposnse.errorCode) {
            console.log("TemplateStore preview returned an error." + getTemplateByIdResposnse.errorMessage);
        }

        this.template = getTemplateByIdResposnse;

        return getTemplateByIdResposnse;
    }

    uuidv4() {
        return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }
}

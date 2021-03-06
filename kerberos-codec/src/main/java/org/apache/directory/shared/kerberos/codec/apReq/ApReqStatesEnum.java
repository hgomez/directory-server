/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.directory.shared.kerberos.codec.apReq;


import org.apache.directory.api.asn1.ber.grammar.Grammar;
import org.apache.directory.api.asn1.ber.grammar.States;


/**
 * This class store the AP-REQ grammar's constants. It is also used for debugging
 * purpose
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public enum ApReqStatesEnum implements States
{
    // Start
    START_STATE, // 0

    // ----- AP-REQ message --------------------------------------
    AP_REQ_STATE, // 1
    AP_REQ_SEQ_STATE, // 2

    AP_REQ_PVNO_TAG_STATE, // 3
    AP_REQ_PVNO_STATE, // 4

    AP_REQ_MSG_TYPE_TAG_STATE, // 5
    AP_REQ_MSG_TYPE_STATE, // 6

    AP_REQ_AP_OPTIONS_TAG_STATE, // 7
    AP_REQ_AP_OPTIONS_STATE, // 8

    AP_REQ_TICKET_STATE, // 9

    AP_REQ_AUTHENTICATOR_STATE, // 10

    // End
    LAST_AP_REQ_STATE; // 11

    /**
     * Get the grammar name
     *
     * @param grammar The grammar code
     * @return The grammar name
     */
    public String getGrammarName( int grammar )
    {
        return "AP_REQ_GRAMMAR";
    }


    /**
     * Get the grammar name
     *
     * @param grammar The grammar class
     * @return The grammar name
     */
    public String getGrammarName( Grammar<ApReqContainer> grammar )
    {
        if ( grammar instanceof ApReqGrammar )
        {
            return "AP_REQ_GRAMMAR";
        }
        else
        {
            return "UNKNOWN GRAMMAR";
        }
    }


    /**
     * Get the string representing the state
     *
     * @param state The state number
     * @return The String representing the state
     */
    public String getState( int state )
    {
        return ( ( state == LAST_AP_REQ_STATE.ordinal() ) ? "AP_REQ_END_STATE" : name() );
    }


    /**
     * {@inheritDoc}
     */
    public boolean isEndState()
    {
        return this == LAST_AP_REQ_STATE;
    }


    /**
     * {@inheritDoc}
     */
    public ApReqStatesEnum getStartState()
    {
        return START_STATE;
    }
}

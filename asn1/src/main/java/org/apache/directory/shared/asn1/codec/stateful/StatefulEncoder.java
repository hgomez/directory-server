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
package org.apache.directory.shared.asn1.codec.stateful;


import org.apache.directory.shared.asn1.codec.EncoderException;


/**
 * The StatefulEncoder interface.
 * 
 * @author <a href="mailto:dev@directory.apache.org"> Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public interface StatefulEncoder
{
    /**
     * Encodes a Message object piece by piece often emitting chunks of the
     * final PDU to the callback if present.
     * 
     * @param obj the message object to encode into a PDU
     * @throws EncoderException if there are problems while encoding
     */
    void encode( Object obj ) throws EncoderException;


    /**
     * Sets the callback of the underlying implementation. There is no need for
     * any special callbacks because when encoding we do not need to transform
     * before a value return as we did in the decoder.
     * 
     * @param cb the callback to set on the underlying provider specific encoder
     */
    void setCallback( EncoderCallback cb );


    /**
     * Sets the monitor of the underlying implementation.
     * 
     * @param monitor the monitor to set on the underlying implementation
     */
    void setEncoderMonitor( EncoderMonitor monitor );

}

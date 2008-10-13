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
package org.apache.directory.mitosis.service.protocol.codec;


import org.apache.directory.mitosis.service.protocol.Constants;
import org.apache.directory.mitosis.service.protocol.message.BaseMessage;
import org.apache.directory.mitosis.service.protocol.message.BeginLogEntriesMessage;
import org.apache.directory.server.schema.registries.Registries;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class BeginLogEntriesMessageDecoder extends BaseMessageDecoder
{

    public BeginLogEntriesMessageDecoder()
    {
        super( Constants.BEGIN_LOG_ENTRIES, 0, 0 );
    }


    protected BaseMessage decodeBody( Registries registries, int sequence, int bodyLength, ByteBuffer in ) throws Exception
    {
        return new BeginLogEntriesMessage( sequence );
    }


    public void finishDecode( IoSession session, ProtocolDecoderOutput out ) throws Exception
    {
    }
}

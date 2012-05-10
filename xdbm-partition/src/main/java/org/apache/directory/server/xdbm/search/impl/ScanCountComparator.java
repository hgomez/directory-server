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
package org.apache.directory.server.xdbm.search.impl;


import java.util.Comparator;

import org.apache.directory.server.xdbm.search.Evaluator;
import org.apache.directory.shared.ldap.model.entry.Entry;


/**
 * A helper class used to compare scan counts.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 *
 * @param <ID> The type of element
 */
public class ScanCountComparator<ID> implements Comparator<Evaluator<?, Entry, ID>>
{
    /**
     * Compare two scan counts frpm two evaluators 
     */
    public int compare( Evaluator<?, Entry, ID> e1, Evaluator<?, Entry, ID> e2 )
    {
        long scanCount1 = ( Long ) e1.getExpression().get( "count" );
        long scanCount2 = ( Long ) e2.getExpression().get( "count" );

        if ( scanCount1 == scanCount2 )
        {
            return 0;
        }

        /*
         * We want the Evaluator with the smallest scan count first
         * since this node has the highest probability of failing, or
         * rather the least probability of succeeding.  That way we
         * can short the sub-expression evaluation process.
         */
        if ( scanCount1 < scanCount2 )
        {
            return -1;
        }

        return 1;
    }

}
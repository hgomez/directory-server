/*
 *   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.eve.schema;


import org.apache.ldap.common.schema.Syntax;

import java.util.Map;
import java.util.HashMap;

import javax.naming.NamingException;


/**
 * A SyntaxRegistry service available during server startup when other resources
 * like a syntax backing store is unavailable.
 *
 * @author <a href="mailto:directory-dev@incubator.apache.org">Apache Directory Project</a>
 * @version $Rev$
 */
public class DefaultSyntaxRegistry implements SyntaxRegistry
{
    /** a map of entries using an OID for the key and a Syntax for the value */
    private final Map byOid;
    /** the OID oidRegistry this oidRegistry uses to register new syntax OIDs */
    private final OidRegistry oidRegistry;
    /** a monitor used to track noteable oidRegistry events */
    private SyntaxRegistryMonitor monitor = null;
    
    
    // ------------------------------------------------------------------------
    // C O N S T R U C T O R S
    // ------------------------------------------------------------------------
    
    
    /**
     * Creates a DefaultSyntaxRegistry.
     */
    public DefaultSyntaxRegistry( OidRegistry registry )
    {
        this.monitor = new SyntaxRegistryMonitorAdapter();
        this.oidRegistry = registry;
        this.byOid = new HashMap();
    }
    

    // ------------------------------------------------------------------------
    // SyntaxRegistry interface methods
    // ------------------------------------------------------------------------
    
    
    /**
     * @see SyntaxRegistry#lookup(java.lang.String)
     */
    public Syntax lookup( String id ) throws NamingException
    {
        id = oidRegistry.getOid( id );

        if ( byOid.containsKey( id ) )
        {
            Syntax syntax = ( Syntax ) byOid.get( id );
            monitor.lookedUp( syntax );
            return syntax;
        }
        
        NamingException fault = new NamingException( "Unknown syntax OID " + id );
        monitor.lookupFailed( id, fault );
        throw fault;
    }
    

    /**
     * @see SyntaxRegistry#register(Syntax)
     */
    public void register( Syntax syntax ) throws NamingException
    {
        if ( byOid.containsKey( syntax.getOid() ) )
        {
            NamingException e = new NamingException( "syntax w/ OID " +
                syntax.getOid() + " has already been registered!" );
            monitor.registerFailed( syntax, e );
            throw e;
        }

        oidRegistry.register( syntax.getName(), syntax.getOid() );
        byOid.put( syntax.getOid(), syntax );
        monitor.registered( syntax );
    }

    
    /**
     * @see SyntaxRegistry#hasSyntax(java.lang.String)
     */
    public boolean hasSyntax( String id )
    {
        if ( oidRegistry.hasOid( id ) )
        {
            try
            {
                return byOid.containsKey( oidRegistry.getOid( id ) );
            }
            catch ( NamingException e )
            {
                return false;
            }
        }

        return false;
    }


    // ------------------------------------------------------------------------
    // package friendly monitor methods
    // ------------------------------------------------------------------------
    
    
    /**
     * Gets the monitor for this oidRegistry.
     * 
     * @return the monitor
     */
    SyntaxRegistryMonitor getMonitor()
    {
        return monitor;
    }

    
    /**
     * Sets the monitor for this oidRegistry.
     * 
     * @param monitor the monitor to set
     */
    void setMonitor( SyntaxRegistryMonitor monitor )
    {
        this.monitor = monitor;
    }
}

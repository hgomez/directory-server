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


import java.util.Map;
import java.util.HashMap;
import javax.naming.NamingException;

import org.apache.ldap.common.schema.ObjectClass;


/**
 * A plain old java object implementation of an ObjectClassRegistry.
 *
 * @author <a href="mailto:directory-dev@incubator.apache.org">Apache Directory Project</a>
 * @version $Rev$
 */
public class DefaultObjectClassRegistry implements ObjectClassRegistry
{
    /** maps an OID to an ObjectClass */
    private final Map byOid;
    /** monitor notified via callback events */
    private ObjectClassRegistryMonitor monitor;


    // ------------------------------------------------------------------------
    // C O N S T R U C T O R S
    // ------------------------------------------------------------------------


    /**
     * Creates an empty DefaultObjectClassRegistry.
     */
    public DefaultObjectClassRegistry()
    {
        byOid = new HashMap();
        monitor = new ObjectClassRegistryMonitorAdapter();
    }


    /**
     * Sets the monitor that is to be notified via callback events.
     *
     * @param monitor the new monitor to notify of notable events
     */
    public void setMonitor( ObjectClassRegistryMonitor monitor )
    {
        this.monitor = monitor;
    }


    // ------------------------------------------------------------------------
    // Service Methods
    // ------------------------------------------------------------------------


    public void register( ObjectClass objectClass ) throws NamingException
    {
        if ( byOid.containsKey( objectClass.getOid() ) )
        {
            NamingException e = new NamingException( "objectClass w/ OID " +
                objectClass.getOid() + " has already been registered!" );
            monitor.registerFailed( objectClass, e );
            throw e;
        }

        byOid.put( objectClass.getOid(), objectClass );
        monitor.registered( objectClass );
    }


    public ObjectClass lookup( String oid ) throws NamingException
    {
        if ( ! byOid.containsKey( oid ) )
        {
            NamingException e = new NamingException( "objectClass w/ OID "
                + oid + " not registered!" );
            monitor.lookupFailed( oid, e );
            throw e;
        }

        ObjectClass objectClass = ( ObjectClass ) byOid.get( oid );
        monitor.lookedUp( objectClass );
        return objectClass;
    }


    public boolean hasObjectClass( String oid )
    {
        return byOid.containsKey( oid );
    }
}

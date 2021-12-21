package com.fiserv.luc.api.infrastructure.database.audited.revision;

import java.io.Serializable;

/**
 * @version 1.0
 */
public interface IEntity<ID extends Serializable> extends Serializable {
    /*-------------------------------------------------------------------
     * 		 				GETTERS AND SETTERS
     *-------------------------------------------------------------------*/

    /**
     * @return ID
     */
    ID getId();

    /**
     * @param id ID
     */
    void setId(ID id);
}

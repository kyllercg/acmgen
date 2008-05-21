/**
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * $Id$
 */

package net.kyllercg.acms;

import java.util.UUID;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.1
 */
public interface NodeElement {

	int defXY	=	200;
	
	/**
	 * Gets the UUID of the Petri net element
	 */
	abstract long getIndex();
	
	/**
	 * Sets the UUID of the Petri net element
	 */
	abstract void setIndex();
	
	/**
	 * Gets the label of the Petri net element
	 */
	abstract String getLabel();
	
	/**
	 * Sets the label of the Petri net element
	 */
	abstract void setLabel(String label);
	
	/**
	 * Gets the UUID of the Petri net element
	 */
	abstract UUID getUUID();
	
	/**
	 * Sets the UUID of the Petri net element
	 */
	abstract void setUUID();
}

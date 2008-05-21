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
public class Arc implements NodeElement {
	
	public static int place2trans	= 0;
	public static int trans2place	= 1;
	public static int test			= 2;
	
	/**
	 * The UUID if the source element of the arc
	 */
	NodeElement src;
	
	/**
	 * The UUID of the destination element of the arc
	 */
	NodeElement dest;
	
	/**
	 * An integer identification for the arc.
	 */
	long index;
	
	/**
	 * The label of the arc
	 */
	String label;
	
	/**
	 * The type of the arc. Indicates if the arc links a place to a transition
	 * or a transition to a place. May have the fallowing values:
	 * <p>
	 * <ul>
	 *   <li>Arc.place2trans: if the arc links a place to a transition</li>
	 *   <li>Arc.trans2place: if the arc links a transition to a place</li>
	 *   <li>Arc.test: if the arc is a test arc. i.e. it links the place to the
	 *       transition and vice-versa</li>
	 * </ul>
	 */
	int type;
	
	/**
	 * The universally unique identifier of the place. UUID is build from
	 * the label of the source and destination nodes
	 */
	UUID uuid;
	
	
	
	/**
	 * Class constructor
	 * 
	 * @param src - an integer containing the ID of the source element
	 * @param dest - an integer containing the ID of the destination element
	 * @param type - an integer specifying the type of the transition. May be:
	 * <ul>
	 *   <li>Arc.place2trans: if the arc links a place to a transition</li>
	 *   <li>Arc.trans2place: if the arc links a transition to a place</li>
	 *   <li>Arc.test: if the arc is a test arc. i.e. it links the place to the
	 *       transition and vice-versa</li>
	 * </ul>
	 */
	public Arc(NodeElement src, NodeElement dest, int type) {
		
		this.src = src;
		this.dest = dest;
		this.type = type;
		this.setLabel();
	}
	
	/**
	 * 
	 * @return The UUID of the source node of the arc
	 */
	public NodeElement getSrc() {
		
		return this.src;
	}
	
	/**
	 * 
	 * @param src
	 */
	public void setSrc(NodeElement src) {
		
		this.src = src;
		this.setUUID();
	}
	
	/**
	 * 
	 * @return the UUID of the destination node of the arc
	 */
	public NodeElement getDest() {
		
		return this.dest;
	}
	
	/**
	 * 
	 * @param dest
	 */
	public void setDest(NodeElement dest) {
		
		this.dest = dest;
		this.setUUID();
	}
	
	/**
	 * 
	 * @return the UUID of the arc
	 */
	public long getIndex() {
		
		return this.index;
	}
	
	/**
	 * Sets the UUID of the transition
	 */
	public void setIndex() {
		
		int cont;
		int lsize = this.label.length();
		char[] lchar = label.toCharArray();
		long index = 0;
		
		for (cont = 0; cont < lsize; cont++) {
			
			index += lchar[cont] * Math.pow(31, lsize - (cont + 1));
		}
		
		this.index = index;
//		this.index = this.label.hashCode();
	}
	
	/**
	 * Gets the label of the arc
	 * 
	 * @return a string containing the label of the place
	 */
	public String getLabel() {
		
		return this.label;
	}
	
	/**
	 * Sets the label of the arc
	 */
	public void setLabel() {
		
		this.label = this.src.getLabel() + "_TO_" + this.dest.getLabel();
		this.setIndex();
		this.setUUID();
	}
	
	/**
	 * Sets the label of the arc
	 * 
	 * @param label - a string containing the new label
	 */
	public void setLabel(String label) {
		
		this.label = label;
		this.setIndex();
		this.setUUID();
	}
	
	/**
	 * 
	 * @return the tye of the arc
	 */
	public int getType() {
		
		return this.type;
	}
	
	/**
	 * 
	 * @param type
	 */
	public void setType(int type) {
		
		this.type = type;
	}
	
	/**
	 * 
	 * @return the UUID of the arc
	 */
	public UUID getUUID() {
		
		return this.uuid;
	}
	
	/**
	 * Sets the UUID of the transition
	 */
	public void setUUID() {
		
		this.uuid = UUID.nameUUIDFromBytes(this.label.getBytes());
	}
}

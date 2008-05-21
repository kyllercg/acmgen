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
 * This class describes a data-structure for a Petri Net Place.
 * 
 * @author Kyller Costa Gorgônio
 * @version 1.1
 */
public class Place implements NodeElement {
	
	/**
	 * An integer identification for the arc.
	 */
	long index;
	
	/**
	 * The label of the place
	 */
	String label;
	
	/**
	 * The marking of the place
	 */
	int marking;
	
	/**
	 * The universally unique identifier of the place. UUID is build from
	 * the label
	 */
	UUID uuid;
	
	/**
	 * The horizontal coordination of the place in the grid
	 */
	int posX;
	
	/**
	 * The vertical coordination of the place in the grid
	 */
	int posY;
	
	/**
	 * Class constructor
	 * 
	 * @param label - a string containing the label of the place
	 */
	public Place(String label, int marking) {
		
		this.setLabel(label);
		this.setMarking(marking);
		this.setUUID();
		this.posX = defXY;
		this.posY = defXY;
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
//		this.index = label.hashCode();
	}
	
	/**
	 * Gets the label of the place
	 * 
	 * @return a string containing the label of the place
	 */
	public String getLabel() {
		
		return this.label;
	}
	
	/**
	 * Sets the label of the place
	 * 
	 * @param label - a string containing the new label
	 */
	public void setLabel(String label) {
		
		this.label = label;
		this.setIndex();
		this.setUUID();
	}
	
	/**
	 * Gets the marking of the place
	 * 
	 * @return an integer with the marking of the place
	 */
	public int getMarking() {
		
		return this.marking;
	}
	
	/**
	 * Sets the marking of the place
	 * 
	 * @param marking - an integer with the marking of the place
	 */
	public void setMarking(int marking) {
		
		this.marking = marking;
	}
	
	/**
	 * Gets the UUID of the place
	 * 
	 * @return an UUID object
	 */
	public UUID getUUID() {
		
		return this.uuid;
	}
	
	/**
	 * Sets the UUID of the place
	 */
	public void setUUID() {
		
		this.uuid = UUID.nameUUIDFromBytes(this.label.getBytes());
	}
	
	/**
	 * Gets the X coordinate of the place
	 * 
	 * @return an integer containing the X coordinate
	 */
	public int getX() {
		
		return this.posX;
	}
	
	/**
	 * Sets the X coordinate of the place
	 * 
	 * @param x - the new X coordinate
	 */
	public void setX(int x) {
		
		this.posX = x;
	}
	
	/**
	 * Gets the Y coordinate of the place
	 * 
	 * @return an integer containing the Y coordinate
	 */
	public int getY() {
		
		return this.posY;
	}
	
	/**
	 * Sets the Y coordinate of the place
	 * 
	 * @param y - the new Y coordinate
	 */
	public void setY(int y) {
		
		this.posY = y;
	}
}

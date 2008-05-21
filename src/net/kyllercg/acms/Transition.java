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
public class Transition implements NodeElement {
	
	public static int T_ACCESS		= 0;
	public static int T_CONTROL		= 1;
	public static int T_NONE		= 2;
	
	/**
	 * An integer identification for the arc.
	 */
	long index;
	
	/**
	 * The label of the transition
	 */
	String label;
	
	/**
	 * The universally unique identifier of the transition. UUID is build from
	 * the label
	 */
	UUID uuid;
	
	/**
	 * The horizontal coordinate of the transition in the grid
	 */
	int posX;
	
	/**
	 * The vertical coordinate of the transition in the grid
	 */
	int posY;
	
	/**
	 * Defines if the transition model a control action, an access or none
	 */
	int type;
	
	/**
	 * The number of the cell/slot to be accessed, if it's an access action
	 */
	int cell;
	
	/**
	 * The number of the source cell, if it's a control action
	 */
	int src;
	
	/**
	 * The number of the destination cell, if it's a control action
	 */
	int dest;
	
	/**
	 * Class constructor
	 * 
	 * @param label - a string containing the label of the transition
	 */
	public Transition(String label) {
		
		this.setLabel(label);
		this.setUUID();
		this.type = Transition.T_NONE;
		this.posX = defXY;
		this.posY = defXY;
	}
	
	/**
	 * Class constructor
	 * 
	 * @param label - a string containing the label of the transition
	 * @param cell - an integer with the cell number accessed by the transition
	 */
	public Transition(String label, int cell) {
		
		this.setLabel(label);
		this.setUUID();
		this.type = Transition.T_ACCESS;
		this.cell = cell;
		this.posX = defXY;
		this.posY = defXY;
	}
	
	/**
	 * Class constructor
	 * 
	 * @param label - a string containing the label of the transition
	 * @param src - the source cell
	 * @param dest - the destination cell
	 */
	public Transition(String label, int src, int dest) {
		
		this.setLabel(label);
		this.setUUID();
		this.type = Transition.T_CONTROL;
		this.src = src;
		this.dest = dest;
		this.posX = defXY;
		this.posY = defXY;
	}
	
	/**
	 * Class constructor. This is only to allow inherancy.
	 *
	 * @param none
	 */
	Transition() {
		
		super();
		this.type = Transition.T_NONE;
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
	 * Gets the label of the transition
	 * 
	 * @return a string containing the label of the transition
	 */
	public String getLabel() {
		
		return this.label;
	}
	
	/**
	 * Sets the label of the transition
	 * 
	 * @param label - a string containing the new label
	 */
	public void setLabel(String label) {
		
		this.label = label;
		this.setIndex();
		this.setUUID();
	}
	
	/**
	 * Gets the UUID of the transition
	 * 
	 * @return an UUID object
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
	
	/**
	 * Gets the source cell of the control transition
	 * 
	 * @return an integer containing the source of the transition
	 */
	public int getSrc() {
		
		return this.src;
	}
	
	/**
	 * Gets the destination cell of the control transition
	 * 
	 * @return an integer containing the destination of the transition
	 */
	public int getDest() {
		
		return this.dest;
	}
	
	/**
	 * Gets the X coordinate of the transition
	 * 
	 * @return an integer containing the X coordinate
	 */
	public int getX() {
		
		return this.posX;
	}
	
	/**
	 * Sets the X coordinate of the transition
	 * 
	 * @param x - the new X coordinate
	 */
	public void setX(int x) {
		
		this.posX = x;
	}
	
	/**
	 * Gets the Y coordinate of the transition
	 * 
	 * @return an integer containing the Y coordinate
	 */
	public int getY() {
		
		return this.posY;
	}
	
	/**
	 * Sets the Y coordinate of the transition
	 * 
	 * @param y - the new Y coordinate
	 */
	public void setY(int y) {
		
		this.posY = y;
	}
}

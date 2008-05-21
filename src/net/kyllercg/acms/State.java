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
public class State implements NodeElement {

	/**
	 * An integer identification for the state.
	 */
	long index;
	
	/**
	 * The label of the state
	 */
	String label;
	
	/**
	 * The universally unique identifier of the state. UUID is build from
	 * the label
	 */
	UUID uuid;
	
	/**
	 * The cell number the writer is currently pointing to
	 */
	int writer_cell;
	
	/**
	 * The cell number the reader is currently pointing to
	 */
	int reader_cell;
	
	/**
	 * Controls if the writer is accessing or ready to access
	 */
	boolean writing;
	
	/**
	 * Controls of the writer is accessing or ready to access
	 */
	boolean reading;
	
	
	/**
	 * Class constructor
	 * 
	 * @param label - a string containing the label of the state
	 */
	public State(String label) {
		
		this.setLabel(label);
		this.setUUID();
	}
	
	/**
	 * Class constructor
	 * @param label - a string containing the label of the state
	 * @param wr_cell - the cell number of the writer
	 * @param rd_cell - the cell number of the reader
	 * @param wr - if the writer is accessing
	 * @param rd - if the reader is accesing
	 */
	public State(String label, int wr_cell, int rd_cell,
			boolean wr, boolean rd) {
		
		this.setLabel(label);
		this.setUUID();
		this.writer_cell = wr_cell;
		this.reader_cell = rd_cell;
		this.writing = wr;
		this.reading = rd;
	}
	
	/**
	 * Gets the index number of the state
	 * @return the UUID of the state
	 */
	public long getIndex() {
		
		return this.index;
	}
	
	/**
	 * Sets the index of the state
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
	}
	
	/**
	 * Gets the label of the state
	 * @return a string containing the label of the state
	 */
	public String getLabel() {
		
		return this.label;
	}
	
	/**
	 * Sets the label of the state
	 * @param label - a string containing the new label
	 */
	public void setLabel(String label) {
		
		this.label = label;
		this.setIndex();
		this.setUUID();
	}
	
	/**
	 * Gets the UUID of the state
	 * @return an UUID object
	 */
	public UUID getUUID() {
		
		return this.uuid;
	}
	
	/**
	 * Sets the UUID of the state
	 */
	public void setUUID() {
		
		this.uuid = UUID.nameUUIDFromBytes(this.label.getBytes());
	}
	
	/**
	 * Gets the cell number that the writer is ponting to
	 * @return - an integer with the cell number
	 */
	public int getWriterCell() {
		
		return this.writer_cell;
	}
	
	/**
	 * Sets the cell number that the writer is pointing to
	 * @param cell - the new cell number
	 */
	public void setWriterCell(int cell) {
		
		this.writer_cell = cell;
	}
	
	/**
	 * Gets the cell number that the reader is ponting to
	 * @return - an integer with the cell number
	 */
	public int getReaderCell() {
		
		return this.reader_cell;
	}
	
	/**
	 * Sets the cell number that the reader is pointing to
	 * @param cell - the new cell number
	 */
	public void setReaderCell(int cell) {
		
		this.reader_cell = cell;
	}
	
	/**
	 * Checks if the writer is accessing or ready to access it
	 * @return - a boolean determining if it is accessing or not
	 */
	public boolean isWriting() {
		
		return this.writing;
	}
	
	/**
	 * Sets if the writer is accessing the ACM or ready to access it
	 * @param status - the new status of the writer
	 */
	public void setWriting(boolean status) {
		
		this.writing = status;
	}
	
	/**
	 * Checks if the reader is accessing the ACM or ready to access it
	 * @return - a boolean determining if it is accessing or not
	 */
	public boolean inReading() {
		
		return this.reading;
	}
	
	/**
	 * Sets if the reader is accessing or ready to access it
	 * @param status - the new status of the writer
	 */
	public void setReading(boolean status) {
		
		this.reading = status;
	}
}

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

import java.util.Iterator;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public abstract class ACMsys {
	
	/**
	 * The size of the ACM
	 */
	protected int size;
	
	/**
	 * The Petri net of the writer process
	 */
	protected PetriNet writer;
	
	/**
	 * The Petri net of the reader process
	 */
	protected PetriNet reader;
	
	/**
	 * The composition of the writer and reader processes
	 */
	protected PetriNet acm;

	/**
	 * Class contructor
	 */
	public ACMsys() {
		
		super();
	}
	
	/**
	 * Calculates the union of the reader and writer Petri nets. The result is
	 * stored in an internal object
	 */
	protected void calcUnion() {
		
		Place p;
		Iterator<Place> i;
		
		i = writer.getPlaces().iterator();
		while (i.hasNext()) {
			
			p = (Place)i.next();
			if (!writer.isTestPlace(p)) {
				
				acm.addPlace(p);
			}
		}
		
		i = reader.getPlaces().iterator();
		while (i.hasNext()) {
			
			p = (Place)i.next();
			if (!reader.isTestPlace(p)) {
				
				acm.addPlace(p);
			}
		}
		
		acm.addTransition(writer.getTransitions());
		acm.addTransition(reader.getTransitions());
		
		acm.addArc(writer.getArcs());
		acm.addArc(reader.getArcs());
	}
	
	/**
	 * Gets the Petri net of the ACM
	 * 
	 * @return a PetriNet object with the Petri net of an ACM
	 */
	public PetriNet getACM() {
		
		return this.acm;
	}
	
	/**
	 * Gets the Petri net of the Reader process
	 * 
	 * @return a PetriNet object with the Petri net of the Reader process
	 */
	public PetriNet getReader() {
		
		return this.reader;
	}
	
	/**
	 * Gets the size of the ACM
	 * 
	 * @return an integer with the size of the ACM
	 */
	public int getSize() {
		
		return this.size;
	}

	/**
	 * Gets the Petri net of the Writer process
	 * 
	 * @return a PetriNet object with the Petri net of the Writer process
	 */
	public PetriNet getWriter() {
		
		return this.writer;
	}
	
	protected abstract boolean createWriter();
	protected abstract boolean createReader();
}

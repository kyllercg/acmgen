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


/**
 * @author Kyller Costa Gorgônio
 * @version 1.1
 */
public class OWRRBB extends ACMsys {
	
	/**
	 * Class contructor
	 * 
	 * @param size - the size of the OWRRBB ACM
	 */
	public OWRRBB(int size) {
		
		this.size = size;
		
		this.writer = new PetriNet();
		this.reader = new PetriNet();
		this.acm = new PetriNet();
		
		if (!this.createWriter()) {
			
			System.out.println("writer not created");
			System.exit(1000);
		}
		
		if (!this.createReader()) {
			
			System.out.println("reader not created");
			System.exit(1001);
		}
		
		calcUnion();
	}
	
	/**
	 * Creates the ACM writer process
	 */
	protected boolean createWriter() {
		
		int cont;
		OWRRBBWriterModule aux;
		
		for (cont = 0; cont < size; cont++) {
			
			aux = new OWRRBBWriterModule(cont, size);
			
			if (!writer.addPlace(aux.getPlaces())) {
				
				System.err.println(ACMgen.msg.getMessage("create_writer_error"));
				return false;
			}
			
			if (!writer.addTransition(aux.getTransitions())) {
				
				System.err.println(ACMgen.msg.getMessage("create_writer_error"));
				return false;
			}
			
			if (!writer.addArc(aux.getArcs())) {
				
				System.err.println(ACMgen.msg.getMessage("create_writer_error"));
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Creates the ACM reader process
	 */
	protected boolean createReader() {
		
		int cont;
		OWRRBBReaderModule aux;
		
		for (cont = 0; cont < size; cont++) {
			
			aux = new OWRRBBReaderModule(cont, size);
			
			if (!reader.addPlace(aux.getPlaces())) {
				
				System.err.println(ACMgen.msg.getMessage("create_reader_error"));
				return false;
			}
			
			if (!reader.addTransition(aux.getTransitions())) {
				
				System.err.println(ACMgen.msg.getMessage("create_reader_error"));
				return false;
			}
			
			if (!reader.addArc(aux.getArcs())) {
				
				System.err.println(ACMgen.msg.getMessage("create_reader_error"));
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Gets the Petri net of the OWRRBB ACM
	 * 
	 * @return a PetriNet object with the Petri net of a OWRRBB ACM
	 */
	public PetriNet getOWRRBB() {
		
		return getACM();
	}
}

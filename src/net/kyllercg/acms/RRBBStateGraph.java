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

import java.util.Vector;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.1
 */
public class RRBBStateGraph implements StateGraphACM {

	/**
	 * The size (number of cells) of the ACM
	 */
	int size;
	
	Vector<State> States = null;
	Vector<Arc> Arcs = null;
	
	Vector<State> New = null;
	
	/**
	 * 
	 */
	public RRBBStateGraph(int size) {
		
		this.size = size;
		States = new Vector<State>();
		Arcs = new Vector<Arc>();
		New = new Vector<State>();
		
		this.genStateGraph();
	}
	
	public void genStateGraph() {
	
		this.genInitState();
		
		while (!this.New.isEmpty()) {
				
			this.genSuccessors(this.New.lastElement());
			this.New.removeElementAt(this.New.size() - 1);
		}
	}
	
	public void genInitState() {
	
		State s = new State("s0", size - 1, 0, false, true);
		this.States.add(s);
		this.New.add(s);
	}
	
	public void genSuccessors(State state) {
		
	}

	public boolean createState() {
		
		return true;
	}
	
	public boolean createArc() {
		
		return true;
	}
	
	public int nextState() {
		
		return 0;
	}
	
	public int nextCounters() {
		
		return 0;
	}
	
	public boolean insertArc() {
		
		return true;
	}
}

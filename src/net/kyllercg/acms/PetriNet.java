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
import java.util.UUID;
import java.util.Vector;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public class PetriNet {
	
	public static int notfound		= -1;
	
	Vector<Place> places = null;
	Vector<Transition> transitions = null;
	Vector<Arc> arcs = null;
	
	/**
	 * Class constructor
	 * 
	 * @param places - the set of places of the Petri net
	 * @param trasitions - the set of transitions of the Petri net
	 * @param arcs - the set of arcs of the Petri net
	 * @param m0 - the initial marking
	 */
	public PetriNet(Vector<Place> places, Vector<Transition> trasitions,
			Vector<Arc> arcs) {
		
		this.places = places;
		this.transitions = trasitions;
		this.arcs = arcs;
	}
	
	/**
	 * Class constructor
	 */
	public PetriNet() {
		
		this.places = new Vector<Place>();
		this.transitions = new Vector<Transition>();
		this.arcs = new Vector<Arc>();
	}
	
	/* *******************************************************************
	 * The PLACES stuff
	 * ******************************************************************* */
	
	/**
	 * Adds a place to the set of places
	 * 
	 * @param e - a Place object
	 * @return true if operation was done correctly, false otherwise
	 */
	public boolean addPlace(Place e) {
		
		if (existsPlace(e.getLabel()) == null) {
			
			if (!places.add(e)) {
				
				return false;
			}
		} 

		return true;
	}
	
	/**
	 * Adds a place set to the set of places
	 * 
	 * @param v - a vector of Place objects
	 * @return true if operation was done correctly, false otherwise
	 */
	public boolean addPlace(Vector<Place> v) {
		
		Place p;
		Iterator<Place> i = v.iterator();
		
		while (i.hasNext()) {
			
			p = (Place)i.next();
			if (existsPlace(p.getLabel()) == null) {
				
				if (!places.add(p)) {
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Creates a new place element
	 * 
	 * @param label - the label of the new place
	 * @param marking - the initial marking of the place
	 * @return a Place object
	 */
	public static Place createPlace(String label, int marking) {
		
		Place e = new Place(label, marking);	
		return e;
	}
	
	/**
	 * Creates a new place element with fixed X and Y coordinates
	 * 
	 * @param label - tha label of the new place
	 * @param marking - the initial marking of the place
	 * @param x - the X coordinate
	 * @param y - the Y coordinate
	 * @return a Place object
	 */
	public static Place createPlace(String label, int marking, int x, int y) {
		
		Place e = new Place(label, marking);
		e.setX(x);
		e.setY(y);
		return e;
	}
	
	/**
	 * Creates a new place element with fixed X and Y coordinates
	 * 
	 * @param label - tha label of the new place
	 * @param marking - the initial marking of the place
	 * @param x - the X coordinate
	 * @param y - the Y coordinate
	 * @return a Place object
	 */
//	public static Place createPlace(String label, int marking, int x, int y) {
//		
//		Place e = new Place(label, marking, shared);
//		e.setX(x);
//		e.setY(y);
//		e.setShared(false);
//		return e;
//	}
	
	/**
	 * Checks if a place with a certain label exists
	 * 
	 * @param label - a string containing the label to be checked
	 * @return the place, if there is one. Otherwise it returns null
	 */
	public Place existsPlace(String label) {
		
		Place p;
		Iterator<Place> i = places.iterator();
		
		while (i.hasNext()) {
			
			p = (Place)i.next();
			if (p.getLabel().equals(label)) {
				
				return p;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the set of places
	 * 
	 * @return a vector containing the set of places of the Petri net
	 */
	public Vector<Place> getPlaces() {
		
		return this.places;
	}
	
	/**
	 * Gets size of set of places
	 * 
	 * @return an integer with the size of the set of places
	 */
	public int getPlacesSize() {
		
		return this.places.size();
	}
	
	/**
	 * Gets the position of a place in the vector of places
	 * 
	 * @param place - the Place object to be checked
	 * @return an integer with the position of the place in the vector of
	 *         places, if there is some. Otherwise, it returns notfound
	 */
	public int getPlPosition(Place place) {
		
		Place p;
		Iterator<Place> i;
		int c = 1;
		
		if (places == null) {
			
			return notfound;
		}
		
		i = places.iterator();
		
		while (i.hasNext()) {
			
			p = (Place)i.next();
			if (p.uuid.equals(place.getUUID())) {
				
				return c;
			}
			c++;
		}
		
		return notfound;
	}

	/**
	 * Tests if the Place element p is a test place int the net or not
	 * 
	 * @param p - the place to be tested
	 * @return true if p is a test place, otherwise false
	 */
	public boolean isTestPlace(Place p) {
		
		Arc e;
		Iterator<Arc> i = arcs.iterator();
		
		while (i.hasNext()) {
			
			e = (Arc)i.next();
			if (e.getType() == Arc.test &&
					e.getDest().getUUID().equals(p.getUUID())) {
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Sets the set of places
	 * 
	 * @param places - the set of places of the Petri net
	 */
	public void setPlaces(Vector<Place> places) {
		
		this.places = places;
	}
	
	/**
	 * Sets the label of a place
	 * 
	 * @param uuid - the UUID of the place
	 * @param label - a string containing the new label of the place
	 * @return true if operation was ok, otherwise false
	 */
//	public boolean setPlaceLabel(Place place, String label) {
//		
//		int i = existsPlace(place);
//		
//		if (i != notfound) {
//			
//			((Place)this.places.elementAt(i)).label = label;
//			return true;
//		}
//		
//		return false;
//		
//	}
	
	/**
	 * Sets the label of a place
	 * 
	 * @param oldlabel - the current label of the place
	 * @param label - a string containing the new label of the place
	 * @return true if operation was ok, otherwise false
	 */
//	public boolean setPlaceLabel(String oldlabel, String label) {
//		
//		Place p = existsPlace(oldlabel);
//		
//		if (p != null) {
//			
////			return setPlaceLabel(id, label);
//			p.setLabel(label);
//		}
//		
//		return false;
//	}
	
	/* *******************************************************************
	 * The TRANSITIONS stuff
	 * ******************************************************************* */
	
	/**
	 * Adds a transition to the set of transitions
	 * 
	 * @param e - a Transition object
	 * @return true if operation was done correctly, false otherwise
	 */
	public boolean addTransition(Transition e) {
		
		if (existsTransition(e.getLabel()) == null) {
			
			if (!transitions.add(e)) {
				
				return false;
			}
		} 

		return true;
	}
	
	/**
	 * Adds a transition set to the set of transitions
	 * 
	 * @param v - a vector of Transition objects
	 * @return true if operation was done correctly, false otherwise
	 */
	public boolean addTransition(Vector<Transition> v) {
		
		Transition t;
		Iterator<Transition> i = v.iterator();
		
		while (i.hasNext()) {
			
			t = (Transition)i.next();
			if (existsTransition(t.getLabel()) == null) {
				
				if (!transitions.add(t)) {
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Creates a new transition element
	 * 
	 * @param label - tha label of the new transition
	 * @return a Transition object
	 */
	public static Transition createTransition(String label) {
		
		Transition e = new Transition(label);
		return e;
	}
	
	/**
	 * Creates a new transition element with fixed X and Y coordinates
	 * 
	 * @param label - tha label of the new transition
	 * @param x - the X coordinate
	 * @param y - the Y coordinate
	 * @return a Transition object
	 */
	public static Transition createTransition(String label, int x, int y) {
		
		Transition e = new Transition(label);
		e.setX(x);
		e.setY(y);
		return e;
	}
	
	/**
	 * Creates a new transition element with fixed X and Y coordinates
	 * 
	 * @param label - tha label of the new transition
	 * @param x - the X coordinate
	 * @param y - the Y coordinate
	 * @param cell - the cell number that the transitions access
	 * @return a Transition object
	 */
	public static Transition createTransition(String label, int x, int y, 
			int cell) {
		
		Transition e = new Transition(label, cell);
		e.setX(x);
		e.setY(y);
		return e;
	}
	
	/**
	 * Creates a new transition element with fixed X and Y coordinates
	 * 
	 * @param label - tha label of the new transition
	 * @param x - the X coordinate
	 * @param y - the Y coordinate
	 * @param src - the source cell number of the transition
	 * @param dest - the destination cell number of the transition
	 * @return a Transition object
	 */
	public static Transition createTransition(String label, int x, int y, 
			int src, int dest) {
		
		Transition e = new Transition(label, src, dest);
		e.setX(x);
		e.setY(y);
		return e;
	}

	/**
	 * Checks if a transition with a certain label exists
	 * 
	 * @param label - a string containing the label to be checked
	 * @return the transition, if there is one. Otherwise it returns null
	 */
	public Transition existsTransition(String label) {
		
		Transition t;
		Iterator<Transition> i = transitions.iterator();
		
		while (i.hasNext()) {
			
			t = (Transition)i.next();
			if (t.getLabel().equals(label)) {
				
				return t;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the set of transitions
	 * 
	 * @return a vector containing the set of transitions of the Petri net
	 */
	public Vector<Transition> getTransitions() {
		
		return this.transitions;
	}

	/**
	 * Gets size of set of transitions
	 * 
	 * @return an integer with the size of the set of transitions
	 */
	public int getTransitionsSize() {
		
		return this.transitions.size();
	}
	
	/**
	 * Gets the position of a transition in the vector of places
	 * 
	 * @param transition - the Transition object to be checked
	 * @return an integer with the position of the transition in the vector of
	 *         transitions, if there is some. Otherwise, it returns notfound
	 */
	public int getTrPosition(Transition transition) {
		
		Transition t;
		Iterator<Transition> i = transitions.iterator();
		int c = 1;
		
		while (i.hasNext()) {
			
			t = (Transition)i.next();
			if (t.uuid.equals(transition.getUUID())) {
				
				return c;
			}
			c++;
		}
		
		return notfound;
	}

	/**
	 * Sets the set of transitions
	 * 
	 * @param transitions - the set of transitions of the Petri net
	 */
	public void setTransitions(Vector<Transition> transitions) {
		
		this.transitions = transitions;
	}
	
	/**
	 * Sets the label of a transition
	 * 
	 * @param transition - the transition
	 * @param label - a string containing the new label of the transition
	 * @return true if operation was ok, otherwise false
	 */
//	public boolean setTransitionLabel(Transition transition, String label) {
//		
//		int i = existsTransition(transition);
//		
//		if (i != notfound) {
//			
//			((Transition)this.transitions.elementAt(i)).label = label;
//			return true;
//		}
//		
//		return false;
//	}
	
	/**
	 * Sets the label of a transition
	 * 
	 * @param oldlabel - the current label of the transition
	 * @param label - a string containing the new label of the transition
	 * @return true if operation was ok, otherwise false
	 */
//	public boolean setTransitionLabel(String oldlabel, String label) {
//		
//		Transition t = existsTransition(oldlabel);
//		
//		if (t != null) {
//			
////			return setTransitionLabel(id, label);
//			t.setLabel(label);
//		}
//		
//		return false;
//	}
	
	/* *******************************************************************
	 * The ARCS stuff
	 * ******************************************************************* */
	
	/**
	 * Adds an arc to the set of arcs
	 * 
	 * @param e - an Arc object
	 * @return true if operation was done correctly, false otherwise
	 */
	public boolean addArc(Arc e) {
		
		if (!arcs.add(e)) {
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Adds an arc set to the set of arcs
	 * 
	 * @param v - a vector of Transition objects
	 * @return true if operation was done correctly, false otherwise
	 */
	public boolean addArc(Vector<Arc> v) {
		
		int cont;
		
		for (cont = 0; cont < v.size(); cont++) {
			
			if (!arcs.add((Arc)v.elementAt(cont))) {
			
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Creates a new arc element
	 * 
	 * @param src - the source node
	 * @param dest - the destination node
	 * @param type - type of the arc. May be:
	 * <ul>
	 *   <li>Arc.place2trans: if the arc links a place to a transition</li>
	 *   <li>Arc.trans2place: if the arc links a transition to a place</li>
	 * </ul>
	 * @return an Arc object
	 */
	public static Arc createArc(NodeElement src, NodeElement dest, int type) {
		
		Arc e = new Arc(src, dest, type);
		return e;
	}
	
	/**
	 * Checks if an arc with a certain UUID exists
	 * 
	 * @param uuid - the UUID to be checked
	 * @return an integer with the position of the place in the vector of
	 *         places, if there is some with UUID uuid. Otherwise, it returns
	 *         notfound
	 */
	public int existsArc(UUID uuid) {
		
		Arc a;
		Iterator<Arc> i = arcs.iterator();
		int c = 0;
		
		while (i.hasNext()) {
			
			a = (Arc)i.next();
			if (a.uuid.equals(uuid)) {
				
				return c;
			}
			c++;
		}
		
		return notfound;
	}
	
	/**
	 * Gets the set of arcs
	 * 
	 * @return a vector containing the set of arcs of the Petri net
	 */
	public Vector<Arc> getArcs() {
		
		return this.arcs;
	}
	
	/**
	 * Gets the UUID of the source node of an arc
	 * 
	 * @param uuid - the UUID of the arc
	 * @return the UUID of the source node of the arc
	 */
	public UUID getArcSrc(UUID uuid) {
		
		int i = existsArc(uuid);
		
		if (i != notfound) {
		
			return ((Arc)arcs.elementAt(i)).src.getUUID();
		}
		
		return null;
	}
	
	/**
	 * Gets the destination node of an arc
	 * 
	 * @param uuid - the UUID of the arc
	 * @return an integer with the ID of the destination node of the arc
	 */
	public UUID getArcDest(UUID uuid) {
		
		int i = existsArc(uuid);
		
		if (i != notfound) {
		
			return ((Arc)arcs.elementAt(i)).dest.getUUID();
		}
		
		return null;
	}
	
	/**
	 * Gets the type of an arc
	 * 
	 * @param uuid - the UUID of the arc
	 * @return an integer with the type of the arc
	 */
	public int getArcType(UUID uuid) {
		
		int i = existsArc(uuid);
		
		if (i != notfound) {
		
			return ((Arc)arcs.elementAt(i)).type;
		}
		
		return notfound;
	}
	
	/**
	 * Sets the set of arcs
	 * 
	 * @param arcs - the set of arcs of the Petri net
	 */
	public void setArcs(Vector<Arc> arcs) {
		
		this.arcs = arcs;
	}
	
	/**
	 * Sets the source node of an arc
	 * 
	 * @param uuid - the UUID of the arc
	 * @param src - the new source node of the arc
	 */
	public boolean setArcSrc(UUID uuid, NodeElement src) {

		int i = existsArc(uuid);
		
		if (i != notfound) {
		
			((Arc)arcs.elementAt(i)).src = src;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the destination node of an arc
	 * 
	 * @param uuid - the UUID of the arc
	 * @param dest - the new source node of the arc
	 */
	public boolean setArcDest(UUID uuid, NodeElement dest) {
		
		int i = existsArc(uuid);
		
		if (i != notfound) {
		
			((Arc)arcs.elementAt(i)).dest = dest;
			return true;
		}
		
		return false;
	}
}

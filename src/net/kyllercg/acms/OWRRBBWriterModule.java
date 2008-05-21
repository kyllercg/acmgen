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
public class OWRRBBWriterModule extends PetriNet {
	
//	private static int PEPcol40		= 40;
	private static int PEPcol80		= 80;
	private static int PEPcol140	= 140;
	private static int PEPcol180	= 180;
	private static int PEPcol220	= 220;
	private static int PEPcol280	= 280;
	private static int PEPcol380	= 380;
	private static int PEPcol480	= 480;
	private static int PEPcol540	= 540;
	private static int PEPcol580	= 580;
	private static int PEPcol620	= 620;
	private static int PEPcol680	= 680;
//	private static int PEPcol720	= 720;
//	private static int PEPcol820	= 820;
	private static int PEPcol840	= 840;
//	private static int PEPcol860	= 860;
//	private static int PEPcol900	= 900;
//	private static int PEPcol940	= 940;
	private static int PEPcol960	= 960;
//	private static int PEPcol980	= 980;
//	private static int PEPcol1040	= 1040;
//	private static int PEPcol1200	= 1200;
//	private static int PEPcol1360	= 1360;
//	private static int PEPcol1420	= 1420;
	private static int PEPcol1440	= 1440;
//	private static int PEPcol1460	= 1460;
//	private static int PEPcol1500	= 1500;
//	private static int PEPcol1540	= 1540;
//	private static int PEPcol1560	= 1560;
	private static int PEPcol1580	= 1580;
	
	private static int PEPln20		= 20;
	private static int PEPln80		= 80;
//	private static int PEPln120		= 120;
	private static int PEPln140		= 140;
	private static int PEPln180		= 180;
//	private static int PEPln240		= 240;
//	private static int PEPln260		= 260;
	private static int PEPln300		= 300;
	private static int PEPln400		= 400;
	private static int PEPheight	= PEPln400 - PEPln20;	// 380
	
	// All places names
	private String wiI0   = "";
	private String wiI1   = "";
	private String pwiI0  = "";
	private String pwiI1  = "";
	private String wiJ0   = "";
	private String wiJ1   = "";
	private String weI    = "";
	private String weJ    = "";
	private String lI0    = "";
	private String lI1    = "";
	private String lJ0    = "";
	private String lJ1    = "";
	private String reJ0   = "";
	private String rneJ0  = "";
	private String reJ1   = "";
	private String rneJ1  = "";
	
	// All transitions names
	private String wrI0   = "";
	private String wrI1   = "";
	private String lI0J0  = "";
	private String lI0J0p = "";
	private String lI0J1  = "";
	private String lI0J1p = "";
	private String lI1J0  = "";
	private String lI1J0p = "";
	private String lI1J1  = "";
	private String lI1J1p = "";
	
	/**
	 * The number of the cell controled by the module
	 */
	private int cell;
	
	/**
	 * The number of the next cell to the one controled by the module
	 */
	private int next;
	
	/**
	 * Class constructor
	 * 
	 * @param cell - the cell number that the module controls
	 */
	public OWRRBBWriterModule(int cell, int size) {
		
		this.cell = cell;
		this.next = (this.cell + 1) % size;
		
		this.wiI0   =  "wi" + this.cell + "0";
		this.wiI1   =  "wi" + this.cell + "1";
		this.pwiI0  = "pwi" + this.cell + "0";
		this.pwiI1  = "pwi" + this.cell + "1";
		this.wiJ0   =  "wi" + next + "0";
		this.wiJ1   =  "wi" + next + "1";
		this.weI    =  "we" + this.cell;
		this.weJ    =  "we" + next;
		this.lI0    =   "l" + this.cell + "0";
		this.lI1    =   "l" + this.cell + "1";
		this.lJ0    =   "l" + next + "0";
		this.lJ1    =   "l" + next + "1";
		this.reJ0   =  "re" + next + "0";
		this.rneJ0  = "rne" + next + "0";
		this.reJ1   =  "re" + next + "1";
		this.rneJ1  = "rne" + next + "1";
		
		this.wrI0   =  "wr" + this.cell + "0";
		this.wrI1   =  "wr" + this.cell + "1";
		this.lI0J0  =   "l" + this.cell + "0" + "_" + next + "0";
		this.lI0J0p =   "l" + this.cell + "0" + "_" + next + "0p";
		this.lI0J1  =   "l" + this.cell + "0" + "_" + next + "1";
		this.lI0J1p =   "l" + this.cell + "0" + "_" + next + "1p";
		this.lI1J0  =   "l" + this.cell + "1" + "_" + next + "0";
		this.lI1J0p =   "l" + this.cell + "1" + "_" + next + "0p";
		this.lI1J1  =   "l" + this.cell + "1" + "_" + next + "1";
		this.lI1J1p =   "l" + this.cell + "1" + "_" + next + "1p";
		
		this.createAllPlaces();
		this.createAllTransitions();
		this.createAllArcs();
	}
	
	/**
	 * Create all places of the modules.
	 */
	private void createAllPlaces() {
		
		//places
		addPlace(PetriNet.createPlace(this.wiI0, 0,
				PEPcol180, (PEPln20 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.wiI1, (this.cell == 1) ? 1 : 0,
				PEPcol580, (PEPln20 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.pwiI0, 0,
				PEPcol180, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.pwiI1, 0,
				PEPcol580, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.wiJ0, 0,
				PEPcol180, (PEPln20 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.wiJ1, (this.next == 1) ? 1 : 0,
				PEPcol580, (PEPln20 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.weI, (this.cell == 1) ? 1 : 0,
				PEPcol380, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.weJ, (this.next == 1) ? 1 : 0,
				PEPcol380, (PEPln140 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.lI0, 0,
				PEPcol80, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.lI1, (this.cell != 1) ? 1 : 0,
				PEPcol680, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.lJ0, 0,
				PEPcol80, (PEPln140 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.lJ1, (this.next != 1) ? 1 : 0,
				PEPcol680, (PEPln140 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.reJ0, 0,
				PEPcol840, (PEPln180 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.rneJ0, 1,
				PEPcol960, (PEPln180 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.reJ1, ((this.next == 0) ? 1 : 0),
				PEPcol1580, (PEPln180 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.rneJ1, ((this.next != 0) ? 1 : 0),
				PEPcol1440, (PEPln180 + (PEPheight * this.next))));
	}
	
	/**
	 * Create all transitions of the modules.
	 */
	private void createAllTransitions() {
		
		addTransition(PetriNet.createTransition(this.wrI0, PEPcol180,
				(PEPln80 + (PEPheight * this.cell)), 2 * this.cell));
		addTransition(PetriNet.createTransition(this.wrI1, PEPcol580,
				(PEPln80 + (PEPheight * this.cell)), (2 * this.cell) + 1));
		
		addTransition(PetriNet.createTransition(this.lI0J0,
				PEPcol80, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.lI0J0p,
				PEPcol140, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.lI0J1,
				PEPcol480, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 1));
		addTransition(PetriNet.createTransition(this.lI0J1p,
				PEPcol540, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 1));
		
		addTransition(PetriNet.createTransition(this.lI1J0,
				PEPcol220, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.lI1J0p,
				PEPcol280, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.lI1J1,
				PEPcol620, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 1));
		addTransition(PetriNet.createTransition(this.lI1J1p,
				PEPcol680, (PEPln300 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 1));
	}
	
	/**
	 * Create all arcs of the modules.
	 */
	private void createAllArcs() {
	
		// arcs: place -> transition
		addArc(PetriNet.createArc(existsPlace(this.wiI0),
				existsTransition(this.wrI0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.wiI1),
				existsTransition(this.wrI1), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.pwiI0),
				existsTransition(this.lI0J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.pwiI0),
				existsTransition(this.lI0J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.pwiI0),
				existsTransition(this.lI0J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.pwiI0),
				existsTransition(this.lI0J1p), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.pwiI1),
				existsTransition(this.lI1J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.pwiI1),
				existsTransition(this.lI1J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.pwiI1),
				existsTransition(this.lI1J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.pwiI1),
				existsTransition(this.lI1J1p), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.lJ0),
				existsTransition(this.lI0J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.lJ0),
				existsTransition(this.lI1J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.lJ0),
				existsTransition(this.lI0J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.lJ0),
				existsTransition(this.lI1J1), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.lJ1),
				existsTransition(this.lI0J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.lJ1),
				existsTransition(this.lI1J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.lJ1),
				existsTransition(this.lI0J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.lJ1),
				existsTransition(this.lI1J1p), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI0J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI0J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI0J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI0J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI1J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI1J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI1J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.weI),
				existsTransition(this.lI1J1p), Arc.place2trans));
		
		// arcs: trasition -> place
		addArc(PetriNet.createArc(existsTransition(this.wrI0),
				existsPlace(this.pwiI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.wrI1),
				existsPlace(this.pwiI1), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI0J0),
				existsPlace(this.lI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J0),
				existsPlace(this.wiJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J0),
				existsPlace(this.weJ), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI0J0p),
				existsPlace(this.lI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J0p),
				existsPlace(this.wiJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J0p),
				existsPlace(this.weJ), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI1J0),
				existsPlace(this.lI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J0),
				existsPlace(this.wiJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J0),
				existsPlace(this.weJ), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI1J0p),
				existsPlace(this.lI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J0p),
				existsPlace(this.wiJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J0p),
				existsPlace(this.weJ), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI0J1),
				existsPlace(this.lI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J1),
				existsPlace(this.wiJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J1),
				existsPlace(this.weJ), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI0J1p),
				existsPlace(this.lI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J1p),
				existsPlace(this.wiJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI0J1p),
				existsPlace(this.weJ), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI1J1),
				existsPlace(this.lI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J1),
				existsPlace(this.wiJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J1),
				existsPlace(this.weJ), Arc.trans2place));
		
		addArc(PetriNet.createArc(existsTransition(this.lI1J1p),
				existsPlace(this.lI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J1p),
				existsPlace(this.wiJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.lI1J1p),
				existsPlace(this.weJ), Arc.trans2place));
		
		// arcs: test
		addArc(PetriNet.createArc(existsTransition(this.lI0J0),
				existsPlace(this.rneJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI0J0p),
				existsPlace(this.rneJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI0J0p),
				existsPlace(this.reJ1), Arc.test));
		
		addArc(PetriNet.createArc(existsTransition(this.lI1J0),
				existsPlace(this.rneJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI1J0p),
				existsPlace(this.rneJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI1J0p),
				existsPlace(this.reJ1), Arc.test));
		
		addArc(PetriNet.createArc(existsTransition(this.lI0J1),
				existsPlace(this.rneJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI0J1p),
				existsPlace(this.rneJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI0J1p),
				existsPlace(this.reJ0), Arc.test));
		
		addArc(PetriNet.createArc(existsTransition(this.lI1J1),
				existsPlace(this.rneJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI1J1p),
				existsPlace(this.rneJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.lI1J1p),
				existsPlace(this.reJ0), Arc.test));
	}
}

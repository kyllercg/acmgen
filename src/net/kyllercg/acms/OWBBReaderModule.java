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
public class OWBBReaderModule extends PetriNet {
	
//	private static int PEPcol40		= 40;
	private static int PEPcol80		= 80;
//	private static int PEPcol140	= 140;
//	private static int PEPcol180	= 180;
//	private static int PEPcol220	= 220;
//	private static int PEPcol280	= 280;
	private static int PEPcol380	= 380;
//	private static int PEPcol480	= 480;
//	private static int PEPcol540	= 540;
//	private static int PEPcol580	= 580;
//	private static int PEPcol620	= 620;
	private static int PEPcol680	= 680;
//	private static int PEPcol720	= 720;
	private static int PEPcol820	= 820;
	private static int PEPcol840	= 840;
	private static int PEPcol860	= 860;
	private static int PEPcol900	= 900;
	private static int PEPcol940	= 940;
	private static int PEPcol960	= 960;
	private static int PEPcol980	= 980;
	private static int PEPcol1040	= 1040;
	private static int PEPcol1200	= 1200;
	private static int PEPcol1360	= 1360;
	private static int PEPcol1420	= 1420;
	private static int PEPcol1440	= 1440;
	private static int PEPcol1460	= 1460;
	private static int PEPcol1500	= 1500;
	private static int PEPcol1540	= 1540;
//	private static int PEPcol1560	= 1560;
	private static int PEPcol1580	= 1580;
	
	private static int PEPln20		= 20;
	private static int PEPln80		= 80;
//	private static int PEPln120		= 120;
	private static int PEPln140		= 140;
	private static int PEPln180		= 180;
	private static int PEPln240		= 240;
	private static int PEPln260		= 260;
	private static int PEPln400		= 400;
	private static int PEPheight	= PEPln400 - PEPln20;	// 380
	
	// All places names
	private String riI0    = "";
	private String riI1    = "";
	private String riJ0    = "";
	private String riJ1    = "";
	private String priI0   = "";
	private String priI1   = "";
	private String reI0    = "";
	private String rneI0   = "";
	private String reI1    = "";
	private String rneI1   = "";
	private String reJ0    = "";
	private String rneJ0   = "";
	private String reJ1    = "";
	private String rneJ1   = "";
	private String raI     = "";
	private String raJ     = "";
	private String weI     = "";
	private String weJ     = "";
	private String lI0     = "";
	private String lI1     = "";
	private String lJ0     = "";
	private String lJ1     = "";
	
	// All transitions names
	private String rdI0    = "";
	private String rdI1    = "";
//	private String mI0I0   = "";
//	private String mI1I1   = "";
	private String mI0J0   = "";
	private String mI0J0p  = "";
	private String mI0J1   = "";
	private String mI0J1p  = "";
	private String mI1J0   = "";
	private String mI1J0p  = "";
	private String mI1J1   = "";
	private String mI1J1p  = "";
	private String mI0e1   = "";
	private String mI0e2   = "";
	private String mI0e3   = "";
	private String mI1e1   = "";
	private String mI1e2   = "";
	private String mI1e3   = "";
	
	/**
	 * The number of the cell controled by the module
	 */
	private int cell;
	
	/**
	 * The number of the next cell to the one controled by the module
	 */
	private int next;

	/**
	 * Class contructor
	 * 
	 * @param cell - the cell number that the module controls
	 */
	public OWBBReaderModule(int cell, int size) {
		
		this.cell = cell;
		this.next = (this.cell + 1) % size;
		
		this.riI0    =  "ri" + this.cell + "0";
		this.riI1    =  "ri" + this.cell + "1";
		this.riJ0    =  "ri" + this.next + "0";
		this.riJ1    =  "ri" + this.next + "1";
		this.priI0   = "pri" + this.cell + "0";
		this.priI1   = "pri" + this.cell + "1";
		this.reI0    =  "re" + this.cell + "0";
		this.rneI0   = "rne" + this.cell + "0";
		this.reI1    =  "re" + this.cell + "1";
		this.rneI1   = "rne" + this.cell + "1";
		this.reJ0    =  "re" + this.next + "0";
		this.rneJ0   = "rne" + this.next + "0";
		this.reJ1    =  "re" + this.next + "1";
		this.rneJ1   = "rne" + this.next + "1";
		this.raI     =  "ra" + this.cell;
		this.raJ     =  "ra" + this.next;
		this.weI     =  "we" + this.cell;
		this.weJ     =  "we" + this.next;
		this.lI0     =   "l" + this.cell + "0";
		this.lI1     =   "l" + this.cell + "1";
		this.lJ0     =   "l" + this.next + "0";
		this.lJ1     =   "l" + this.next + "1";
		
		this.rdI0    =  "rd" + this.cell + "0";
		this.rdI1    =  "rd" + this.cell + "1";
//		this.mI0I0   =   "m" + this.cell + "0" + "_" + this.cell + "0";
//		this.mI1I1   =   "m" + this.cell + "1" + "_" + this.cell + "1";
		this.mI0J0   =   "m" + this.cell + "0" + "_" + this.next + "0";
		this.mI0J0p  =   "m" + this.cell + "0" + "_" + this.next + "0p";
		this.mI0J1   =   "m" + this.cell + "0" + "_" + this.next + "1";
		this.mI0J1p  =   "m" + this.cell + "0" + "_" + this.next + "1p";
		this.mI1J0   =   "m" + this.cell + "1" + "_" + this.next + "0";
		this.mI1J0p  =   "m" + this.cell + "1" + "_" + this.next + "0p";
		this.mI1J1   =   "m" + this.cell + "1" + "_" + this.next + "1";
		this.mI1J1p  =   "m" + this.cell + "1" + "_" + this.next + "1p";
		this.mI0e1   =   "m" + this.cell + "0e1";
		this.mI0e2   =   "m" + this.cell + "0e2";
		this.mI0e3   =   "m" + this.cell + "0e3";
		this.mI1e1   =   "m" + this.cell + "1e1";
		this.mI1e2   =   "m" + this.cell + "1e2";
		this.mI1e3   =   "m" + this.cell + "1e3";
		
		this.createAllPlaces();
		this.createAllTransitions();
		this.createAllArcs();
	}
	
	/**
	 * Create all places of the modules.
	 */
	private void createAllPlaces() {
		
		addPlace(PetriNet.createPlace(this.riI0, 0,
				PEPcol900, (PEPln20 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.riI1, ((this.cell == 0) ? 1 : 0),
				PEPcol1500, (PEPln20 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.riJ0, 0,
				PEPcol900, (PEPln20 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.riJ1, ((this.next == 0) ? 1 : 0),
				PEPcol1500, (PEPln20 + (PEPheight * this.next))));
		
		addPlace(PetriNet.createPlace(this.priI0, 0,
				PEPcol900, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.priI1, 0,
				PEPcol1500, (PEPln140 + (PEPheight * this.cell))));
		
		addPlace(PetriNet.createPlace(this.reI0, 0,
				PEPcol840, (PEPln180 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.rneI0, 1,
				PEPcol960, (PEPln180 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.reI1, ((this.cell == 0) ? 1 : 0),
				PEPcol1580, (PEPln180 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.rneI1, ((this.cell != 0) ? 1 : 0),
				PEPcol1440, (PEPln180 + (PEPheight * this.cell))));
		
		addPlace(PetriNet.createPlace(this.reJ0, 0,
				PEPcol840, (PEPln180 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.rneJ0, 1,
				PEPcol960, (PEPln180 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.reJ1, ((this.next == 0) ? 1 : 0),
				PEPcol1580, (PEPln180 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.rneJ1, ((this.next != 0) ? 1 : 0),
				PEPcol1440, (PEPln180 + (PEPheight * this.next))));
		
		addPlace(PetriNet.createPlace(this.raI, 0,
				PEPcol1200, (PEPln260 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.raJ, 0,
				PEPcol1200, (PEPln260 + (PEPheight * this.next))));
		
		addPlace(PetriNet.createPlace(this.weI, ((this.cell == 1) ? 1 : 0),
				PEPcol380, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.weJ, ((this.next == 1) ? 1 : 0),
				PEPcol380, (PEPln140 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.lI0, 0,
				PEPcol80, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.lI1, ((this.cell != 1) ? 1 : 0),
				PEPcol680, (PEPln140 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(this.lJ0, 0,
				PEPcol80, (PEPln140 + (PEPheight * this.next))));
		addPlace(PetriNet.createPlace(this.lJ1, ((this.next != 1) ? 1 : 0),
				PEPcol680, (PEPln140 + (PEPheight * this.next))));
	}
	
	/**
	 * Create all transitions of the modules.
	 */
	private void createAllTransitions() {
		
		addTransition(PetriNet.createTransition(this.rdI0, PEPcol900,
				(PEPln80 + (PEPheight * this.cell)), 2 * this.cell));
		addTransition(PetriNet.createTransition(this.rdI1, PEPcol1500,
				(PEPln80 + (PEPheight * this.cell)), (2 * this.cell) + 1));
		
//		addTransition(PetriNet.createTransition(this.mI0I0, PEPcol980,
//				(PEPln80 + (PEPheight * this.cell)),
//				(2 * this.cell) + 0, (2 * this.cell) + 0));
//		addTransition(PetriNet.createTransition(this.mI1I1, PEPcol1420,
//				(PEPln80 + (PEPheight * this.cell)),
//				(2 * this.cell) + 1, (2 * this.cell) + 1));
		
		addTransition(PetriNet.createTransition(this.mI0J0, PEPcol860,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.mI0J0p, PEPcol820,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.mI0J1, PEPcol940,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 1));
		addTransition(PetriNet.createTransition(this.mI0J1p, PEPcol980,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, (2 * this.next) + 1));
		
		addTransition(PetriNet.createTransition(this.mI1J0, PEPcol1460,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.mI1J0p, PEPcol1420,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 0));
		addTransition(PetriNet.createTransition(this.mI1J1, PEPcol1540,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 1));
		addTransition(PetriNet.createTransition(this.mI1J1p, PEPcol1580,
				(PEPln260 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, (2 * this.next) + 1));
		
		addTransition(PetriNet.createTransition(this.mI0e1, PEPcol1040,
				(PEPln140 + (PEPheight * this.cell)),
				(2 * this.cell) + 0, -100));
		addTransition(PetriNet.createTransition(this.mI0e2, PEPcol1040,
				(PEPln240 + (PEPheight * this.cell)),
				-100, -100));
		addTransition(PetriNet.createTransition(this.mI0e3, PEPcol1040,
				(PEPln400 + (PEPheight * this.cell)),
				-100, (2 * this.next) + 0));
		
		addTransition(PetriNet.createTransition(this.mI1e1, PEPcol1360,
				(PEPln140 + (PEPheight * this.cell)),
				(2 * this.cell) + 1, -100));
		addTransition(PetriNet.createTransition(this.mI1e2, PEPcol1360,
				(PEPln240 + (PEPheight * this.cell)),
				-100, -100));
		addTransition(PetriNet.createTransition(this.mI1e3, PEPcol1360,
				(PEPln400 + (PEPheight * this.cell)),
				-100, (2 * this.next) + 1));
	}
	
	/**
	 * Create all arcs of the modules.
	 */
	private void createAllArcs() {
		
		// arcs: place -> transition
		addArc(PetriNet.createArc(existsPlace(this.riI0),
				existsTransition(this.rdI0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.riI1),
				existsTransition(this.rdI1), Arc.place2trans));
		
//		addArc(PetriNet.createArc(existsPlace(this.priI0),
//				existsTransition(this.mI0I0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI0),
				existsTransition(this.mI0J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI0),
				existsTransition(this.mI0J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI0),
				existsTransition(this.mI0J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI0),
				existsTransition(this.mI0J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI0),
				existsTransition(this.mI0e1), Arc.place2trans));
		
//		addArc(PetriNet.createArc(existsPlace(this.priI1),
//				existsTransition(this.mI1I1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI1),
				existsTransition(this.mI1J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI1),
				existsTransition(this.mI1J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI1),
				existsTransition(this.mI1J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI1),
				existsTransition(this.mI1J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.priI1),
				existsTransition(this.mI1e1), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.reI0),
				existsTransition(this.mI0J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI0),
				existsTransition(this.mI0J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI0),
				existsTransition(this.mI0J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI0),
				existsTransition(this.mI0J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI0),
				existsTransition(this.mI0e1), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.reI1),
				existsTransition(this.mI1J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI1),
				existsTransition(this.mI1J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI1),
				existsTransition(this.mI1J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI1),
				existsTransition(this.mI1J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.reI1),
				existsTransition(this.mI1e1), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.rneJ0),
				existsTransition(this.mI0J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ0),
				existsTransition(this.mI0J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ0),
				existsTransition(this.mI1J0), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ0),
				existsTransition(this.mI1J0p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ0),
				existsTransition(this.mI0e3), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.rneJ1),
				existsTransition(this.mI0J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ1),
				existsTransition(this.mI0J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ1),
				existsTransition(this.mI1J1), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ1),
				existsTransition(this.mI1J1p), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.rneJ1),
				existsTransition(this.mI1e3), Arc.place2trans));
		
		addArc(PetriNet.createArc(existsPlace(this.raI),
				existsTransition(this.mI0e2), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.raI),
				existsTransition(this.mI1e2), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.raI),
				existsTransition(this.mI0e3), Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(this.raI),
				existsTransition(this.mI1e3), Arc.place2trans));
		
		// arcs: transition -> place
		addArc(PetriNet.createArc(existsTransition(this.rdI0),
				existsPlace(this.priI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.rdI1),
				existsPlace(this.priI1), Arc.trans2place));
//		addArc(PetriNet.createArc(existsTransition(this.mI0I0),
//				existsPlace(this.riI0), Arc.trans2place));
//		addArc(PetriNet.createArc(existsTransition(this.mI1I1),
//				existsPlace(this.riI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0),
				existsPlace(this.rneI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0),
				existsPlace(this.reJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0),
				existsPlace(this.riJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0p),
				existsPlace(this.rneI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0p),
				existsPlace(this.reJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0p),
				existsPlace(this.riJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1),
				existsPlace(this.rneI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1),
				existsPlace(this.reJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1),
				existsPlace(this.riJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1p),
				existsPlace(this.rneI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1p),
				existsPlace(this.reJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1p),
				existsPlace(this.riJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0),
				existsPlace(this.rneI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0),
				existsPlace(this.reJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0),
				existsPlace(this.riJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0p),
				existsPlace(this.rneI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0p),
				existsPlace(this.reJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0p),
				existsPlace(this.riJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1),
				existsPlace(this.rneI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1),
				existsPlace(this.reJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1),
				existsPlace(this.riJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1p),
				existsPlace(this.rneI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1p),
				existsPlace(this.reJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1p),
				existsPlace(this.riJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0e1),
				existsPlace(this.rneI0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0e1),
				existsPlace(this.raJ), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0e2),
				existsPlace(this.raJ), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0e3),
				existsPlace(this.reJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI0e3),
				existsPlace(this.riJ0), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1e1),
				existsPlace(this.rneI1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1e1),
				existsPlace(this.raJ), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1e2),
				existsPlace(this.raJ), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1e3),
				existsPlace(this.reJ1), Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(this.mI1e3),
				existsPlace(this.riJ1), Arc.trans2place));
		
		// arcs: test
//		addArc(PetriNet.createArc(existsTransition(this.mI0I0),
//				existsPlace(this.weJ), Arc.test));
//		addArc(PetriNet.createArc(existsTransition(this.mI0I0),
//				existsPlace(this.lI0), Arc.test));
//		addArc(PetriNet.createArc(existsTransition(this.mI1I1),
//				existsPlace(this.weJ), Arc.test));
//		addArc(PetriNet.createArc(existsTransition(this.mI1I1),
//				existsPlace(this.lI1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0),
				existsPlace(this.lI0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0),
				existsPlace(this.lJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0p),
				existsPlace(this.weI), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J0p),
				existsPlace(this.lJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1),
				existsPlace(this.lI0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1),
				existsPlace(this.lJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1p),
				existsPlace(this.weI), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0J1p),
				existsPlace(this.lJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0),
				existsPlace(this.lI1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0),
				existsPlace(this.lJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0p),
				existsPlace(this.weI), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J0p),
				existsPlace(this.lJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1),
				existsPlace(this.lI1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1),
				existsPlace(this.lJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1p),
				existsPlace(this.weI), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1J1p),
				existsPlace(this.lJ1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0e1),
				existsPlace(this.lI1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0e2),
				existsPlace(this.lI0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0e3),
				existsPlace(this.weI), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI0e3),
				existsPlace(this.lJ0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1e1),
				existsPlace(this.lI0), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1e2),
				existsPlace(this.lI1), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1e3),
				existsPlace(this.weI), Arc.test));
		addArc(PetriNet.createArc(existsTransition(this.mI1e3),
				existsPlace(this.lJ1), Arc.test));
	}
}

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
public class RRBBWriterModule extends PetriNet {
	
	private static int PEPcol100	= 100;
	private static int PEPcol160	= 160;
	private static int PEPcol220	= 220;
//	private static int PEPcol500	= 500;
//	private static int PEPcol560	= 560;
	private static int PEPcol620	= 620;
	
	private static int PEPln60		= 60;
	private static int PEPln140		= 140;
	private static int PEPln220		= 220;
	private static int PEPln300		= 300;
	private static int PEPln380		= 380;
	private static int PEPheight	= PEPln380 - PEPln60;	// 320
	
	/**
	 * The number of the cell controled by the module
	 */
	private int cell;
	
	/**
	 * Class constructor
	 * 
	 * @param cell - the cell number that the module controls
	 */
	public RRBBWriterModule(int cell, int size) {
		
		this.cell = cell;
		int next = (this.cell + 1) % size;
		
		String wI   =  "wi" + this.cell; 
		String wJ   =  "wi" + next;
		String pwI  = "pwi" + this.cell;
		String weI  =  "we" + this.cell;
		String wneI = "wne" + this.cell;
		String weJ  =  "we" + next;
		String wneJ = "wne" + next;
		String rneJ = "rne" + next;
		
		String wrI  =  "wr" + this.cell;
		String lIJ  =   "l" + this.cell + "_" + next;
		
		addPlace(PetriNet.createPlace(wI, (cell == 1) ? 1 : 0,
				PEPcol160, (PEPln60 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(wJ, (next == 1) ? 1 : 0,
				PEPcol160, (PEPln60 + (PEPheight * next))));
		addPlace(PetriNet.createPlace(pwI, 0,
				PEPcol160, (PEPln220 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(weI, ((cell == 1) ? 1 : 0),
				PEPcol100, (PEPln220 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(wneI, ((cell == 1) ? 0 : 1),
				PEPcol220, (PEPln220 + (PEPheight * this.cell))));
		addPlace(PetriNet.createPlace(weJ, ((next == 1) ? 1 : 0),
				PEPcol100, (PEPln220 + (PEPheight * next))));
		addPlace(PetriNet.createPlace(wneJ, ((next == 1) ? 0 : 1),
				PEPcol220, (PEPln220 + (PEPheight * next))));
		addPlace(PetriNet.createPlace(rneJ, ((next == 0) ? 0 : 1),
				PEPcol620, (PEPln220 + (PEPheight * next))));
		
		addTransition(PetriNet.createTransition(wrI, PEPcol160,
				(PEPln140 + (PEPheight * this.cell)), this.cell));
		addTransition(PetriNet.createTransition(lIJ, PEPcol160,
				(PEPln300 + (PEPheight * this.cell)), this.cell, next));
		
		addArc(PetriNet.createArc(existsPlace(wI), existsTransition(wrI),
				Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(weI), existsTransition(lIJ),
				Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(wneJ), existsTransition(lIJ),
				Arc.place2trans));
		addArc(PetriNet.createArc(existsPlace(pwI), existsTransition(lIJ),
				Arc.place2trans));
		addArc(PetriNet.createArc(existsTransition(wrI), existsPlace(pwI),
				Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(lIJ), existsPlace(wJ),
				Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(lIJ), existsPlace(wneI),
				Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(lIJ), existsPlace(weJ),
				Arc.trans2place));
		addArc(PetriNet.createArc(existsTransition(lIJ), existsPlace(rneJ),
				Arc.test));
	}
}

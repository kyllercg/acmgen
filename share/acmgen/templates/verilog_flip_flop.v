/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * $Id$
 */

module flip_flop_reset (D, Q, clock, reset);

parameter init_value = 0;

input D;
input clock;
input reset;
output Q;
	
reg Q;
	
always @(posedge clock or posedge reset) begin
	if (reset)
		Q <= init_value;
	else
		Q <= D;
end
endmodule

/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * $Id$
 */

module mux(data, sel, result, clock);

input [%%%ACMgen:ACM_SIZE%%%:0] data;
input [%%%ACMgen:ACM_SIZE_LOG%%%:0] sel;
input clock;
output result;

reg result;

always @(sel or data) begin
	result <= 1'b1;
	%%%ACMgen:MUX_CASE%%%

end

endmodule

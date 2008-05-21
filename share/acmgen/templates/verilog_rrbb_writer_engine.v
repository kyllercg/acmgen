/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * $Id$
 */

module writer_engine(
	clock,
	ack,
	ereq,
	rd,
	edata,
%%%ACMgen:cell_parms%%%
	req,
	eack,
	addr,
	data,
	sel,
	reset
);

parameter
%%%ACMgen:states_def%%%

input	clock;
input	ack;
input	ereq;
input	rd;
input	[31:0] edata;
input	reset;
%%%ACMgen:cell_io_decl%%%
output	req;
output	eack;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] addr;
output	[31:0] data;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] sel;

%%%ACMgen:cell_decl%%%
reg req;
reg eack;
reg	[%%%ACMgen:ACM_SIZE_LOG%%%:0] addr;
reg	[31:0] data;
reg	[%%%ACMgen:ACM_SIZE_LOG%%%:0] sel;

reg [%%%ACMgen:MAX_STATES-1%%%:0] state;

always @(posedge clock or posedge reset) begin
	if (reset) begin
		%%%ACMgen:init%%%
		sel <= 2;
		req <= 1'b0;
		eack <= 1'b0;
		addr <= 1;
		data <= edata;
		state <= %%%ACMgen:MAX_STATES%%%'b0;
		state[IDLE1] <= 1'b1;
	end else begin
		case (1'b1)
			%%%ACMgen:fsm%%%
		endcase
	end
end

endmodule

/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * $Id$
 */

module reader_engine(
	clock,
	ack,
	ereq,
	wr,
	data,
%%%ACMgen:cell_parms%%%
	req,
	eack,
	addr,
	edata,
	sel,
	reset
);

parameter
%%%ACMgen:states_def%%%

input	clock;
input	ack;
input	ereq;
input	wr;
input	[31:0] data;
input	reset;
%%%ACMgen:cell_io_decl%%%
output	req;
output	eack;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] addr;
output	[31:0] edata;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] sel;

%%%ACMgen:cell_decl%%%
reg req;
reg eack;
reg [%%%ACMgen:ACM_SIZE_LOG%%%:0] addr;
reg [31:0] edata;
reg [%%%ACMgen:ACM_SIZE_LOG%%%:0] sel;

reg [%%%ACMgen:MAX_STATES-1%%%:0] state;

always @(posedge clock or posedge reset) begin
	if (reset) begin
		%%%ACMgen:init%%%
		sel <= 1;
		req <= 1'b0;
		eack <= 1'b0;
		addr <= 0;
		edata <= data;
		state <= %%%ACMgen:MAX_STATES%%%'b0;
		state[IDLE0] <= 1'b1;
	end else begin
		case (1'b1)
			%%%ACMgen:fsm%%%
		endcase
	end
end

endmodule

/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * $Id$
 */

module writer(
	clock,
	reset,
	rd_result,
	ereq,
	ack,
	edata,
	rd_select,
	result,
	eack,
	req,
	addr,
	data,
	select
);

input	clock;
input	reset;
input	rd_result;
input	ereq;
input	ack;
input	[31:0] edata;
input	[%%%ACMgen:ACM_SIZE_LOG%%%:0] rd_select;
output	result;
output	eack;
output	req;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] addr;
output	[31:0] data;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] select;

wire	[%%%ACMgen:ACM_SIZE%%%:0] wr;
wire	WIRE_DELAY_0;
wire	WIRE_DELAY_1;
%%%ACMgen:wire_decl%%%

// This is the first delay to avoid metastability
flip_flop_reset delay0(
	.D(rd_result),
	.clock(clock),
	.reset(reset),
	.Q(WIRE_DELAY_0));
defparam delay0.init_value = 0;

// This is the second delay to avoid metastability
flip_flop_reset delay1(
	.D(WIRE_DELAY_0),
	.clock(clock),
	.reset(reset),
	.Q(WIRE_DELAY_1));
defparam delay1.init_value = 0;

// writer's core engine
writer_engine writer_core(
	.ereq(ereq),
	.clock(clock),
	.ack(ack),
	.rd(WIRE_DELAY_1),
	.edata(edata),
	.eack(eack),
%%%ACMgen:cell_parms%%%
	.req(req),
	.addr(addr),
	.data(data),
	.sel(select),
	.reset(reset));

%%%ACMgen:flip_flops%%%
// for the reader selection
mux wr_sel(
	.data(wr),
	.sel(rd_select),
	.result(result),
	.clock(clock));

endmodule

// some includes -- needed for simulation with cver only

`include "writer_engine.v"


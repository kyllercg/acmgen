/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * $Id$
 */

module reader(
	clock,
	reset,
	ereq,
	wr_result,
	ack,
	data,
	wr_select,
	result,
	eack,
	req,
	addr,
	edata,
	select
);

input	clock;
input	reset;
input	ereq;
input	wr_result;
input	ack;
input	[31:0] data;
input	[%%%ACMgen:ACM_SIZE_LOG%%%:0] wr_select;
output	result;
output	eack;
output	req;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] addr;
output	[31:0] edata;
output	[%%%ACMgen:ACM_SIZE_LOG%%%:0] select;

wire	[%%%ACMgen:ACM_SIZE%%%:0] rd;
wire	WIRE_DELAY_0;
wire	WIRE_DELAY_1;
%%%ACMgen:wire_decl%%%

// This is the first delay to avoid metastability
flip_flop_reset	delay0(
	.D(wr_result),
	.Q(WIRE_DELAY_0),
	.clock(clock),
	.reset(reset));
defparam delay0.init_value = 0;

// This is the second delay to avoid metastability
flip_flop_reset	delay1(
	.D(WIRE_DELAY_0),
	.Q(WIRE_DELAY_1),
	.clock(clock),
	.reset(reset));
defparam delay1.init_value = 0;

// reader's core engine
reader_engine	reader_core(
	.wr(WIRE_DELAY_1),
	.clock(clock),
	.ack(ack),
	.ereq(ereq),
	.data(data),
%%%ACMgen:cell_parms%%%
	.req(req),
	.eack(eack),
	.addr(addr),
	.edata(edata),
	.sel(select),
	.reset(reset));

%%%ACMgen:flip_flops%%%
// for the writer selection
mux rd_sel(
	.data(rd),
	.sel(wr_select),
	.result(result),
	.clock(clock));

endmodule

// some includes -- needed for simulation with cver only

`include "reader_engine.v"

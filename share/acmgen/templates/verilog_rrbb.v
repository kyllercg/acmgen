/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * $Id$
 */

module ACM(
	reset,
	wreq,
	rreq,
	clock_wr,
	clock_rd,
	wdata,
	wack,
	rack,
	rdata
);

input	reset;
input	wreq;
input	rreq;
input	clock_wr;
input	clock_rd;
input	[31:0] wdata;
output	wack;
output	rack;
output	[31:0] rdata;

wire	SYNTHESIZED_WIRE_0;
wire	SYNTHESIZED_WIRE_1;
wire	[31:0] SYNTHESIZED_WIRE_2;
wire	[%%%ACMgen:ACM_SIZE_LOG%%%:0] SYNTHESIZED_WIRE_3;
wire	SYNTHESIZED_WIRE_4;
wire	SYNTHESIZED_WIRE_5;
wire	[%%%ACMgen:ACM_SIZE_LOG%%%:0] SYNTHESIZED_WIRE_6;
wire	[%%%ACMgen:ACM_SIZE_LOG%%%:0] SYNTHESIZED_WIRE_7;
wire	[31:0] SYNTHESIZED_WIRE_8;
wire	SYNTHESIZED_WIRE_9;
wire	SYNTHESIZED_WIRE_10;
wire	[%%%ACMgen:ACM_SIZE_LOG%%%:0] SYNTHESIZED_WIRE_11;
wire	SYNTHESIZED_WIRE_12;
wire	SYNTHESIZED_WIRE_13;

always @(SYNTHESIZED_WIRE_6 or SYNTHESIZED_WIRE_7) begin
	$display("%g      %d          %d", $time, SYNTHESIZED_WIRE_7, SYNTHESIZED_WIRE_6);
	if (SYNTHESIZED_WIRE_6 == SYNTHESIZED_WIRE_7) begin
		$display("Houston, we have a problem!");
		$stop;
	end else
		$display("Everything is going fine!");
end

reader	reader_module(.ereq(rreq),
					  .clock(clock_rd),
					  .ack(SYNTHESIZED_WIRE_0),
					  .reset(reset),
					  .wr_result(SYNTHESIZED_WIRE_1),
					  .data(SYNTHESIZED_WIRE_2),
					  .wr_select(SYNTHESIZED_WIRE_3),
					  .eack(rack),.req(SYNTHESIZED_WIRE_5),
					  .result(SYNTHESIZED_WIRE_10),
					  .addr(SYNTHESIZED_WIRE_6),
					  .edata(rdata),
					  .select(SYNTHESIZED_WIRE_11));

shmem	shared_memory(.wreq(SYNTHESIZED_WIRE_4),
					  .rreq(SYNTHESIZED_WIRE_5),
					  .reset(reset),
					  .clock_rd(clock_rd),
					  .clock_wr(clock_wr),
					  .raddr(SYNTHESIZED_WIRE_6),
					  .waddr(SYNTHESIZED_WIRE_7),
					  .wdata(SYNTHESIZED_WIRE_8),
					  .wack(SYNTHESIZED_WIRE_9),
					  .rack(SYNTHESIZED_WIRE_0),
					  .rdata(SYNTHESIZED_WIRE_2));

writer	writer_module(.ereq(wreq),
					  .clock(clock_wr),
					  .ack(SYNTHESIZED_WIRE_9),
					  .reset(reset),
					  .rd_result(SYNTHESIZED_WIRE_10),
					  .edata(wdata),
					  .rd_select(SYNTHESIZED_WIRE_11),
					  .eack(wack),
					  .req(SYNTHESIZED_WIRE_4),
					  .result(SYNTHESIZED_WIRE_1),
					  .addr(SYNTHESIZED_WIRE_7),
					  .data(SYNTHESIZED_WIRE_8),
					  .select(SYNTHESIZED_WIRE_3));

endmodule

// some includes -- needed for simulation with cver only

`include "shmem.v"
`include "reader.v"
`include "writer.v"
`include "mux.v"
`include "flip_flop.v"

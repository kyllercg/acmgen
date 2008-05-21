/*
 * Copyright 2006-2007 Kyller Costa Gorgônio
 * Copyright 2006-2007 Universitat Politècnica de Catalunya
 *
 * RRBB Verilo usage example
 *
 * $Id$
 */

module acm_project;

wire [31:0] data_wr;
wire req_wr;
wire ack_wr;
wire clk_wr;

wire [31:0] data_rd;
wire req_rd;
wire ack_rd;
wire clk_rd;

reg reset;

initial begin
	$display("time:  WRITER     READER");
	// $monitor("%g  %d %d", $time, data_wr, data_rd);
	reset = 1'b1;
	#5 reset = 1'b0;
end

// always begin
	// if ($time > 50) begin
		// $display ("proj_acm: stopping simulation @%d", $time);
		// $stop;
	// end
// end

ACM acmp(.reset(reset),
         .wreq(req_wr),
		 .rreq(req_rd),
		 .clock_wr(clk_wr),
		 .clock_rd(clk_rd),
		 .wdata(data_wr),
		 .wack(ack_wr),
		 .rack(ack_rd),
		 .rdata(data_rd));

rd_proc rdp(.data(data_rd),
			.req(req_rd),
			.ack(ack_rd),
			.clock(clk_rd),
			.reset(reset));

wr_proc wrp(.data(data_wr),
			.req(req_wr),
			.ack(ack_wr),
			.clock(clk_wr),
			.reset(reset));
endmodule



module wr_proc(data, req, ack, clock, reset);

// port declarations
output data;
output req;
input ack;
output clock;
input reset;

integer data;
reg req;
wire ack;
reg clock;
wire reset;

always #13 clock = ~clock;

always @(posedge clock or posedge reset) begin
	if ($time > 100000) begin
		$stop;
	end else if (reset) begin
		clock <= 1'b0;
		req <= 1'b0;
		data <= 32'b0;
	end else if (!req && !ack) begin
		data <= $random;
		req <= 1;
		$display("writing: %d", data);
	end else if (ack) begin
		req <= $random;
		// $display("end write: %d", data);
	end
end
endmodule // end of wr_proc module



module rd_proc(data, req, ack, clock, reset);

// port declarations
input data;
output req;
input ack;
output clock;
input reset;

wire [31:0] data;
reg req;
wire ack;
reg clock;
wire reset;

integer new;

always #11 clock = ~clock;

always @(posedge clock or posedge reset) begin
	if (reset) begin
		clock <= 1'b0;
		req <= 1'b0;
		new <= data;
	end else if (!req && !ack) begin
		req <= 1'b1;
	end else if (ack) begin
		new <= data;
		req <= $random;
		$display("reading: %d", new);
	end
end
endmodule // end of wr_proc module

`include "ACM.v"

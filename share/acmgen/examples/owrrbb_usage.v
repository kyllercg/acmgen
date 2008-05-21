// ===========================================================================
// Copyright 2006, 2008 - Kyller Costa Gorgônio
// Copyright 2006, 2008 - Universitat Politècnica de Catalunya
//
// OWRRBB / OWBB Verilo usage example
//
// $Id$
// ===========================================================================

module proj_acm;

	wire [31:0] data_in;
	wire req_in;
	wire ack_in;
	wire [31:0] data_out;
	wire req_out;
	wire ack_out;

	initial begin
		// $display("time:  WRITER     READER");
		// $monitor("%g  %d %d", $time, data_in, data_out);
	end

	ACM my_acm(data_in, req_in, ack_in, data_out, req_out, ack_out);
	rd_proc rdp(data_out, req_out, ack_out);
	wr_proc wrp(data_in, req_in, ack_in);
endmodule

module wr_proc(data, req, ack);

	// port declarations
	output data;
	output req;
	input ack;

	integer data;
	reg req;
	wire ack;

	reg clk;

	initial begin
		clk = 0;
		req = 0;
		data = 0;
		// #2 req = ~req;
		// $monitor("%b", ack);
	end

	always #13 clk = ~clk;

	always @(posedge clk)
	begin
		if (req == 0)
		begin
			data = $random;
			req = 1;
		end
	end

	always @(negedge clk)
	begin
		if (ack == 1)
		begin
			req = 0;
		end
	end
endmodule // end of wr_proc module

module rd_proc(data, req, ack);

	// port declarations
	input data;
	output req;
	input ack;

	wire [31:0] data;
	reg req;
	wire ack;

	reg clk;
	integer new;

	initial begin
		clk = 0;
		req = 0;
		new = data;
		// #3 req = ~req;
		//$monitor("%d", clk);
	end

	always #3 clk = ~clk;

	always @(posedge clk)
	begin
		if (req == 0)
		begin
			req = 1;
		end
	end

	always @(negedge clk)
	begin
		if (ack == 1)
		begin
			new = data;
			req = 0;
		end
	end
endmodule // end of wr_proc module

`include "ACM.v"

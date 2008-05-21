// The module for the shared memory
module shmem_mod(add_in, data_in, req_in, ack_in, clk_in, add_out, data_out, req_out, ack_out, clk_out);

	input add_in;
	input data_in;
	input req_in;
	input clk_in;
	output ack_in;
	input add_out;
	output data_out;
	input req_out;
	output ack_out;
	input clk_out;

	wire [31:0] add_in;
	wire [31:0] data_in;
	wire req_in;
	wire clk_in;
	reg ack_in;
	wire [31:0] add_out;
	integer data_out;
	wire req_out;
	reg ack_out;
	wire clk_out;

	integer ACM [0:%%%ACMgen:ACM_SIZE%%%];

	initial begin
		ACM[0] = 0;
		ACM[1] = 0;
		ack_in = 0;
		ack_out = 0;
		data_out = ACM[0];
	end

	always @(posedge clk_in)
	begin
		if (req_in == 1 && ack_in == 0)
		begin
			ACM[add_in] = data_in;
			ack_in = 1;
		end else if (req_in == 0 && ack_in == 1)
		begin
			ack_in = 0;
		end
	end

	always @(posedge clk_out)
	begin
		if (req_out == 1 && ack_out == 0)
		begin
			data_out = ACM[add_out];
			ack_out = 1;
		end else if (req_out == 0 && ack_out == 1)
		begin
			ack_out = 0;
		end
	end

endmodule // end of shmem_mod module

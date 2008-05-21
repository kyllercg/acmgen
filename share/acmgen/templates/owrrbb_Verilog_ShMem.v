// The module for the shared memory
module shmem_mod(add_in, data_in, req_in, ack_in, add_out, data_out, req_out, ack_out);

	input add_in;
	input data_in;
	input req_in;
	output ack_in;
	input add_out;
	output data_out;
	input req_out;
	output ack_out;

	wire [31:0] add_in;
	wire [31:0] data_in;
	wire req_in;
	reg ack_in;
	wire [31:0] add_out;
	integer data_out;
	wire req_out;
	reg ack_out;

	integer ACM [0:%%%ACMgen:ACM_SIZE*2%%%];

	initial begin
		ACM[0] = 0;
		ACM[1] = 0;
		ack_in = 0;
		ack_out = 0;
		data_out = ACM[0];
	end

	always @(req_in)
	begin
		ACM[add_in] = data_in;
		ack_in = ~ack_in;
	end

	always @(req_out)
	begin
		data_out = ACM[add_out];
		ack_out = ~ack_out;
	end

endmodule // end of shmem_mod module

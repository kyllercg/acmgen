// The main OWRRBB / OWBB ACM module
module ACM(wr_data, wrreq, wrack, rd_data, rdreq, rdack);

	// ports declarations
	input wr_data;
	input wrreq;
	input rdreq;
	output wrack;
	output rd_data;
	output rdack;

	// input ports
	wire [31:0] wr_data;
	wire wrreq;
	wire rdreq;

	// output ports
	wire wrack;
	wire [31:0] rd_data;
	wire rdack;
	
	// input-output ports

	// internal variables
	wire [0:%%%ACMgen:ACM_SIZE%%%] we;
	wire [0:%%%ACMgen:ACM_SIZE*2%%%] l;
	wire [0:%%%ACMgen:ACM_SIZE*2%%%] re;
	wire [0:%%%ACMgen:ACM_SIZE*2%%%] rne;
	wire [0:%%%ACMgen:ACM_SIZE%%%] ra;
	wire [31:0] wradd;
	wire [31:0] rdadd;
	wire wsmreq;
	wire wsmack;
	wire rsmreq;
	wire rsmack;
	wire rrun;

	initial begin
		$display("time:  WRITER     READER");
		$monitor("%g  %d %d", $time, wradd, rdadd);
	end
	
	always @(wradd | rdadd)
	begin
		if (wradd == rdadd && rrun == 0)
		begin
			$display("Houston, we have a problem!");
		end else 
		begin
			$display("Everything is going fine!");
		end
	end
	
	writer_mod writer(we, l, re, rne, ra, wrreq, wrack, wradd, rdack, wsmreq, wsmack);
	reader_mod reader(re, rne, ra, we, l, rdreq, rdack, rdadd, rsmreq, rsmack, rrun);
	shmem_mod memory(wradd, wr_data, wsmreq, wsmack, rdadd, rd_data, rsmreq, rsmack);

endmodule // end of rrbb module

// INCLUDES
`include "Writer.v"
`include "Reader.v"
`include "ShMem.v"

// The main RRBB ACM module
module ACM(wr_data, wrreq, wrack, rd_data, rdreq, rdack, wclk, rclk);

// ports declarations
input wr_data;
input wrreq;
input rdreq;
input wclk;
input rclk;
output wrack;
output rd_data;
output rdack;

// input ports
wire [31:0] wr_data;
wire wrreq;
wire rdreq;
wire wclk;
wire rclk;

// output ports
wire wrack;
wire [31:0] rd_data;
wire rdack;

// input-output ports

// internal variables
wire we;
wire wne;
wire re;
wire rne;
wire [31:0] wradd;
wire [31:0] rdadd;
wire wsmreq;
wire wsmack;
wire rsmreq;
wire rsmack;
wire [31:0] wsel;
wire [31:0] rsel;
wire wreq;
wire rreq;
wire wack;
wire rack;

initial begin
	$display("time:  WRITER     READER");
	$monitor("%g  %d %d", $time, wradd, rdadd);
end

always @(wradd | rdadd)
begin
	if (wradd == rdadd)
	begin
		$display("Houston, we have a problem!");
	end else 
	begin
		$display("Everything is going fine!");
	end
end

writer_mod writer(we, wne, re, rne, wrreq, wrack, wradd, wsmreq, wsmack, wsel, wreq, wack, rsel, rreq, rack, wclk);
reader_mod reader(re, rne, we, wne, rdreq, rdack, rdadd, rsmreq, rsmack, rsel, rreq, rack, wsel, wreq, wack, rclk);
shmem_mod memory(wradd, wr_data, wsmreq, wsmack, wclk, rdadd, rd_data, rsmreq, rsmack, rclk);

endmodule // end of rrbb module

// INCLUDES
`include "Writer.v"
`include "Reader.v"
`include "ShMem.v"
